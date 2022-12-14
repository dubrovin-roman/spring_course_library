package ru.library.service.impl;

import org.springframework.stereotype.Service;
import ru.library.model.Person;
import ru.library.repository.PersonRepository;
import ru.library.service.PersonService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person getPerson(long id) {
        Optional<Person> optional = personRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void savePerson(Person person) {
        if (person.getRegisterAt() == null)
            person.setRegisterAt(new Date());
        personRepository.save(person);
    }

    @Override
    public void deletePerson(long id) {
        Person personFromDB = personRepository.findById(id).orElse(null);
        if (personFromDB != null && !personFromDB.getBookList().isEmpty()) {
            personFromDB.removeAllBook();
            personRepository.save(personFromDB);
        }
        personRepository.deleteById(id);
    }
}
