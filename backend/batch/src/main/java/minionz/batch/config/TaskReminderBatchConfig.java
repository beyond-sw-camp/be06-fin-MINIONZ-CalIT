package minionz.batch.config;

import lombok.RequiredArgsConstructor;
import minionz.common.scrum.task.repository.TaskRepository;
import minionz.common.scrum.task.model.Task;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class TaskReminderBatchConfig {

    private final JpaTransactionManager jpaTransactionManager;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TaskRepository taskRepository;

    @Bean
    public ItemReader<Task> taskReader() {
        return new RepositoryItemReaderBuilder<Task>()
                .repository(taskRepository)
                .methodName("findTasksByEndDateAfter")
                .arguments(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .pageSize(50)
                .sorts(Collections.singletonMap("taskId", Sort.Direction.ASC))
                .name("taskReader")
                .build();
    }

    @Bean
    public ItemProcessor<Task, Map<String, Object>> taskProcessor() {
        return task -> {
            LocalDate today = LocalDate.now();

            List<Long> participants = task.getTaskParticipations()
                    .stream()
                    .map(taskParticipation -> taskParticipation.getUser().getUserId())
                    .toList();

            if (task.getEndDate().toLocalDate().isEqual(today.minusDays(1))) {
                return Map.of("participants", participants, "id", task.getTaskId(), "alarmId", 7L);
            }

            if (task.getEndDate().toLocalDate().isEqual(today)) {
                return Map.of("participants", participants, "id", task.getTaskId(), "alarmId", 8L);
            }

            return null;
        };
    }


    @Bean
    public ItemWriter<Map<String, Object>> taskWriter() {
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
    public Job taskReminderJob(JobRepository jobRepository, Step taskStep) {
        return new JobBuilder("taskReminderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobDurationListener())
                .start(taskStep)
                .build();
    }

    @Bean
    public Step taskStep(JobRepository jobRepository, ItemReader<Task> taskReader, ItemProcessor<Task, Map<String, Object>> taskProcessor, ItemWriter<Map<String, Object>> taskWriter) {

        return new StepBuilder("taskStep", jobRepository)
                .<Task, Map<String, Object>>chunk(50, jpaTransactionManager)
                .reader(taskReader)
                .processor(taskProcessor)
                .writer(taskWriter)
                .transactionManager(jpaTransactionManager)
                .build();
    }


}
