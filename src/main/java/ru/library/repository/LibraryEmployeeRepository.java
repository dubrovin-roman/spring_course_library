package ru.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.LibraryEmployee;

import java.util.Optional;

public interface LibraryEmployeeRepository extends JpaRepository<LibraryEmployee, Long> {
    Optional<LibraryEmployee> findByUsername(String username);
}
