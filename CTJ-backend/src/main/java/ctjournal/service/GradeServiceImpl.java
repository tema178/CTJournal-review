package ctjournal.service;

import ctjournal.domain.Grade;
import ctjournal.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository repository;

    @Override
    public Grade save(Grade grade) {
        return repository.save(grade);
    }

    @Override
    public Grade findByFrench(String grade) {
        return repository.findByFrench(grade).orElseThrow(NullPointerException::new);
    }

    @Override
    public Grade findById(Long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Grade> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
