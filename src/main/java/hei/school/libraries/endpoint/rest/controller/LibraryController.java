package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.dto.LibraryResponse;
import hei.school.libraries.entity.Library;
import hei.school.libraries.service.LibraryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

  private final LibraryService libraryService;

  @GetMapping
  public ResponseEntity<List<LibraryResponse>> getAll() {
    return ResponseEntity.ok(libraryService.getAllLibraries());
  }

  @GetMapping("/{id}")
  public ResponseEntity<LibraryResponse> getById(@PathVariable String id) {
    return ResponseEntity.ok(libraryService.getLibraryById(id));
  }

  @PostMapping
  public ResponseEntity<LibraryResponse> create(@RequestBody Library library) {
    return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.createLibrary(library));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<LibraryResponse> update(
      @PathVariable String id, @RequestBody Library library) {

    return ResponseEntity.ok(libraryService.patchLibrary(id, library));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    libraryService.deleteLibrary(id);
    return ResponseEntity.noContent().build();
  }
}
