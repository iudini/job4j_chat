package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roles;

    public List<Role> findAll() {
        log.info("Fetching all roles");
        List<Role> rsl = new ArrayList<>();
        this.roles.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<Role> findById(Long id) {
        log.info("Fetching role with id {}", id);
        return this.roles.findById(id);
    }

    public Role save(Role role) {
        log.info("Saving new role {} to database", role.getName());
        return this.roles.save(role);
    }

    public void delete(Long id) {
        log.info("Delete role with id {} from database", id);
        this.roles.deleteById(id);
    }
}
