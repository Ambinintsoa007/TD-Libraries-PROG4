package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.dto.LibraryRequest;
import hei.school.libraries.dto.LibraryResponse;
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

    @GetMapping
    public List<LibraryResponse> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @GetMapping("/{id}")
    public LibraryResponse getLibraryById(@PathVariable String id) {
        return libraryService.getLibraryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryResponse createLibrary(@RequestBody LibraryRequest request) {
        return libraryService.createLibrary(request);
    }

    @PatchMapping("/{id}")
    public LibraryResponse patchLibrary(
            @PathVariable String id,
            @RequestBody LibraryRequest request) {
        return libraryService.patchLibrary(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLibrary(@PathVariable String id) {
        libraryService.deleteLibrary(id);
    }
}