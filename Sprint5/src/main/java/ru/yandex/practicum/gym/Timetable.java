package main.java.ru.yandex.practicum.gym;

import com.sun.source.tree.Tree;

import java.util.*;

public class Timetable {

    Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.computeIfAbsent(day, k -> new TreeMap<>());
        dayMap.computeIfAbsent(time, k -> new ArrayList<>()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessions : dayMap.values()) {
            result.addAll(sessions);
        }
        return Collections.unmodifiableList(result);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
       if (dayMap == null) {
           return Collections.emptyList();
       }
        List<TrainingSession> sessions = dayMap.get(timeOfDay);
       if (sessions == null) {
           return Collections.emptyList();
       }
       return Collections.unmodifiableList(sessions);
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> countMap = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession trainingSession : sessions) {
                    Coach coach = trainingSession.getCoach();
                    countMap.put(coach, countMap.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<Map.Entry<Coach, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort((a, b) -> Integer.compare(a.getValue(), b.getValue()));
        Map<Coach, Integer> resultCount = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : sortedEntries) {
            resultCount.put(entry.getKey(), entry.getValue());
        }
        return resultCount;
    }
}
