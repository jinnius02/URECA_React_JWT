package com.ureca.book.dto;

import org.springframework.web.multipart.MultipartFile;

public class Book {// 도서와 관련된 속성들을 저장하는 객체
	private String isbn;
	private String title;
	private String author;
	private int price;
	private String desc;
	private String originImg;//이미지파일명 또는 원래 경로와 이미지파일명 저장 
	private String saveImg;//이미지파일명 또는  저장되 경로와 이미지파일명 저장 
	
	private MultipartFile upfile;//업로드 파일 저장 객체 
	//여러파일의 경우 List<MultipartFile> 사용하면 됨
	

    public Book() {
		// TODO Auto-generated constructor stub
	}
	public Book(String isbn, String title, String author, int price, String desc, String originImg, String saveImg,
			MultipartFile upfile) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.desc = desc;
		this.originImg = originImg;
		this.saveImg = saveImg;
		this.upfile = upfile;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOriginImg() {
		return originImg;
	}
	public void setOriginImg(String originImg) {
		this.originImg = originImg;
	}
	public String getSaveImg() {
		return saveImg;
	}
	public void setSaveimg(String saveImg) {
		this.saveImg = saveImg;
	}
	public MultipartFile getUpfile() {
		return upfile;
	}
	public void setUpfile(MultipartFile upfile) {
		this.upfile = upfile;
	}
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", price=" + price + ", desc=" + desc
				+ ", originImg=" + originImg + ", saveImg=" + saveImg + ", upfile=" + upfile + "]";
	}
    

}
