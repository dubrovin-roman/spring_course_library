package ru.library.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotEmpty(message = "Поле не должно быть пустым")
    private String title;

    @Column(name = "author", nullable = false)
    @NotEmpty(message = "Поле не должно быть пустым")
    private String author;

    @Column(name = "year_of_publication")
    @Min(value = 1, message = "Минимальное значение 1")
    @Max(value = 2022, message = "Максимальное значение 2022")
    private int yearOfPublication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "take_book_at")
    private LocalDateTime takeBookAt;

    @Transient
    private boolean isOverdue;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getTakeBookAt() {
        return takeBookAt;
    }

    public void setTakeBookAt(LocalDateTime takeBookAt) {
        this.takeBookAt = takeBookAt;
    }

    public boolean isOverdue() {
        return takeBookAt.plusDays(10).isBefore(LocalDateTime.now()) ;
    }

    public boolean isHaveReader() {
        return person != null;
    }
}
