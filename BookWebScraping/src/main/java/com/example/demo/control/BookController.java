package com.example.demo.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;

@Controller
public class BookController {
	@Autowired
	BookService service;
	static List<Book>books;

	//Home page, get all books and sort by ID
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		books=service.getBookNames();
		int totalPages=(int) Math.ceil((Double)(books.size()/5.0));
		Collections.sort(books);
		Set<Book>bookSet=new TreeSet<Book>();
		bookSet.addAll(books.subList(0, 5));
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("list",bookSet);
		return "index";
	}
	
	//used for paging
	@RequestMapping(value="/{page}", method=RequestMethod.GET)
	public String page(Model model,@PathVariable (value="page") int page) {
		books=service.getBookNames();
		int totalPages=(int) Math.ceil((Double)(books.size()/5.0));
		int from,to;
		Collections.sort(books);
		if(page==1) {
			from=0;
			to=5;
		}
		else {
			from=page*5-5;
			if(books.size()<page*5)
				to=books.size();
			else
				to=page*5;
		}
		Set<Book>bookSet=new TreeSet<Book>();
		bookSet.addAll(books.subList(from, to));
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("list",bookSet);
		return "index";
	}
	
	
	//Download book to user PC
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public void downloadPDF(HttpServletResponse response,@Param(value="id") int id) throws IOException {
		List<byte[]> pdfList = new ArrayList<>();
		byte[] pdf=null;
		String name = null;
		pdfList=service.getPDFOfBook(id);
		pdf=pdfList.get(0);
		for(Book book:service.bookNames) {
			if(book.getID()==id) {
				name=book.getName();
			}
		}
		response.setContentType("application/octet-stream");
		String headerKey="Content-Disposition";
		String headerValue="attachment; filename="+name+".pdf";
		response.setHeader(headerKey, headerValue);
		ServletOutputStream ops=response.getOutputStream();
		ops.write(pdf);
	}
	
	//get relevant books when user enter string
	@RequestMapping("/search")
    public String searchBook(Model model, @Param("keyword") String keyword) {
		books=service.getBookNames();
		Collections.sort(books);
		Set<Book>bookSet=new TreeSet<Book>();
		for(int i=0;i<books.size();i++) {
			if(books.get(i).getName().contains(keyword))
				bookSet.add(books.get(i));
		}
		int totalPages=(int) Math.ceil((Double)(bookSet.size()/5.0));
		model.addAttribute("totalPages", totalPages);
        model.addAttribute("list", bookSet);
        model.addAttribute("keyword", keyword);
        return "index";
    }
	
	//auto complete search when user is searching for book
	@RequestMapping(value = "/AutoCompleteSearch")
	@ResponseBody
	public Set<String> getTechList(@RequestParam(value="term", required = false, defaultValue="") String term) {
	   Set<Book> bookSearchList=service.bookNames;
	   Set<String> result = new TreeSet<String>();
	   for (Book book : bookSearchList) {
	       if (book.getName().contains(term)) {
	         result.add(book.getName());
	       }
	   }
	   return result;
	}
}
