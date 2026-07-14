import java.util.*;

public class Timetable {

    private static class DaySchedule {
        private final List<TrainingSession> sortedSessions = new ArrayList<>();
        private final TreeMap<TimeOfDay, List<TrainingSession>> timeSessions = new TreeMap<>();
    }

    public static class CoachCount {
        private final Coach coach;
        private final int count;

        public CoachCount(Coach coach, int count) {
            this.coach = coach;
            this.count = count;
        }

        public Coach getCoach() {
            return coach;
        }

        public int getCount() {
            return count;
        }
    }

    private final DaySchedule[] daySchedules = new DaySchedule[7];

    public Timetable() {
        for (int i = 0; i < 7; i++) {
            daySchedules[i] = new DaySchedule();
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        DaySchedule schedule = daySchedules[day.ordinal()];

        schedule.timeSessions.computeIfAbsent(time, k -> new ArrayList<>()).add(trainingSession);
        List<TrainingSession> newList = schedule.sortedSessions;
        int serialNumber = 0;
        while (serialNumber < newList.size() && newList.get(serialNumber).getTimeOfDay().compareTo(time) <= 0) {
            serialNumber++;
        }
        newList.add(serialNumber, trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return Collections.unmodifiableList(daySchedules[dayOfWeek.ordinal()].sortedSessions);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
       List<TrainingSession> sessions = daySchedules[dayOfWeek.ordinal()].timeSessions.get(timeOfDay);
       if (sessions == null) {
           return Collections.emptyList();
       }
       return Collections.unmodifiableList(sessions);
    }

    public List<CoachCount> getCountByCoaches() {
        Map<Coach, Integer> countMap = new HashMap<>();
        for (DaySchedule daySchedule : daySchedules) {
            for (TrainingSession trainingSession : daySchedule.sortedSessions) {
                Coach coach = trainingSession.getCoach();
                countMap.put(coach, countMap.getOrDefault(coach, 0) + 1);
            }
        }
        List<CoachCount> resultCount = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countMap.entrySet()) {
            resultCount.add(new CoachCount(entry.getKey(), entry.getValue()));
        }
        resultCount.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
        return resultCount;
    }
}
