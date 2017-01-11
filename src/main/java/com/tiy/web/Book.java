package com.tiy.web;

import javax.persistence.*;

/**
 * Created by crci1 on 1/10/2017.
 */

@Entity
@Table(name = "books")
public class Book {

    @ManyToOne
    User user;

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String author;

    @Column(nullable = false)
    String genre;

    @Column(nullable = false)
    boolean checkedOut;

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Book(String title, String author, String genre, boolean checkedOut, User user) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkedOut = checkedOut;
        this.user = user;

    }
}
