package ru.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.model.Person;
import ru.library.service.PersonService;
import ru.library.util.PersonValidator;

import javax.validation.Valid;

@Controller
public class PersonController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping("/people")
    public String showAllPeople(Model model) {
        model.addAttribute("people", personService.getAllPeople());
        return "person/all_people";
    }

    @GetMapping("/people/{id}")
    public String showPerson(@PathVariable("id") long id,
                             Model model) {
        model.addAttribute("person", personService.getPerson(id));
        return "person/show_person";
    }

    @GetMapping("/people/new")
    public String formForNewPerson(@ModelAttribute("person") Person person) {
        return "person/new_person";
    }

    @PostMapping("/people")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "person/new_person";

        personService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String formForEditPerson(@PathVariable("id") long id,
                                    Model model) {
        model.addAttribute("person", personService.getPerson(id));
        return "person/edit_person";
    }

    @PatchMapping("/people/{id}")
    public String editPerson(@PathVariable("id") long id,
                             @ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "person/edit_person";

        Person personFromDB = personService.getPerson(id);
        personFromDB.setFullName(person.getFullName());
        personFromDB.setDateOfBirth(person.getDateOfBirth());
        personService.savePerson(personFromDB);
        return "redirect:/people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }
}
