package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.job4j.chat.service.Services.update;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messages;
    private final PersonRepository persons;
    private final RoomRepository rooms;

    public List<Message> findAll() {
        List<Message> rsl = new ArrayList<>();
        this.messages.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<Message> findById(Long id) {
        return this.messages.findById(id);
    }

    public Message save(Message message) {
        Room room = this.rooms.findById(message.getRoom().getId()).get();
        Person person = this.persons.findById(message.getPerson().getId()).get();
        message.setRoom(room);
        message.setPerson(person);
        return this.messages.save(message);
    }

    public void delete(Long id) {
        this.messages.deleteById(id);
    }

    public Optional<Message> updateSomeFields(Message message) throws InvocationTargetException, IllegalAccessException {
        return update(messages, message);
    }
}
