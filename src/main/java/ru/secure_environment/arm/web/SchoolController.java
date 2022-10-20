package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.secure_environment.arm.model.School;
import ru.secure_environment.arm.repository.SchoolRepository;

import java.net.URI;

@RestController
@RequestMapping(value = SchoolController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "School Controller")
public class SchoolController {
    public static final String URL = "/api/school";

    private final SchoolRepository schoolRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<School> create(@RequestBody School school) {
        log.info("create school {}", school);
        school = schoolRepository.save(school);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(school.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(school);
    }
}
