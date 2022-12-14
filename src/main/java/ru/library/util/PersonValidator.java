package ru.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.library.model.Person;
import ru.library.repository.PersonRepository;

@Component
public class PersonValidator implements Validator {

    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personRepository.existsByFullName(person.getFullName()))
            errors.rejectValue("fullName", "", "Человек с таким именем уже существует");
    }
}
