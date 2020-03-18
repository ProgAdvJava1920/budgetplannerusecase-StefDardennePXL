package be.pxl.student.entity;

import java.util.List;

public interface DAO<T, E extends Exception> {
    T create(T t) throws E;

    T getById(int id) throws E;

    List<T> getAll() throws E;

    T update(T t) throws E;

    T delete(T t) throws E;
}
