package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {
    private final PersonRepository persons;
    private final RoleRepository roles;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = persons.findByUsername(username);
        if (user == null) {
            log.error("User with name {} not found in the database", username);
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public List<Person> findAll() {
        log.info("Fetching all users");
        return this.persons.findAll();
    }

    public Optional<Person> findById(Long id) {
        log.info("Fetching user with id {}", id);
        return this.persons.findById(id);
    }

    public Person findByUsername(String username) {
        log.info("Fetching user {}", username);
        return this.persons.findByUsername(username);
    }

    public Person save(Person person) {
        log.info("Saving new user {} to the database", person.getUsername());
        Person fromBase = findByUsername(person.getUsername());
        if (fromBase != null) {
            person = fromBase;
        } else {
            Role role = this.roles.findByName("ROLE_USER");
            person.getRoles().add(role);
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return this.persons.save(person);
    }

    public void delete(Long id) {
        log.info("Delete user with id {} from database", id);
        this.persons.deleteById(id);
    }
}
