package com.example.crus.springcrud.controller;

import com.example.crus.springcrud.Model.Book;
import com.example.crus.springcrud.repo.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class BookController {

    @Autowired
    private BooksRepo booksRepo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> bookList = new ArrayList<>(booksRepo.findAll());
            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBooksById(@PathVariable Long id){
        Optional<Book> bookData = booksRepo.findById(id);
        return bookData.map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book bookObj =  booksRepo.save(book);
        return new ResponseEntity<>(bookObj,HttpStatus.OK);
    }

    @PostMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id,@RequestBody Book newBookData){
        Optional<Book> oldBook = booksRepo.findById(id);
        if(oldBook.isPresent()){
            Book updatedBook = oldBook.get();
            updatedBook.setTitle(newBookData.getTitle());
            updatedBook.setAuthor(newBookData.getAuthor());

            Book book = booksRepo.save(updatedBook);
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long id){
        booksRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
