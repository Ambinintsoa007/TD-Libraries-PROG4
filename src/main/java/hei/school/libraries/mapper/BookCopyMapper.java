package hei.school.libraries.mapper;

import hei.school.libraries.dto.BookCopyRequest;
import hei.school.libraries.dto.BookCopyResponse;
import hei.school.libraries.entity.BookCopy;
import org.springframework.stereotype.Component;

@Component
public class BookCopyMapper {

  public BookCopyResponse toResponse(BookCopy bookCopy) {
    return new BookCopyResponse(
        bookCopy.getId(),
        bookCopy.getBook() != null ? bookCopy.getBook().getId() : null,
        bookCopy.getLibrary() != null ? bookCopy.getLibrary().getId() : null,
        bookCopy.getFormat(),
        bookCopy.getPrice(),
        bookCopy.getShelfLocation(),
        bookCopy.getStatus());
  }

  public void patch(BookCopy found, BookCopyRequest update) {
    if (update.format() != null) found.setFormat(update.format());
    if (update.price() != null) found.setPrice(update.price());
    if (update.shelfLocation() != null) found.setShelfLocation(update.shelfLocation());
    if (update.status() != null) found.setStatus(update.status());
  }
}
