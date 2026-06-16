package hei.school.libraries.service;

import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Library;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import hei.school.libraries.repository.LibraryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;
  private final BookRepository bookRepository;
  private final LibraryRepository libraryRepository;

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

    if (bookCopy.getLibrary() != null) {
      String libraryId = bookCopy.getLibrary().getId();

      Library library =
          libraryRepository
              .findById(libraryId)
              .orElseThrow(() -> new RuntimeException("Library not found : " + libraryId));

      bookCopy.setLibrary(library);
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

    if (bookCopy.getLibrary() != null) {
      String libraryId = bookCopy.getLibrary().getId();

      Library library =
          libraryRepository
              .findById(libraryId)
              .orElseThrow(() -> new RuntimeException("Library not found : " + libraryId));

      found.setLibrary(library);
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

    BookCopy found =
        bookCopyRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("BookCopy not found : " + id));

    bookCopyRepository.delete(found);
  }
}
