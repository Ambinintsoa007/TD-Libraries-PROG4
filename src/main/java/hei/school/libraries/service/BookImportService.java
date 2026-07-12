package hei.school.libraries.service;

import hei.school.libraries.Dto.BookResponse;
import hei.school.libraries.Dto.ExternalBookResponse;
import hei.school.libraries.entity.Book;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.mapper.BookMapper;
import hei.school.libraries.repository.BookRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookImportService {

  private final BookExternalService bookExternalService;
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookResponse importByIsbn(String isbn) {
    ExternalBookResponse externalBook = bookExternalService.findByIsbn(isbn);

    if (bookRepository.existsByIsbn(externalBook.isbn())) {
      throw new BadRequestException("Book already exists with ISBN : " + externalBook.isbn());
    }

    Book book = new Book();
    book.setTitle(externalBook.title());
    book.setIsbn(externalBook.isbn());
    book.setLanguage(externalBook.language());
    book.setDescription(externalBook.description());
    book.setCoverUrl(externalBook.coverUrl());
    book.setPublicationDate(parsePublicationDate(externalBook.publicationDate()));

    return bookMapper.toResponse(bookRepository.save(book));
  }

  private LocalDate parsePublicationDate(String publicationDate) {
    if (publicationDate == null || publicationDate.isBlank()) {
      return null;
    }

    try {
      if (publicationDate.matches("\\d{4}")) {
        return LocalDate.parse(publicationDate + "-01-01");
      }

      if (publicationDate.matches("\\d{4}-\\d{2}")) {
        return LocalDate.parse(publicationDate + "-01");
      }

      return LocalDate.parse(publicationDate);
    } catch (DateTimeParseException e) {
      return null;
    }
  }
}
