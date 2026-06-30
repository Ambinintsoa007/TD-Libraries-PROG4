package hei.school.libraries.service;

import hei.school.libraries.dto.LibraryRequest;
import hei.school.libraries.dto.LibraryResponse;
import hei.school.libraries.entity.Library;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.exception.ForbiddenException;
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

  public List<LibraryResponse> getAllLibraries() {
    return libraryRepository.findAll().stream().map(libraryMapper::toResponse).toList();
  }

  public LibraryResponse getLibraryById(String id) {
    return libraryMapper.toResponse(getLibraryEntityById(id));
  }

  public LibraryResponse createLibrary(LibraryRequest request) {
    validateCreateRequest(request);

    Library library = libraryMapper.toEntity(request);
    return libraryMapper.toResponse(libraryRepository.save(library));
  }

  public LibraryResponse patchLibrary(String id, LibraryRequest request) {
    validateRequestNotNull(request);

    Library found = getLibraryEntityById(id);
    libraryMapper.patch(found, request);

    return libraryMapper.toResponse(libraryRepository.save(found));
  }

  public void deleteLibrary(String id) {
    Library found = getLibraryEntityById(id);

    if (!found.getBookCopies().isEmpty()) {
      throw new ForbiddenException("Cannot delete library with existing book copies");
    }

    libraryRepository.delete(found);
  }

  private Library getLibraryEntityById(String id) {
    validateUuid(id);

    return libraryRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Library not found : " + id));
  }

  private void validateUuid(String id) {
    if (id == null || id.isBlank()) {
      throw new BadRequestException("Library id is required");
    }

    try {
      UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid library id format : " + id);
    }
  }

  private void validateCreateRequest(LibraryRequest request) {
    validateRequestNotNull(request);

    if (request.name() == null || request.name().isBlank()) {
      throw new BadRequestException("Library name is required");
    }

    if (request.address() == null || request.address().isBlank()) {
      throw new BadRequestException("Library address is required");
    }
  }

  private void validateRequestNotNull(LibraryRequest request) {
    if (request == null) {
      throw new BadRequestException("Library request body is required");
    }
  }
}
