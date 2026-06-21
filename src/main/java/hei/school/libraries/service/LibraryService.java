package hei.school.libraries.service;

import hei.school.libraries.Dto.LibraryResponse;
import hei.school.libraries.entity.Library;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.LibraryMapper;
import hei.school.libraries.repository.LibraryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;
  private final LibraryMapper libraryMapper;

  public List<LibraryResponse> getAllLibraries() {
    return libraryRepository.findAll().stream().map(this::toResponse).toList();
  }

  public LibraryResponse getLibraryById(String id) {
    Library library =
        libraryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Library not found: " + id));

    return toResponse(library);
  }

  public LibraryResponse createLibrary(Library library) {
    Library saved = libraryRepository.save(library);
    return toResponse(saved);
  }

  public LibraryResponse patchLibrary(String id, Library update) {
    Library found =
        libraryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Library not found: " + id));

    libraryMapper.patch(found, update);

    Library saved = libraryRepository.save(found);
    return toResponse(saved);
  }

  public void deleteLibrary(String id) {
    Library found =
        libraryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Library not found: " + id));

    libraryRepository.delete(found);
  }

  private LibraryResponse toResponse(Library library) {
    return new LibraryResponse(
        library.getId(),
        library.getName(),
        library.getAddress(),
        library.getPhone(),
        library.getBookCopies(),
        library.getCustomers(),
        library.getSales());
  }
}
