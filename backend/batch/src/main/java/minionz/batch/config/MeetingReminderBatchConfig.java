package minionz.batch.config;

import lombok.RequiredArgsConstructor;
import minionz.common.scrum.meeting.MeetingRepository;
import minionz.common.scrum.meeting.model.Meeting;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class MeetingReminderBatchConfig {

    private final JpaTransactionManager jpaTransactionManager;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MeetingRepository meetingRepository;

    @Bean
    public ItemReader<Meeting> meetingReader() {
        return new RepositoryItemReaderBuilder<Meeting>()
                .repository(meetingRepository)
                .methodName("findMeetingByStartDateAfter")
                .arguments(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .pageSize(50)
                .sorts(Collections.singletonMap("meetingId", Sort.Direction.ASC))
                .name("meetingReader")
                .build();
    }

    @Bean
    public ItemProcessor<Meeting, Map<String, Object>> meetingProcessor() {
        return meeting -> {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            System.out.println("현재 시각: " + now);

            if (meeting.getStartDate().minusMinutes(10).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                List<Long> participants = meeting.getMeetingParticipations()
                        .stream()
                        .map(participation -> participation.getUser().getUserId())
                        .toList();

                Long meetingId = meeting.getMeetingId();

                return Map.of("participants", participants, "id", meetingId, "alarmId", 6L);
            }

            return null;
        };
    }

    @Bean
    public ItemWriter<Map<String, Object>> meetingWriter() {
        return messages -> {
            System.out.println("messages" + messages);
            for (Map<String, Object> messageData : messages) {
                System.out.println("messageData: " + messageData);
                if (messageData != null) {
                    List<Long> participants = (List<Long>) messageData.get("participants");
                    Long id = (Long) messageData.get("id");
                    Long alarmId = (Long) messageData.get("alarmId");

                    String messageContent = String.format(
                            "{\"participants\": %s, \"id\": %d, \"alarmId\": \"%d\"}",
                            participants.toString(), id, alarmId
                    );

                    kafkaTemplate.send("reminder-alarm", messageContent);
                }
            }
        };
    }

    @Bean
    public Job meetingReminderJob(JobRepository jobRepository, Step meetingStep) {
        return new JobBuilder("meetingReminderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobDurationListener())
                .start(meetingStep)
                .build();
    }

    @Bean
    public Step meetingStep(JobRepository jobRepository, ItemReader<Meeting> meetingReader, ItemProcessor<Meeting, Map<String, Object>> meetingProcessor, ItemWriter<Map<String, Object>> meetingWriter) {

        return new StepBuilder("meetingStep", jobRepository)
                .<Meeting, Map<String, Object>>chunk(50, jpaTransactionManager)
                .reader(meetingReader)
                .processor(meetingProcessor)
                .writer(meetingWriter)
                .transactionManager(jpaTransactionManager)
                .build();
    }
}
