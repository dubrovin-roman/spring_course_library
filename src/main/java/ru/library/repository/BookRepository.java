package ru.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitleStartsWith(String startWith);
}
