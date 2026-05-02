package com.sg.crudemo.model;

public class BookAuthorJoinRow {

    private long bookId;
    private String bookTitle;
    private Integer publicationYear;
    private String genre;
    private Integer pages;
    private long authorId;
    private String authorName;
    private String nationality;

    public BookAuthorJoinRow() {
    }

    public BookAuthorJoinRow(Long bookId, String bookTitle, Integer publicationYear, String genre, Integer pages,
                             Long authorId, String authorName, String nationality) {
        if (bookId != null) {
            this.bookId = bookId;
        }
        this.bookTitle = bookTitle;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.pages = pages;
        if (authorId != null) {
            this.authorId = authorId;
        }
        this.authorName = authorName;
        this.nationality = nationality;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
