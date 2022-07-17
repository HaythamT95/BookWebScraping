package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
@Service
public class BookService {
	@Autowired
	JdbcTemplate template;
	public List<Book> bookList=new ArrayList<>();
	public Set<Book> bookNames=new HashSet<Book>();
	
	//Return all books details from data base
	public List<Book> getBookNames(){
		String sql = "select idBook,bookName,author,bookCover from book";
		RowMapper<Book> rm = new RowMapper<Book>() {
			@Override
			public Book mapRow(ResultSet resultSet, int i) throws SQLException {
				byte[] encodeBase64 = Base64.encodeBase64(ReadBlob(resultSet,4));
				String imgBase64 = null;
				try {
					imgBase64 = new String(encodeBase64, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Book book = new Book(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),imgBase64);
				bookNames.add(book);
				return book;
			}
		};
		return template.query(sql, rm);
	}
	
	//Given ID of book
	//Returns byte array for the requested book 
	public List<byte[]> getPDFOfBook(int id) {
		String sql = "select bookPDF from book where idBook="+id;
		RowMapper<byte[]> rm = new RowMapper<byte[]>() {
			public byte[] mapRow(ResultSet resultSet, int i) throws SQLException {
				byte[] pdf=ReadBlob(resultSet,1);
				return pdf;
			}
		};
		return template.query(sql, rm);
	}
	
	//Get Blob from Data base and convert to byte Array
	private byte[] ReadBlob(ResultSet resultSet,int column) throws SQLException {
		 Blob blob = resultSet.getBlob(column);
		 byte [] bytes = blob.getBytes(1, (int)blob.length());
         return bytes;	
	}
	
	//filter book names given input
	public List<Book> getStrings(final String input) {
		return bookNames.stream().filter(s -> s.getName().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toList());
	}
}
