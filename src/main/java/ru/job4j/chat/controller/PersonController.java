package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.validation.Operation;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    private final PersonService service;
    private final ObjectMapper objectMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Person> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        var person = this.service.findById(id);
        return new ResponseEntity<>(
                person.orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-up")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.service.save(person));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        this.service.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Person> updateSomeFields(@RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        return new ResponseEntity<>(
                this.service.updateSomeFields(person).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public void passwordLengthLessThan8(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", "Password not correct");
                put("details", e.getMessage());
            }
        }));
        log.error(e.getMessage());
    }
}
