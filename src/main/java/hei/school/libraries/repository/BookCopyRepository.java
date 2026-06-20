package hei.school.libraries.repository;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {

  List<BookCopy> findByBook_IdAndStatus(String bookId, Status status);

  long countByBook_IdAndStatus(String bookId, Status status);
}
