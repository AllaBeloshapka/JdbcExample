package com.example.jdbcexample.old;

import com.example.jdbcexample.model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookJdbcExample {

    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    // ================= SAVE =================
    public static Long saveBook(Book book) {

        String sql = "INSERT INTO books (title, author, publish_year, publisher, price) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublishYear());
            statement.setString(4, book.getPublisher());
            statement.setBigDecimal(5, book.getPrice());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                book.setId(id);
                return id;
            }

            throw new RuntimeException("Не удалось получить ID после вставки");

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении книги: " + book.getTitle(), e);
        }
    }

    // ================= FIND BY ID =================
    public static Book findBookById(Long id) {

        String sql = "SELECT id, title, author, publish_year, publisher, price FROM books WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublishYear(resultSet.getInt("publish_year"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return book;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении книги: " + e.getMessage(), e);
        }
    }

    // ================= UPDATE =================
    public static Long updateBook(Book book) {

        // Check that the book ID is not null
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book ID cannot be null when updating");
        }

        // Update an existing book by its id
        // WHERE id = ? — to modify only one row
        String sql = "UPDATE books SET title = ?, author = ?, publish_year = ?, publisher = ?, price = ? WHERE id = ?";

        // Open a database connection and prepare the SQL statement
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set real values instead of ?
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublishYear());
            statement.setString(4, book.getPublisher());
            statement.setBigDecimal(5, book.getPrice());

            // Finally set the id — the database will use it to determine which row to update
            statement.setLong(6, book.getId());

            // Execute the update. Since this is UPDATE, use executeUpdate()
            int rowsAffected = statement.executeUpdate();

            // If no rows were affected — there is no book with such id
            if (rowsAffected == 0) {
                throw new RuntimeException("Book with ID " + book.getId() + " not found");
            }

            // Return the id of the updated book
            return book.getId();

        } catch (SQLException e) {
            // If an error occurred while working with the database
            throw new RuntimeException("Error updating book: " + book.getTitle(), e);
        }
    }
    // ================= DELETE =================
    public static void deleteBook(Long id) {

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Книга с ID " + id + " не найдена");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении книги: " + e.getMessage(), e);
        }
    }
}








