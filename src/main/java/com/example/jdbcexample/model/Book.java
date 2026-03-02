package com.example.jdbcexample.model;

import java.math.BigDecimal;


    public class Book {
        private Long id;
        private String title;
        private String author;
        private Integer publishYear;
        private String publisher;
        private BigDecimal price;

        public Book() {
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public Integer getPublishYear() {
            return publishYear;
        }

        public String getPublisher() {
            return publisher;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setPublishYear(Integer publishYear) {
            this.publishYear = publishYear;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }


/*
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publish_year INT,
    publisher VARCHAR(255),
    price DECIMAL(10, 2)
);
 */

/*
Java Object <--> ORM (Object-Relational Mapping) Framework <--> Database Table
Book <--> Hibernate ORM Framework <--> books
 */

/*
JPA - интерфейс, который определяет методы для работы с базой данных. (List<T>)
Hibernate ORM Framework - реализация JPA. (ArrayList<T>)
 */

