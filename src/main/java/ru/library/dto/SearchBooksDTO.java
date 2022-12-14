package ru.library.dto;

public class SearchBooksDTO {
    private String startWith;

    public SearchBooksDTO() {
    }

    public String getStartWith() {
        return startWith;
    }

    public void setStartWith(String startWith) {
        this.startWith = startWith;
    }
}
