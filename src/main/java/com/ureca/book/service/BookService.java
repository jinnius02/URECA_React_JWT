package com.ureca.book.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ureca.book.dto.Book;

public interface BookService {
    public int regist(Book book)throws SQLException;
    public int modify(Book book)throws SQLException;
    public int remove(String isbn)throws SQLException;

    public List<Book> findAll()throws SQLException;
    public List<Book> findPage(Map<String, Integer> map)throws SQLException;
    public Book find(String isbn)throws SQLException;
}
