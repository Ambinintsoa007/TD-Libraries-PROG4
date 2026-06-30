package hei.school.libraries.repository;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Format;
import hei.school.libraries.entity.enums.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookCopyRepository extends JpaRepository<BookCopy, String> {

  List<BookCopy> findByBook_IdAndStatus(String bookId, Status status);

  long countByBook_IdAndStatus(String bookId, Status status);

  @Query("""
      SELECT bc FROM BookCopy bc
      WHERE (:bookId IS NULL OR bc.book.id = :bookId)
        AND (:libraryId IS NULL OR bc.library.id = :libraryId)
        AND (:status IS NULL OR bc.status = :status)
        AND (:format IS NULL OR bc.format = :format)
        AND (:minPrice IS NULL OR bc.price >= :minPrice)
        AND (:maxPrice IS NULL OR bc.price <= :maxPrice)
      """)
  List<BookCopy> searchBookCopies(
          @Param("bookId") String bookId,
          @Param("libraryId") String libraryId,
          @Param("status") Status status,
          @Param("format") Format format,
          @Param("minPrice") Double minPrice,
          @Param("maxPrice") Double maxPrice);
}