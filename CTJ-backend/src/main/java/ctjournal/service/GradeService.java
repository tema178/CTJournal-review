package ctjournal.service;

import ctjournal.domain.Grade;

import java.util.List;

public interface GradeService {
    Grade save(Grade grade);

    Grade findByFrench(String grade);

    Grade findById(Long id);

    List<Grade> findAll();

    void delete(Long id);
}
