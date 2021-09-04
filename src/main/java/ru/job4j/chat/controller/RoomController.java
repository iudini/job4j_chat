package ru.job4j.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService service;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Room> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Room> findById(@PathVariable Long id) {
        var room = this.service.findById(id);
        return new ResponseEntity<>(
                room.orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("All fields must be filled");
        }
        return new ResponseEntity<>(
                this.service.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("All fields must be filled");
        }
        this.service.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Room> updateSomeFields(@RequestBody Room room) throws InvocationTargetException, IllegalAccessException {
        return new ResponseEntity<>(
                this.service.updateSomeFields(room).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                ),
                HttpStatus.OK
        );
    }
}
