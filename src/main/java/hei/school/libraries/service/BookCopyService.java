package hei.school.libraries.service;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.exception.ForbiddenException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.BookCopyMapper;
import hei.school.libraries.repository.BookCopyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;
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
}
