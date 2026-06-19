package hei.school.libraries.mapper;

import hei.school.libraries.entity.BookCopy;
import org.springframework.stereotype.Component;

@Component
public class BookCopyMapper {

  public void patch(BookCopy found, BookCopy update) {
    if (update.getBook() != null) found.setBook(update.getBook());
    if (update.getLibrary() != null) found.setLibrary(update.getLibrary());
    if (update.getFormat() != null) found.setFormat(update.getFormat());
    if (update.getPrice() != null) found.setPrice(update.getPrice());
    if (update.getShelfLocation() != null) found.setShelfLocation(update.getShelfLocation());
    if (update.getStatus() != null) found.setStatus(update.getStatus());
  }
}
