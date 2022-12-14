package ru.library.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.library.model.LibraryEmployee;

import java.util.Collection;

public class LibraryEmployeeDetails implements UserDetails {
    private LibraryEmployee libraryEmployee;

    public LibraryEmployeeDetails(LibraryEmployee libraryEmployee) {
        this.libraryEmployee = libraryEmployee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.libraryEmployee.getPassword();
    }

    @Override
    public String getUsername() {
        return this.libraryEmployee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно, чтобы получать данные аутентифицированного работника.
    public LibraryEmployee getLibraryEmployee() {
        return libraryEmployee;
    }
}
