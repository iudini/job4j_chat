package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    @EntityGraph(attributePaths = {"roles"})
    List<Person> findAll();

    Person findByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    Optional<Person> findById(Long id);
}
