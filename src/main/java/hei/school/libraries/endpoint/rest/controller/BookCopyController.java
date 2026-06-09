package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.service.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookCopies")
@RequiredArgsConstructor
public class BookCopyController {

  private final BookCopyService bookCopyService;

  @GetMapping
  public List<BookCopy> getAllBookCopies() {
    return bookCopyService.getAllBookCopies();
  }

  @GetMapping("/{id}")
  public BookCopy getBookCopyById(@PathVariable String id) {
    return bookCopyService.getBookCopyById(id);
  }

  @PostMapping
  public BookCopy createBookCopy(@RequestBody BookCopy bookCopy) {
    return bookCopyService.createBookCopy(bookCopy);
  }

  @PatchMapping("/{id}")
  public BookCopy patchBookCopy(
      @PathVariable String id,
      @RequestBody BookCopy bookCopy) {
    return bookCopyService.patchBookCopy(id, bookCopy);
  }

  @DeleteMapping("/{id}")
  public void deleteBookCopy(@PathVariable String id) {
    bookCopyService.deleteBookCopy(id);
  }
}