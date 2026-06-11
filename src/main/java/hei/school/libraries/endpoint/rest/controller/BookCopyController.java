package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.service.BookCopyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookCopies")
@RequiredArgsConstructor
public class BookCopyController {

  private final BookCopyService bookCopyService;

  @GetMapping
  public ResponseEntity<List<BookCopy>> getAllBookCopies() {
    return ResponseEntity.ok(bookCopyService.getAllBookCopies());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookCopy> getBookCopyById(@PathVariable String id) {
    return ResponseEntity.ok(bookCopyService.getBookCopyById(id));
  }

  @PostMapping
  public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopy bookCopy) {
    BookCopy createdBookCopy = bookCopyService.createBookCopy(bookCopy);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdBookCopy);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<BookCopy> patchBookCopy(
      @PathVariable String id, @RequestBody BookCopy bookCopy) {

    return ResponseEntity.ok(bookCopyService.patchBookCopy(id, bookCopy));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBookCopy(@PathVariable String id) {
    bookCopyService.deleteBookCopy(id);
    return ResponseEntity.noContent().build();
  }
}
