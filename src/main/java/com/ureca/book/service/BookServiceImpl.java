package com.ureca.book.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ureca.book.dao.BookDAO;
import com.ureca.book.dto.Book;

import jakarta.servlet.ServletContext;

@Service
public class BookServiceImpl implements BookService {

	private final BookDAO bookDAO;

	@Autowired
	ServletContext application;//서버컨텍스트 정보를 담는 객체

	public BookServiceImpl( BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	@Override
	public int regist(Book book) throws SQLException {//도서정보등록
		uploadFile( book );//book.getUpfile()  );
		return bookDAO.insert(book); //img==> null  ==> "react.png"
	}

	private void uploadFile(Book book) { //MultipartFile mfile){//파일업로드 처리

		try {
			MultipartFile mfile = book.getUpfile();
			String fileName = mfile.getOriginalFilename();

			book.setOriginImg(fileName); //DB에 파일명이 저장되고 싶다 null  ==> "react.png"
			book.setSaveimg(UUID.randomUUID().toString()+fileName);

			System.out.println("업로드 파일명:"+ fileName);
			byte[] inFile= mfile.getBytes();



			System.out.println("실제 경로: "+ application.getRealPath(""));

			//웹서버의 실제 하드디스크 (위치)경로 찾기
			String filePath = application.getRealPath("")+"upload"; // 현재 실행 중인 실제 경로
//		   String filePath = new File("src/main/resources/static/upload").getAbsolutePath(); // -> 쉼의 쑤뤠기 코드,, (실제 하드 경로)

			File file = new File(filePath);
			if(!file.exists()) {
				//upload폴더 생성!!
				file.mkdirs();
			}

			System.out.println("서버경로(프로젝트경로):"+ filePath);
// => 서버경로(프로젝트경로):C:\Users\student\AppData\Local\Temp\tomcat-docbase.8080.12369757972387596250/upload
//		   File outFile = new File("c:/ureca/"+ fileName);
			File outFile = new File(filePath, book.getSaveImg());

			FileCopyUtils.copy(inFile, outFile);//서버(특정위치)에 업로드하기

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int modify(Book book) throws SQLException {
		return bookDAO.update(book);
	}

	@Override
	public int remove(String isbn) throws SQLException {
		return bookDAO.delete(isbn);
	}

	@Override
	public List<Book> findAll() throws SQLException {
		return bookDAO.selectAll();
	}

	@Override
	public Book find(String isbn) throws SQLException {
		return bookDAO.select(isbn);
	}

	@Override
	public List<Book> findPage(Map<String, Integer> map) throws SQLException {
		return bookDAO.selectPage(map);
	}

}









