package minionz.apiserver.scrum.schedule;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.alarm.AlarmService;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.meeting.MeetingRepository;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.SprintRepository;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.task.TaskRepository;
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

//    private final MeetingRepository meetingRepository;
    private final AlarmService alarmService;
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

//    @Scheduled(cron = "0 */10 * * * ?")
//    @Transactional
//    public void sendMeetingReminders() {
//        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//        List<Meeting> upcomingMeetings = meetingRepository.findMeetingByStartDateAfter(now);
//
//        for (Meeting meeting : upcomingMeetings) {
//            if (meeting.getStartDate().minusMinutes(10).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
//                List<Long> participants = meeting.getMeetingParticipations().stream().map(meetingParticipationRepository -> meetingParticipationRepository.getUser().getUserId()).toList();
//                alarmService.sendScheduledEventsToClients(participants, 6L, meeting.getMeetingId());
//            }
//        }
//    }

    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional
    public void sendTaskReminders() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Task> upcomingTasks = taskRepository.findTasksByEndDateAfter(now);

        for (Task task : upcomingTasks) {
            if (task.getEndDate().minusDays(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendScheduledEventsToClients(task.getTaskParticipations().stream().map(taskParticipation -> taskParticipation.getUser().getUserId()).toList(), 7L, task.getTaskId());
            }

            if (task.getEndDate().minusHours(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendScheduledEventsToClients(task.getTaskParticipations().stream().map(taskParticipation -> taskParticipation.getUser().getUserId()).toList(), 8L, task.getTaskId());
            }
        }
    }

    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional
    public void sendSprintReminders() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Sprint> upcomingSprints = sprintRepository.findSprintsByEndDateAfter(now);

        for (Sprint sprint : upcomingSprints) {
            if (sprint.getEndDate().minusDays(1).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendScheduledEventsToClients(sprint.getSprintParticipations().stream().map(sprintParticipation -> sprintParticipation.getUser().getUserId()).toList(), 10L, sprint.getSprintId());
            }

            if (sprint.getEndDate().minusDays(2).truncatedTo(ChronoUnit.MINUTES).isEqual(now)) {
                alarmService.sendScheduledEventsToClients(sprint.getSprintParticipations().stream().map(sprintParticipation -> sprintParticipation.getUser().getUserId()).toList(), 9L, sprint.getSprintId());
            }
        }
    }

}