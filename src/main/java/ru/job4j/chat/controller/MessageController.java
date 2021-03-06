package ru.job4j.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.validation.Operation;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService service;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Message> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        var message = service.findById(id);
        return new ResponseEntity<>(
                message.orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        return new ResponseEntity<>(
                this.service.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        this.service.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Message> updateSomeFields(@RequestBody Message message) throws InvocationTargetException, IllegalAccessException {
        return new ResponseEntity<>(
                this.service.updateSomeFields(message).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                ),
                HttpStatus.OK
        );
    }
}
