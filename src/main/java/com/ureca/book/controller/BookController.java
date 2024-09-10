package com.ureca.book.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.book.dto.Book;
import com.ureca.book.service.BookService;

@RestController   // JSON,XML서비스 (각 메서드의 @ResponseBody 생략 가능)
@CrossOrigin("*") // 자바스크립트 CORS허용
@RequestMapping("/book")
public class BookController {

	BookService bookService;

	public BookController(BookService bookService) {
		//생성자 주입 ==> 만약 컨트롤러 안에 한개의 생성자가 있다면 알아서 파라미터에 정의되어 있는 객체에 대하여  @Autowired가 적용됨
		this.bookService = bookService;
	}

	//도서추가-DB입력
	@PostMapping("/regist")
//	public int regist(@RequestBody Book book) throws SQLException {//Content-Type : application/json
	public int regist(Book book) throws SQLException {//Content-Type : multipart/form-data
		System.out.println("도서추가 요청>>>"+ book);
		return bookService.regist(book);
	}

	//도서수정-DB수정
	@PutMapping("/modify")
	public int modify(@RequestBody Book book)throws SQLException{
		return bookService.modify(book);
	}

	//도서삭제-DB삭제  /book?isbn=aa-bb-01   /book/aa-bb-01
	@DeleteMapping("/remove") //HTTP메서드   HTTP요청방식  ==>  post/put/delete/get
	public int remove(@RequestParam /*@PathVariable*/ String isbn)throws SQLException{
		return bookService.remove(isbn);
	}


	//도서조회(전체)-목록에 사용
	@GetMapping("/findAll")
	public List<Book> list()throws SQLException{
		return bookService.findAll();
	}
	
	//도서조회(조건 페이지)-목록에 사용
	@GetMapping("/findPage/{offset}/{len}")
	public List<Book> listByPage(
			     @PathVariable("offset") int offset,
			     @PathVariable("len") int len)throws SQLException{
		Map<String, Integer> map = new HashMap<>();
		    map.put("offset", offset);
		    map.put("len"   , len);
		return bookService.findPage(map);
	}
//	@GetMapping("/findPage")
//	public List<Book> listByPage()throws SQLException{
//		Map<String, Integer> map = new HashMap<>();
//		map.put("offset", 10);
//		map.put("len"   , 10);
//		return bookService.findPage(map);
//	}

	//도서조회(한권)-상세조회,수정폼에 사용
	@GetMapping("/find")
	public Book searchBook(@RequestParam String isbn)throws SQLException{
		System.out.println("도서조회 요청>>"+ isbn);
		return bookService.find(isbn);
	}

}









