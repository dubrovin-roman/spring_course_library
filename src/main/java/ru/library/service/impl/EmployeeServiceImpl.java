package ru.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.library.model.LibraryEmployee;
import ru.library.repository.LibraryEmployeeRepository;
import ru.library.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final LibraryEmployeeRepository libraryEmployeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(LibraryEmployeeRepository libraryEmployeeRepository, PasswordEncoder passwordEncoder) {
        this.libraryEmployeeRepository = libraryEmployeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(LibraryEmployee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        libraryEmployeeRepository.save(employee);
    }
}
