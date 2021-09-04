package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/**
 * Utility class for Services
 */
public class Services {
    /**
     * Method for updating objects fields store in repository. At the beginning searched all getters and setters of t,
     * after excluded methods with annotation @JsonIgnore, and update each field.
     * @param repo repository.
     * @param t object with fields, that must be updated, and have other fields with null value.
     * @param <T> class of updating object.
     * @return Optional of updated value, if updating was successful, or Optional.empty(), if it was wrong.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T extends Model> Optional<T> update(CrudRepository<T, Long> repo, T t) throws InvocationTargetException, IllegalAccessException {
        var currentOptional = repo.findById(t.getId());
        if (currentOptional.isEmpty()) {
            return Optional.empty();
        }
        var current = currentOptional.get();
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        var excludeMethods = new ArrayList<>();
        for (Method method : methods) {
            var name = method.getName();
            if (Arrays.stream(method.getAnnotations()).anyMatch(x -> x.annotationType().getName().equals("com.fasterxml.jackson.annotation.JsonIgnore"))) {
                excludeMethods.add(name.substring(3));
            }
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        excludeMethods.forEach(s -> {
            namePerMethod.remove("get" + s);
            namePerMethod.remove("set" + s);
        });
        for (String name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    return Optional.empty();
                }
                var newValue = getMethod.invoke(t);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        return Optional.of(repo.save(current));
    }
}
