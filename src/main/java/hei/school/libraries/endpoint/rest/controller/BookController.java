package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.Dto.BookResponse;
import hei.school.libraries.Dto.BookStockResponse;
import hei.school.libraries.entity.Book;
import hei.school.libraries.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  private BookResponse toResponse(Book book) {
    return new BookResponse(
        book.getId(),
        book.getTitle(),
        book.getIsbn(),
        book.getLanguage(),
        book.getDescription(),
        book.getCoverUrl(),
        book.getPublicationDate());
  }

  @GetMapping
  public List<BookResponse> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{id}")
  public BookResponse getBookById(@PathVariable String id) {
    return toResponse(bookService.getBookById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponse createBook(@RequestBody Book book) {
    return toResponse(bookService.createBook(book));
  }

  @PatchMapping("/{id}")
  public BookResponse updateBook(@PathVariable String id, @RequestBody Book book) {
    return toResponse(bookService.patchBook(id, book));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable String id) {
    bookService.deleteBook(id);
  }

  @PutMapping("/{id}/authors/{authorId}")
  public BookResponse addAuthorToBook(@PathVariable String id, @PathVariable String authorId) {
    return toResponse(bookService.addAuthorToBook(id, authorId));
  }

  @GetMapping("/low-stock")
  public List<BookResponse> getLowStock(@RequestParam(defaultValue = "5") int threshold) {
    return bookService.getLowStock(threshold);
  }

  @GetMapping("/{id}/stock")
  public BookStockResponse getBookStock(@PathVariable String id) {
    return bookService.getBookStock(id);
  }

  @PostMapping("/{id}/sell")
  @ResponseStatus(HttpStatus.OK)
  public void sellBook(
      @PathVariable String id, @RequestParam String customerId, @RequestParam int quantity) {
    bookService.sell(id, customerId, quantity);
  }
}
