package com.example.jdbcexample;

import com.example.jdbcexample.model.Book;
import com.example.jdbcexample.old.BookJdbcExample;

import java.sql.*;
import java.math.BigDecimal;

public class JdbcExampleApplication {

    public static void main(String[] args) {
        // Создаем книгу
        Book book = new Book();
        book.setTitle("Война и мир");
        book.setAuthor("Лев Толстой");
        book.setPublishYear(1969);
        book.setPublisher("Неуказано");
        book.setPrice(new BigDecimal("5.99"));

        // Сохраняем
        Long id = BookJdbcExample.saveBook(book);
        System.out.println("Книга сохранена с ID: " + id);

        // Получаем
        Book foundBook = BookJdbcExample.findBookById(id);
        System.out.println("=========================");
        System.out.println("Найдена книга: " + foundBook.getTitle());

        // Обновляем

        // Удаляем
        System.out.println("=========================");
        BookJdbcExample.deleteBook(id);
        System.out.println("Книга удалена");
    }

}

