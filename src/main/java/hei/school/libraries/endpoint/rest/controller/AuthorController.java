package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.Dto.AuthorResponse;
import hei.school.libraries.entity.Author;
import hei.school.libraries.service.AuthorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  private AuthorResponse toResponse(Author author) {
    return new AuthorResponse(
        author.getId(),
        author.getFirstName(),
        author.getLastName(),
        author.getBirthDate(),
        author.getNationality(),
        author.getBiography());
  }

  @GetMapping
  public List<AuthorResponse> getAllAuthors() {
    return authorService.getAllAuthors().stream().map(this::toResponse).toList();
  }

  @GetMapping("/{id}")
  public AuthorResponse getAuthorById(@PathVariable String id) {
    return toResponse(authorService.getAuthorById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuthorResponse createAuthor(@RequestBody Author author) {
    return toResponse(authorService.createAuthor(author));
  }

  @PatchMapping("/{id}")
  public AuthorResponse updateAuthor(@PathVariable String id, @RequestBody Author author) {
    return toResponse(authorService.patchAuthor(id, author));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAuthor(@PathVariable String id) {
    authorService.deleteAuthor(id);
  }
}
