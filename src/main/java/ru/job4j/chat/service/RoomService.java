package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository rooms;

    public List<Room> findAll() {
        List<Room> rsl = new ArrayList<>();
        this.rooms.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<Room> findById(Long id) {
        return this.rooms.findById(id);
    }

    public Room save(Room room) {
        return this.rooms.save(room);
    }

    public void delete(Long id) {
        this.rooms.deleteById(id);
    }
}
