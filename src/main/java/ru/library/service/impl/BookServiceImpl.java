package ru.library.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.library.model.Book;
import ru.library.model.Person;
import ru.library.repository.BookRepository;
import ru.library.repository.PersonRepository;
import ru.library.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookServiceImpl(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllBooksWithPagination(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    @Override
    public List<Book> getAllBooksSortByYearOfPublication() {
        return bookRepository.findAll(Sort.by("yearOfPublication"));
    }

    @Override
    public List<Book> getAllBooksWithPaginationAndSortByYearOfPublication(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("yearOfPublication"))).getContent();
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBook(long id) {
        Optional<Book> optional = bookRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deleteBook(long id) {
        Book bookFromDB = bookRepository.findById(id).orElse(null);
        if (bookFromDB != null && bookFromDB.getPerson() != null) {
            Person person = bookFromDB.getPerson();
            person.removeBook(bookFromDB);
            personRepository.save(person);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAllByTitleStartsWith(String startWith) {
        return bookRepository.findAllByTitleStartsWith(startWith);
    }
}
