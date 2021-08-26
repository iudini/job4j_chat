package ru.job4j.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {
    private final PersonRepository persons;
    private final RoleRepository roles;

    public PersonService(PersonRepository persons, RoleRepository roles) {
        this.persons = persons;
        this.roles = roles;
    }

    public List<Person> findAll() {
        return this.persons.findAll();
    }

    public Optional<Person> findById(Long id) {
        return this.persons.findById(id);
    }

    public Person save(Person person) {
        Role role = this.roles.findByName("USER");
        person.setRole(role);
        return this.persons.save(person);
    }

    public void delete(Long id) {
        this.persons.deleteById(id);
    }
}
