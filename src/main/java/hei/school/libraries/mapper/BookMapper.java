package hei.school.libraries.mapper;

import hei.school.libraries.Dto.BookResponse;
import hei.school.libraries.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

  public BookResponse toResponse(Book book) {
    return new BookResponse(
        book.getId(),
        book.getTitle(),
        book.getIsbn(),
        book.getLanguage(),
        book.getDescription(),
        book.getCoverUrl(),
        book.getPublicationDate());
  }

  public void patch(Book found, Book update) {
    if (update.getTitle() != null) found.setTitle(update.getTitle());
    if (update.getIsbn() != null) found.setIsbn(update.getIsbn());
    if (update.getLanguage() != null) found.setLanguage(update.getLanguage());
    if (update.getDescription() != null) found.setDescription(update.getDescription());
    if (update.getCoverUrl() != null) found.setCoverUrl(update.getCoverUrl());
    if (update.getPublicationDate() != null) found.setPublicationDate(update.getPublicationDate());
  }
}
