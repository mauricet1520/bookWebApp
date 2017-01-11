package com.tiy.web;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by crci1 on 1/10/2017.
 */
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByGenre(String genre);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByUser(User user);
    List<Book>findByUserAndGenre(User user, String genre);



    @Query("SELECT b FROM Book b WHERE b.title LIKE ?1% AND b.user.id = ?2")
    List<Book> findByTitleStartsWith(String title, Integer id);

    @Query("SELECT b FROM Book b WHERE b.author LIKE ?1% AND b.user.id = ?2")
    List<Book> findByAuthorStartsWith(String author, Integer id);



}
