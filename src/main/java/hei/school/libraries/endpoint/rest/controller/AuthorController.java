package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.Author;
import hei.school.libraries.service.AuthorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping
  public ResponseEntity<List<Author>> getAllAuthors() {
    return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable String id) {
    try {
      Author author = authorService.getAuthorById(id);
      return ResponseEntity.status(HttpStatus.OK).body(author);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping
  public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
    Author created = authorService.createAuthor(author);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author author) {
    try {
      Author updated = authorService.patchAuthor(id, author);
      return ResponseEntity.status(HttpStatus.OK).body(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
    try {
      authorService.deleteAuthor(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
