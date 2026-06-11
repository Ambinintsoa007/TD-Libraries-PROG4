package hei.school.libraries.service;

import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;
  private final BookRepository bookRepository;

  public List<BookCopy> getAllBookCopies() {
    return bookCopyRepository.findAll();
  }

  public BookCopy getBookCopyById(String id) {
    return bookCopyRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("BookCopy not found : " + id));
  }

  public BookCopy createBookCopy(BookCopy bookCopy) {

    if (bookCopy.getBook() != null) {
      String bookId = bookCopy.getBook().getId();

      Book book =
          bookRepository
              .findById(bookId)
              .orElseThrow(() -> new RuntimeException("Book not found : " + bookId));

      bookCopy.setBook(book);
    }

    return bookCopyRepository.save(bookCopy);
  }

  public BookCopy patchBookCopy(String id, BookCopy bookCopy) {

    BookCopy found = getBookCopyById(id);

    if (bookCopy.getBook() != null) {
      String bookId = bookCopy.getBook().getId();

      Book book =
          bookRepository
              .findById(bookId)
              .orElseThrow(() -> new RuntimeException("Book not found : " + bookId));

      found.setBook(book);
    }

    if (bookCopy.getFormat() != null) {
      found.setFormat(bookCopy.getFormat());
    }

    if (bookCopy.getPrice() != null) {
      found.setPrice(bookCopy.getPrice());
    }

    if (bookCopy.getShelfLocation() != null) {
      found.setShelfLocation(bookCopy.getShelfLocation());
    }

    if (bookCopy.getStatus() != null) {
      found.setStatus(bookCopy.getStatus());
    }

    return bookCopyRepository.save(found);
  }

  public void deleteBookCopy(String id) {
    BookCopy found = getBookCopyById(id);
    bookCopyRepository.delete(found);
  }
}
