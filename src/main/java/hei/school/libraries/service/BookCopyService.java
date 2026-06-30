package hei.school.libraries.service;

import hei.school.libraries.dto.BookCopyRequest;
import hei.school.libraries.dto.BookCopyResponse;
import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Library;
import hei.school.libraries.entity.enums.Format;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.ForbiddenException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.BookCopyMapper;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.BookRepository;
import hei.school.libraries.repository.LibraryRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;
  private final BookRepository bookRepository;
  private final LibraryRepository libraryRepository;
  private final BookCopyMapper bookCopyMapper;

  public List<BookCopyResponse> getAllBookCopies() {
    return bookCopyRepository.findAll().stream()
            .map(bookCopyMapper::toResponse)
            .toList();
  }

  public BookCopyResponse getBookCopyById(String id) {
    return bookCopyMapper.toResponse(getBookCopyEntityById(id));
  }

  public BookCopyResponse createBookCopy(BookCopyRequest request) {
    Book book = getBookEntityById(request.bookId());
    Library library = getLibraryEntityById(request.libraryId());

    validatePrice(request.price());

    BookCopy bookCopy = new BookCopy();
    bookCopy.setBook(book);
    bookCopy.setLibrary(library);
    bookCopy.setFormat(request.format());
    bookCopy.setPrice(request.price());
    bookCopy.setShelfLocation(request.shelfLocation());
    bookCopy.setStatus(request.status() != null ? request.status() : Status.AVAILABLE);

    return bookCopyMapper.toResponse(bookCopyRepository.save(bookCopy));
  }

  public BookCopyResponse patchBookCopy(String id, BookCopyRequest request) {
    BookCopy found = getBookCopyEntityById(id);

    if (request.bookId() != null) {
      found.setBook(getBookEntityById(request.bookId()));
    }

    if (request.libraryId() != null) {
      found.setLibrary(getLibraryEntityById(request.libraryId()));
    }

    validatePrice(request.price());

    bookCopyMapper.patch(found, request);

    return bookCopyMapper.toResponse(bookCopyRepository.save(found));
  }

  public void deleteBookCopy(String id) {
    BookCopy found = getBookCopyEntityById(id);

    if (found.getStatus() != Status.AVAILABLE) {
      throw new ForbiddenException("Cannot delete a non-available book copy");
    }

    bookCopyRepository.delete(found);
  }

  public List<BookCopyResponse> searchBookCopies(
          String bookId,
          String libraryId,
          Status status,
          Format format,
          Double minPrice,
          Double maxPrice) {

    bookId = normalize(bookId);
    libraryId = normalize(libraryId);

    if (bookId != null) validateUuid(bookId, "bookId");
    if (libraryId != null) validateUuid(libraryId, "libraryId");

    validatePriceRange(minPrice, maxPrice);

    return bookCopyRepository
            .searchBookCopies(bookId, libraryId, status, format, minPrice, maxPrice)
            .stream()
            .map(bookCopyMapper::toResponse)
            .toList();
  }

  private BookCopy getBookCopyEntityById(String id) {
    validateUuid(id, "bookCopyId");

    return bookCopyRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("BookCopy not found : " + id));
  }

  private Book getBookEntityById(String bookId) {
    validateUuid(bookId, "bookId");

    return bookRepository
            .findById(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found : " + bookId));
  }

  private Library getLibraryEntityById(String libraryId) {
    validateUuid(libraryId, "libraryId");

    return libraryRepository
            .findById(libraryId)
            .orElseThrow(() -> new NotFoundException("Library not found : " + libraryId));
  }

  private void validateUuid(String id, String fieldName) {
    if (id == null || id.isBlank()) {
      throw new BadRequestException(fieldName + " is required");
    }

    try {
      UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid " + fieldName + " format : " + id);
    }
  }

  private void validatePrice(Double price) {
    if (price != null && price < 0) {
      throw new BadRequestException("Price must be positive");
    }
  }

  private void validatePriceRange(Double minPrice, Double maxPrice) {
    validatePrice(minPrice);
    validatePrice(maxPrice);

    if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
      throw new BadRequestException("minPrice must be lower than maxPrice");
    }
  }

  private String normalize(String value) {
    return value == null || value.isBlank() ? null : value;
  }
}