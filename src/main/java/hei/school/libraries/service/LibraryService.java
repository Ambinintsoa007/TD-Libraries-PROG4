package hei.school.libraries.service;

import hei.school.libraries.entity.Library;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.LibraryMapper;
import hei.school.libraries.repository.LibraryRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;
  private final LibraryMapper libraryMapper;

  private void validateUUID(String id) {
    try {
      UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid UUID format: " + id);
    }
  }

  public List<Library> getAllLibraries() {
    return libraryRepository.findAll();
  }

  public Library getLibraryById(String id) {

    validateUUID(id);

    return libraryRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Library not found: " + id));
  }

  public Library createLibrary(Library library) {
    return libraryRepository.save(library);
  }

  public Library patchLibrary(String id, Library update) {

    validateUUID(id);

    Library found =
        libraryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Library not found: " + id));

    libraryMapper.patch(found, update);

    return libraryRepository.save(found);
  }

  public void deleteLibrary(String id) {

    validateUUID(id);

    Library found =
        libraryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Library not found: " + id));

    libraryRepository.delete(found);
  }
}
