package ru.library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.library.model.LibraryEmployee;
import ru.library.service.EmployeeService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final EmployeeService employeeService;

    @Autowired
    public AuthController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("employee")LibraryEmployee libraryEmployee) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("employee") LibraryEmployee libraryEmployee) {
        employeeService.register(libraryEmployee);
        return "redirect:/people";
    }
}
