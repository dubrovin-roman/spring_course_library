package ru.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.library.model.LibraryEmployee;
import ru.library.repository.LibraryEmployeeRepository;
import ru.library.security.LibraryEmployeeDetails;

import java.util.Optional;

@Service
public class LibraryEmployeeDetailsServiceImpl implements UserDetailsService {

    private final LibraryEmployeeRepository libraryEmployeeRepository;

    @Autowired
    public LibraryEmployeeDetailsServiceImpl(LibraryEmployeeRepository libraryEmployeeRepository) {
        this.libraryEmployeeRepository = libraryEmployeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LibraryEmployee> optional = libraryEmployeeRepository.findByUsername(username);

        if (optional.isEmpty())
            throw new UsernameNotFoundException("LibraryEmployee with name " + username + " not found!");

        return new LibraryEmployeeDetails(optional.get());
    }
}
