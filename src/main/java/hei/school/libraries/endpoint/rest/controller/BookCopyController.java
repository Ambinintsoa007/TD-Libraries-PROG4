package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.Dto.BookCopyResponse;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.service.BookCopyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookCopies")
@RequiredArgsConstructor
public class BookCopyController {

  private final BookCopyService bookCopyService;

  private BookCopyResponse toResponse(BookCopy bookCopy) {
    return new BookCopyResponse(
        bookCopy.getId(),
        bookCopy.getBook() != null ? bookCopy.getBook().getId() : null,
        bookCopy.getLibrary() != null ? bookCopy.getLibrary().getId() : null,
        bookCopy.getFormat(),
        bookCopy.getPrice(),
        bookCopy.getShelfLocation(),
        bookCopy.getStatus());
  }

  @GetMapping
  public List<BookCopyResponse> getAllBookCopies() {
    return bookCopyService.getAllBookCopies().stream().map(this::toResponse).toList();
  }

  @GetMapping("/search")
  public List<BookCopyResponse> searchBookCopies(
      @RequestParam(required = false) String bookId,
      @RequestParam(required = false) Status status) {
    return bookCopyService.searchBookCopies(bookId, status).stream().map(this::toResponse).toList();
  }

  @GetMapping("/{id}")
  public BookCopyResponse getBookCopyById(@PathVariable String id) {
    return toResponse(bookCopyService.getBookCopyById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookCopyResponse createBookCopy(@RequestBody BookCopy bookCopy) {
    return toResponse(bookCopyService.createBookCopy(bookCopy));
  }

  @PatchMapping("/{id}")
  public BookCopyResponse patchBookCopy(@PathVariable String id, @RequestBody BookCopy bookCopy) {
    return toResponse(bookCopyService.patchBookCopy(id, bookCopy));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBookCopy(@PathVariable String id) {
    bookCopyService.deleteBookCopy(id);
  }
}
