package minionz.batch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import java.time.Instant;

public class JobDurationListener implements JobExecutionListener {
    private Instant startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = Instant.now();
        System.out.println("Batch job started at: " + startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Instant endTime = Instant.now();
        long duration = java.time.Duration.between(startTime, endTime).toMillis();
        System.out.println("Batch job completed at: " + endTime);
        System.out.println("Total duration: " + duration + " milliseconds");
    }
}
