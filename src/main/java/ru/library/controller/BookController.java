package ru.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.dto.SearchBooksDTO;
import ru.library.model.Book;
import ru.library.model.Person;
import ru.library.service.BookService;
import ru.library.service.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String showAllBooks(@RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                               @RequestParam(name = "sort_by_year", required = false) Boolean sortByYear,
                               Model model) {
        if (page == null && booksPerPage == null && sortByYear == null) {
            model.addAttribute("books", bookService.getAllBooks());
        } else if (sortByYear == null) {
            if (page != null && booksPerPage != null)
                model.addAttribute("books", bookService.getAllBooksWithPagination(page, booksPerPage));
        } else {
            if (page != null && booksPerPage != null && sortByYear)
                model.addAttribute("books", bookService.getAllBooksWithPaginationAndSortByYearOfPublication(page, booksPerPage));
            else if (sortByYear && page == null && booksPerPage == null)
                model.addAttribute("books", bookService.getAllBooksSortByYearOfPublication());
        }

        return "book/all_books";
    }

    @GetMapping("/new")
    public String formForNewBook(@ModelAttribute("book") Book book) {
        return "book/new_book";
    }

    @PostMapping
    private String createNewBook(@ModelAttribute("book") @Valid Book book,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new_book";

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") long id,
                           Model model) {
        model.addAttribute("book", bookService.getBook(id));
        model.addAttribute("people", personService.getAllPeople());
        model.addAttribute("person", new Person());
        return "book/show_book";
    }

    @PostMapping("/{id}")
    public String assignBook(@PathVariable("id") long id,
                             @ModelAttribute("person") Person person) {
        Book bookFromDB = bookService.getBook(id);
        Person personFromDB = personService.getPerson(person.getId());
        personFromDB.addBook(bookFromDB);
        personService.savePerson(personFromDB);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/free")
    public String freeBook(@PathVariable("id") long id) {
        Book bookFromDB = bookService.getBook(id);
        Person person = bookFromDB.getPerson();
        person.removeBook(bookFromDB);
        personService.savePerson(person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String formForEditBook(@PathVariable("id") long id,
                                  Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "book/edit_book";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") long id,
                           @ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/edit_book";

        Book bookFromDB = bookService.getBook(id);
        bookFromDB.setTitle(book.getTitle());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setYearOfPublication(book.getYearOfPublication());
        bookService.saveBook(bookFromDB);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String formForSearchBooks(@ModelAttribute("searchBooksDTO") SearchBooksDTO searchBooksDTO) {
        return "book/search_books";
    }

    @PostMapping("/search")
    public String searchBooks(@ModelAttribute("searchBooksDTO") SearchBooksDTO searchBooksDTO,
                              Model model) {
        model.addAttribute("books", bookService.getAllByTitleStartsWith(searchBooksDTO.getStartWith()));
        searchBooksDTO.setStartWith(null);
        return "book/found_books";
    }
}
