package ru.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByFullName(String fullName);
}
