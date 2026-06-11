package hei.school.libraries.repository;

import hei.school.libraries.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String> {}
