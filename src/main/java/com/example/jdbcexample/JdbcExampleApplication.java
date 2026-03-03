package com.example.jdbcexample;

import com.example.jdbcexample.model.Book;
import com.example.jdbcexample.old.BookJdbcExample;

import java.sql.*;
import java.math.BigDecimal;

public class JdbcExampleApplication {

    public static void main(String[] args) {

        // Create a book
        Book book = new Book();
        book.setTitle("War and Peace");
        book.setAuthor("Leo Tolstoy");
        book.setPublishYear(1969);
        book.setPublisher("Not specified");
        book.setPrice(new BigDecimal("5.99"));

        // Save the book
        Long id = BookJdbcExample.saveBook(book);
        System.out.println("Book saved with ID: " + id);

        // Retrieve the book
        Book foundBook = BookJdbcExample.findBookById(id);
        System.out.println("=========================");
        System.out.println("Book found: " + foundBook.getTitle());

        // Update the book

        // Delete the book
        System.out.println("=========================");
        BookJdbcExample.deleteBook(id);
        System.out.println("Book deleted");
    }
}

