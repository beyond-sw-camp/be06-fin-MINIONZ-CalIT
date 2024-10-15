package minionz.batch.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import minionz.common.scrum.sprint.SprintRepository;
import minionz.common.scrum.sprint.model.Sprint;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SprintReminderBatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JpaTransactionManager jpaTransactionManager;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final SprintRepository sprintRepository;

    @Bean
    public ItemReader<Sprint> sprintReader() {
        return new RepositoryItemReaderBuilder<Sprint>()
                .repository(sprintRepository)
                .methodName("findSprintsByEndDateAfter")
                .arguments(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .pageSize(50)
                .sorts(Collections.singletonMap("sprintId", Sort.Direction.ASC))
                .name("sprintReader")
                .build();
    }

    @Bean
    public ItemProcessor<Sprint, Map<String, Object>> sprintProcessor() {
        return sprint -> {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            // 알람을 보낼 사용자 ID 목록 생성
            List<Long> participants = sprint.getSprintParticipations()
                    .stream()
                    .map(sprintParticipation -> sprintParticipation.getUser().getUserId())
                    .toList();

            // 1일 전 알람
            if (sprint.getEndDate().minusDays(2).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                return Map.of("participants", participants, "id", sprint.getSprintId(), "alarmId", 9L);
            }

            // 1시간 전 알람
            if (sprint.getEndDate().minusDays(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                return Map.of("participants", participants, "id", sprint.getSprintId(), "alarmId", 10L);
            }

            return null;
        };
    }

    @Bean
    public ItemWriter<Map<String, Object>> sprintWriter() {
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
    public Job sprintReminderJob(JobRepository jobRepository, Step sprintStep) {
        return new JobBuilder("sprintReminderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(sprintStep)
                .build();
    }

    @Bean
    public Step sprintStep(JobRepository jobRepository, ItemReader<Sprint> sprintReader, ItemProcessor<Sprint, Map<String, Object>> sprintProcessor, ItemWriter<Map<String, Object>> sprintWriter) {

        return new StepBuilder("sprintStep", jobRepository)
                .<Sprint, Map<String, Object>>chunk(50, jpaTransactionManager)
                .reader(sprintReader)
                .processor(sprintProcessor)
                .writer(sprintWriter)
                .transactionManager(jpaTransactionManager)
                .build();
    }

}
