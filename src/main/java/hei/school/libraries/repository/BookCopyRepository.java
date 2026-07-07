package hei.school.libraries.repository;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  BookCopyRepository extends JpaRepository<BookCopy, String> {
    long countByBook_IdAndStatus(String bookId, Status status);

    List<BookCopy> findByBook_Id(String bookId);

    List<BookCopy> findByBook_IdAndStatus(String bookId, Status status);
}
