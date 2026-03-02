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

        //Проверяем, что ID книги не null
        if (book.getId() == null) {
            throw new IllegalArgumentException("ID книги не может быть null при обновлении");
        }

        // Обновляем уже существующую книгу по её id
        // WHERE id = ? - чтобы изменить только одну строку
        String sql = "UPDATE books SET title = ?, author = ?, publish_year = ?, publisher = ?, price = ? WHERE id = ?";

        // Открываем соединение с базой и подготавливаем SQL-запрос
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Подставляем реальные значения вместо ?
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublishYear());
            statement.setString(4, book.getPublisher());
            statement.setBigDecimal(5, book.getPrice());

            // В конце подставляем id — по нему база поймёт, какую строку менять
            statement.setLong(6, book.getId());

            // Выполняем обновление. Так как это UPDATE, используем executeUpdate
            int rowsAffected = statement.executeUpdate();

            // Если ни одна строка не изменилась — значит книги с таким id нет
            if (rowsAffected == 0) {
                throw new RuntimeException("Книга с ID " + book.getId() + " не найдена");
            }

            // Возвращаем id обновлённой книги
            return book.getId();

        } catch (SQLException e) {
            // Если произошла ошибка при работе с базой
            throw new RuntimeException("Ошибка при обновлении книги: " + book.getTitle(), e);
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








