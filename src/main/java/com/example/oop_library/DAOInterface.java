package com.example.oop_library;

import java.util.ArrayList;

public interface DAOInterface<Obj> {
    public int add(Obj obj);

    public int update(Obj obj);

    public int delete(Obj obj);

    public ArrayList<Obj> getAll();

    public Obj getById(int id);

    public ArrayList<Obj> getByCondition(String condition);
}
