package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    @EntityGraph(attributePaths = {"role"})
    List<Person> findAll();

    Person findByLogin(String login);
}
