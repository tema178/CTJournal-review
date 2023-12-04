package ctjournal.restController;

import ctjournal.domain.Grade;
import ctjournal.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GradeRestController {

    private final GradeService service;

    @GetMapping("/api/grade")
    public List<Grade> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/grade/{id}")
    public Grade findById(@PathVariable long id) {
        return service.findById(id);
    }
}
