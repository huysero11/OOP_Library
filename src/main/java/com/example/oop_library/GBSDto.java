package com.example.oop_library;

import java.util.*;

public class GBSDto {
    private String type;
    private int totalItems;
    private List<GBSBooks> items;

    public int getTotalItems() {
        return totalItems;
    }

    public List<GBSBooks> getItems() {
        return items;
    }
}