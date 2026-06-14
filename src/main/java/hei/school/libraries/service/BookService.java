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
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found : " + id));
  }

  public Book createBook(Book book) {
    return bookRepository.save(book);
  }

  public Book patchBook(String id, Book book) {
    Book found = getBookById(id);
    if (book.getTitle() != null) found.setTitle(book.getTitle());
    if (book.getIsbn() != null) found.setIsbn(book.getIsbn());
    if (book.getLanguage() != null) found.setLanguage(book.getLanguage());
    if (book.getDescription() != null) found.setDescription(book.getDescription());
    if (book.getCoverUrl() != null) found.setCoverUrl(book.getCoverUrl());
    if (book.getPublicationDate() != null) found.setPublicationDate(book.getPublicationDate());
    return bookRepository.save(found);
  }

  public void deleteBook(String id) {
    Book found = getBookById(id);
    bookRepository.delete(found);
  }
}
