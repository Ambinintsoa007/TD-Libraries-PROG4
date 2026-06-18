package hei.school.libraries.mapper;

import hei.school.libraries.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

  public void patch(Book found, Book update) {
    if (update.getTitle() != null) found.setTitle(update.getTitle());
    if (update.getIsbn() != null) found.setIsbn(update.getIsbn());
    if (update.getLanguage() != null) found.setLanguage(update.getLanguage());
    if (update.getDescription() != null) found.setDescription(update.getDescription());
    if (update.getCoverUrl() != null) found.setCoverUrl(update.getCoverUrl());
    if (update.getPublicationDate() != null) found.setPublicationDate(update.getPublicationDate());
  }
}
