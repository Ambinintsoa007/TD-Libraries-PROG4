package hei.school.libraries.service;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.ForbiddenException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.BookCopyMapper;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;
  private final BookRepository bookRepository;
  private final BookCopyMapper bookCopyMapper;

  public List<BookCopy> getAllBookCopies() {
    return bookCopyRepository.findAll();
  }

  public BookCopy getBookCopyById(String id) {
    return bookCopyRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("BookCopy not found : " + id));
  }

  public BookCopy createBookCopy(BookCopy bookCopy) {
    return bookCopyRepository.save(bookCopy);
  }

  public BookCopy patchBookCopy(String id, BookCopy bookCopy) {
    BookCopy found = getBookCopyById(id);
    bookCopyMapper.patch(found, bookCopy);
    return bookCopyRepository.save(found);
  }

  public void deleteBookCopy(String id) {
    BookCopy found = getBookCopyById(id);
    if (found.getStatus() == Status.RESERVED) {
      throw new ForbiddenException("Cannot delete a reserved book copy");
    }
    bookCopyRepository.deleteById(id);
  }

  public List<BookCopy> searchBookCopies(String bookId, Status status) {
    if (bookId != null) {
      validateBookExists(bookId);
    }

    if (bookId != null && status != null) {
      return bookCopyRepository.findByBook_IdAndStatus(bookId, status);
    }
    if (bookId != null) {
      return bookCopyRepository.findByBook_Id(bookId);
    }
    if (status != null) {
      return bookCopyRepository.findByStatus(status);
    }
    return bookCopyRepository.findAll();
  }

  private void validateBookExists(String bookId) {
    try {
      UUID.fromString(bookId);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid book id format : " + bookId);
    }
    if (!bookRepository.existsById(bookId)) {
      throw new NotFoundException("Book not found : " + bookId);
    }
  }
}
