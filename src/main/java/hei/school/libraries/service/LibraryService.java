package hei.school.libraries.service;

import hei.school.libraries.entity.Library;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.repository.LibraryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;

  public List<Library> getAllLibraries() {
    return libraryRepository.findAll();
  }

  public Library getLibraryById(String id) {
    return libraryRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Library not found : " + id));
  }

  public Library createLibrary(Library library) {
    return libraryRepository.save(library);
  }

  public Library patchLibrary(String id, Library library) {

    Library found = getLibraryById(id);

    if (library.getName() != null) found.setName(library.getName());
    if (library.getAddress() != null) found.setAddress(library.getAddress());
    if (library.getPhone() != null) found.setPhone(library.getPhone());

    return libraryRepository.save(found);
  }

  public void deleteLibrary(String id) {
    Library found = getLibraryById(id);
    libraryRepository.delete(found);
  }
}
