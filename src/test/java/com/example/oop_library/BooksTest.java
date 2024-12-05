package com.example.oop_library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BooksTest {

    private Books book;

    @BeforeEach
    void setUp() {
        book = new Books(
                "12345",
                "Test Book",
                "John Doe",
                "2023",
                "https://example.com/thumbnail.jpg",
                "Fiction",
                "A test book description.",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                false,
                null
        );
    }

    @Test
    void testGetBookID() {
        assertEquals("12345", book.getBookID());
    }

    @Test
    void testSetBookID() {
        book.setBookID("54321");
        assertEquals("54321", book.getBookID());
    }

    @Test
    void testGetBookName() {
        assertEquals("Test Book", book.getBookName());
    }

    @Test
    void testSetBookName() {
        book.setBookName("New Test Book");
        assertEquals("New Test Book", book.getBookName());
    }

    @Test
    void testGetBookAuthor() {
        assertEquals("John Doe", book.getBookAuthor());
    }

    @Test
    void testSetBookAuthor() {
        book.setBookAuthor(Collections.singletonList("Jane Smith"));
        assertEquals("Jane Smith", book.getBookAuthor());
    }

    @Test
    void testGetBookPublicationYear() {
        assertEquals("2023", book.getBookPublicationYear());
    }

    @Test
    void testSetBookPublicationYear() {
        book.setBookPublicationYear("2024");
        assertEquals("2024", book.getBookPublicationYear());
    }

    @Test
    void testGetThumbNail() {
        assertEquals("https://example.com/thumbnail.jpg", book.getThumbNail());
    }

    @Test
    void testSetBorrowed() {
        book.setBorrowed(true);
        assertTrue(book.isBorrowed());
    }

    @Test
    void testGetCatagory() {
        assertEquals("Fiction", book.getCatagory());
    }

    @Test
    void testSetCatagory() {
        book.setCatagory(Collections.singletonList("Non-Fiction"));
        assertEquals("Non-Fiction", book.getCatagory());
    }

    @Test
    void testGetBorrowedDate() {
        assertEquals(LocalDate.of(2024, 1, 1), book.getBorrowedDate());
    }

    @Test
    void testSetBorrowedDate() {
        book.setBorrowedDate(LocalDate.of(2024, 1, 15));
        assertEquals(LocalDate.of(2024, 1, 15), book.getBorrowedDate());
    }

    @Test
    void testGetReturnDate() {
        assertEquals(LocalDate.of(2024, 2, 1), book.getReturnDate());
    }

    @Test
    void testSetReturnDate() {
        book.setReturnDate(LocalDate.of(2024, 2, 15));
        assertEquals(LocalDate.of(2024, 2, 15), book.getReturnDate());
    }

    @Test
    void testGetDescription() {
        assertEquals("A test book description.", book.getDescription());
    }

    @Test
    void testSetDescription() {
        book.setDescription("Updated description.");
        assertEquals("Updated description.", book.getDescription());
    }
}
