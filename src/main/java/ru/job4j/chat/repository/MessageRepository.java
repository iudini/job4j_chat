package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @EntityGraph(attributePaths = {"person", "room"})
    List<Message> findAll();
}
