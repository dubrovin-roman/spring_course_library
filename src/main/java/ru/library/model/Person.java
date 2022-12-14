package ru.library.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", unique = true, nullable = false)
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Пример: Иванов Иван Иванович")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "register_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerAt;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Book> bookList;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date yearOfBirth) {
        this.dateOfBirth = yearOfBirth;
    }

    public Date getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Date registerAt) {
        this.registerAt = registerAt;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void addBook(Book book) {
        if (bookList == null)
            bookList = new ArrayList<>();
        bookList.add(book);
        book.setTakeBookAt(LocalDateTime.now());
        book.setPerson(this);
    }

    public void removeBook(Book book) {
        if (bookList != null) {
            bookList.remove(book);
            book.setPerson(null);
        }

    }

    public void removeAllBook() {
        if (bookList != null) {
            bookList.forEach(book -> {
                book.setPerson(null);
            });
            Iterator<Book> bookIterator = bookList.iterator();
            while (bookIterator.hasNext()) {
                bookIterator.next();
                bookIterator.remove();
            }
        }
    }
}
