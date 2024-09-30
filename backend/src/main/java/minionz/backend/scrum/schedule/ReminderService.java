package minionz.backend.scrum.schedule;

import lombok.RequiredArgsConstructor;
import minionz.backend.alarm.AlarmService;
import minionz.backend.scrum.meeting.MeetingRepository;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.sprint.SprintRepository;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.task.TaskRepository;
import minionz.backend.scrum.task.model.Task;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ReminderService {

    private final MeetingRepository meetingRepository;
    private final AlarmService alarmService;
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;


    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void sendMeetingReminders() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Meeting> upcomingMeetings = meetingRepository.findMeetingByStartDateAfter(now);

        for (Meeting meeting : upcomingMeetings) {
            if (meeting.getStartDate().minusMinutes(10).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                List<Long> participants = meeting.getMeetingParticipations().stream().map(meetingParticipationRepository -> meetingParticipationRepository.getUser().getUserId()).toList();
                alarmService.sendEventsToClients(participants, 1L, 6L);
            }
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void sendTaskReminders() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Task> upcomingTasks = taskRepository.findTasksByEndDateAfter(now);

        for (Task task : upcomingTasks) {
            if (task.getEndDate().minusDays(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendEventsToClients(task.getTaskParticipations().stream().map(taskParticipation -> taskParticipation.getUser().getUserId()).toList(), 1L, 7L);
            }

            if (task.getEndDate().minusHours(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendEventsToClients(task.getTaskParticipations().stream().map(taskParticipation -> taskParticipation.getUser().getUserId()).toList(), 1L, 10L);
            }
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void sendSprintReminders() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Sprint> upcomingSprints = sprintRepository.findSprintsByEndDateAfter(now);

        for (Sprint sprint : upcomingSprints) {
            if (sprint.getEndDate().minusDays(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendEventsToClients(sprint.getSprintParticipations().stream().map(sprintParticipation -> sprintParticipation.getUser().getUserId()).toList(), 1L, 9L);
            }

            if (sprint.getEndDate().minusDays(2).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendEventsToClients(sprint.getSprintParticipations().stream().map(sprintParticipation -> sprintParticipation.getUser().getUserId()).toList(), 1L, 8L);
            }
        }
    }

}
