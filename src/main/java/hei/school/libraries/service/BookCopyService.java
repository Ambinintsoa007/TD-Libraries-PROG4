package hei.school.libraries.service;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;

  public List<BookCopy> getAllBookCopies() {
    return bookCopyRepository.findAll();
  }

  public BookCopy getBookCopyById(String id) {
    return bookCopyRepository.findById(id)
        .orElseThrow(() ->
            new RuntimeException("BookCopy not found : " + id));
  }

  public BookCopy createBookCopy(BookCopy bookCopy) {
    return bookCopyRepository.save(bookCopy);
  }

  public BookCopy patchBookCopy(String id, BookCopy bookCopy) {

    BookCopy found = getBookCopyById(id);

    if (bookCopy.getBook() != null)
      found.setBook(bookCopy.getBook());

    if (bookCopy.getLibrary() != null)
      found.setLibrary(bookCopy.getLibrary());

    if (bookCopy.getFormat() != null)
      found.setFormat(bookCopy.getFormat());

    if (bookCopy.getPrice() != null)
      found.setPrice(bookCopy.getPrice());

    if (bookCopy.getShelfLocation() != null)
      found.setShelfLocation(bookCopy.getShelfLocation());

    if (bookCopy.getStatus() != null)
      found.setStatus(bookCopy.getStatus());

    return bookCopyRepository.save(found);
  }

  public void deleteBookCopy(String id) {
    bookCopyRepository.deleteById(id);
  }
}