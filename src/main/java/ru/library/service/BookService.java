package ru.library.service;

import ru.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getAllBooksWithPagination(int page, int itemsPerPage);
    List<Book> getAllBooksSortByYearOfPublication();
    List<Book> getAllBooksWithPaginationAndSortByYearOfPublication(int page, int itemsPerPage);
    void saveBook(Book book);
    Book getBook(long id);
    void deleteBook(long id);
    List<Book> getAllByTitleStartsWith(String startWith);
}
