package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.dto.LibraryResponse;
import hei.school.libraries.entity.Library;
import hei.school.libraries.service.LibraryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

  private final LibraryService libraryService;

  private LibraryResponse toResponse(Library library) {
    return new LibraryResponse(
        library.getId(),
        library.getName(),
        library.getAddress(),
        library.getPhone(),
        library.getBookCopies().stream().map(bc -> bc.getId()).toList());
    // library.getCustomers().stream().map(c -> c.getId()).toList(),
    // library.getSales().stream().map(s -> s.getId()).toList());
  }

  @GetMapping
  public List<LibraryResponse> getAllLibraries() {
    return libraryService.getAllLibraries().stream().map(this::toResponse).toList();
  }

  @GetMapping("/{id}")
  public LibraryResponse getLibraryById(@PathVariable String id) {
    return toResponse(libraryService.getLibraryById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LibraryResponse createLibrary(@RequestBody Library library) {
    return toResponse(libraryService.createLibrary(library));
  }

  @PatchMapping("/{id}")
  public LibraryResponse patchLibrary(@PathVariable String id, @RequestBody Library library) {
    return toResponse(libraryService.patchLibrary(id, library));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLibrary(@PathVariable String id) {
    libraryService.deleteLibrary(id);
  }
}
