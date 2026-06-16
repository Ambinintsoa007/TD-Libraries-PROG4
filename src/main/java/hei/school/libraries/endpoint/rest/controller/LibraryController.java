package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.Library;
import hei.school.libraries.service.LibraryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

  private final LibraryService libraryService;

  @GetMapping
  public ResponseEntity<List<Library>> getAll() {
    return ResponseEntity.ok(libraryService.getAllLibraries());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Library> getById(@PathVariable String id) {
    try {
      return ResponseEntity.ok(libraryService.getLibraryById(id));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<Library> create(@RequestBody Library library) {
    return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.createLibrary(library));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Library> update(@PathVariable String id, @RequestBody Library library) {
    try {
      return ResponseEntity.ok(libraryService.patchLibrary(id, library));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    try {
      libraryService.deleteLibrary(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
