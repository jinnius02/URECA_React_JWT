package com.ureca.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ureca.book.dto.Book;

@Mapper
public interface BookDAO {
    public int insert(Book book)throws SQLException;
    public int update(Book book)throws SQLException;
    public int delete(String isbn)throws SQLException;

    public List<Book> selectAll()throws SQLException;
    public List<Book> selectPage(Map<String, Integer> map)throws SQLException;

    public Book select(String isbn)throws SQLException;
    
}









