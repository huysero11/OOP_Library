package com.example.oop_library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserConstructorWithAllFields() {
        User user = new User(1, "John Doe", "1234567890", "password123", true);

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    void testUserConstructorWithoutAdmin() {
        User user = new User(2, "Jane Doe", "0987654321", "securePass");

        assertEquals(2, user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("0987654321", user.getPhoneNumber());
        assertEquals("securePass", user.getPassword());
        assertFalse(user.isAdmin());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User(0, "Initial", "0000000000", "initPass");

        user.setId(10);
        user.setName("Updated Name");
        user.setPhoneNumber("1112223333");
        user.setPassword("newPass123");
        user.setAdmin(true);

        assertEquals(10, user.getId());
        assertEquals("Updated Name", user.getName());
        assertEquals("1112223333", user.getPhoneNumber());
        assertEquals("newPass123", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    void testToString() {
        User user = new User(3, "Alice", "5555555555", "password321", false);

        String expected = "id = 3, name = Alice, phoneNumber = 5555555555, password = password321, admin = false";
        assertEquals(expected, user.toString());
    }

    @Test
    void testGetBorrowedBooks() {
        User user = new User(2, "Hung", "05");

        assertNotNull(user.getBorrowedBooks());
    }
}
