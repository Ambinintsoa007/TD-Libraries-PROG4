package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.dto.BookCopyRequest;
import hei.school.libraries.dto.BookCopyResponse;
import hei.school.libraries.entity.enums.Format;
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

  @GetMapping
  public List<BookCopyResponse> getAllBookCopies() {
    return bookCopyService.getAllBookCopies();
  }

  @GetMapping("/search")
  public List<BookCopyResponse> searchBookCopies(
      @RequestParam(required = false) String bookId,
      @RequestParam(required = false) String libraryId,
      @RequestParam(required = false) Status status,
      @RequestParam(required = false) Format format,
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice) {
    return bookCopyService.searchBookCopies(bookId, libraryId, status, format, minPrice, maxPrice);
  }

  @GetMapping("/{id}")
  public BookCopyResponse getBookCopyById(@PathVariable String id) {
    return bookCopyService.getBookCopyById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookCopyResponse createBookCopy(@RequestBody BookCopyRequest request) {
    return bookCopyService.createBookCopy(request);
  }

  @PatchMapping("/{id}")
  public BookCopyResponse patchBookCopy(
      @PathVariable String id, @RequestBody BookCopyRequest request) {
    return bookCopyService.patchBookCopy(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBookCopy(@PathVariable String id) {
    bookCopyService.deleteBookCopy(id);
  }
}
