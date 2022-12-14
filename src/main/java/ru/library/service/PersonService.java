package ru.library.service;

import ru.library.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPeople();

    Person getPerson(long id);

    void savePerson(Person person);

    void deletePerson(long id);
}
