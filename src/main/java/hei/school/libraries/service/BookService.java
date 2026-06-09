package hei.school.libraries.service;

import hei.school.libraries.entity.Book;
import hei.school.libraries.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found : " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(String id, Book book) {
        Book found = getBookById(id);
        found.setTitle(book.getTitle());
        found.setLanguage(book.getLanguage());
        found.setDescription(book.getDescription());
        found.setCoverUrl(book.getCoverUrl());
        found.setPublicationDate(book.getPublicationDate());
        return bookRepository.save(found);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}