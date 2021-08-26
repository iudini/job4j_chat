package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roles;

    public RoleService(RoleRepository roles) {
        this.roles = roles;
    }

    public List<Role> findAll() {
        List<Role> rsl = new ArrayList<>();
        this.roles.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<Role> findById(Long id) {
        return this.roles.findById(id);
    }

    public Role save(Role role) {
        return this.roles.save(role);
    }

    public void delete(Long id) {
        this.roles.deleteById(id);
    }
}
