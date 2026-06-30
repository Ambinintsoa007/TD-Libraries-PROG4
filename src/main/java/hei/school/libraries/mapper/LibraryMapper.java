package hei.school.libraries.mapper;

import hei.school.libraries.dto.LibraryRequest;
import hei.school.libraries.dto.LibraryResponse;
import hei.school.libraries.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper {

    public LibraryResponse toResponse(Library library) {
        return new LibraryResponse(
                library.getId(),
                library.getName(),
                library.getAddress(),
                library.getPhone());
    }

    public Library toEntity(LibraryRequest request) {
        Library library = new Library();
        library.setName(request.name());
        library.setAddress(request.address());
        library.setPhone(request.phone());
        return library;
    }

    public void patch(Library found, LibraryRequest request) {
        if (request.name() != null) found.setName(request.name());
        if (request.address() != null) found.setAddress(request.address());
        if (request.phone() != null) found.setPhone(request.phone());
    }
}