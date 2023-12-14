package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.Grade;

import java.util.List;

public interface GradeService {
    List<Grade> getGrades(String id);

    Grade getGrade(long id, String userId);
}
