package com.example.crus.springcrud.repo;

import com.example.crus.springcrud.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepo extends JpaRepository<Book,Long> {
}
