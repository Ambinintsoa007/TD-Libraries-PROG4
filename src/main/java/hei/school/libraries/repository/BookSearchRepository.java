package hei.school.libraries.repository;

import hei.school.libraries.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSearchRepository extends JpaRepository<Book, String> {

  @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
  List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);

  @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
  List<Book> findByIsbn(@Param("isbn") String isbn);

  @Query(
      """
      SELECT DISTINCT b FROM Book b
      JOIN b.authors a
      WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :authorName, '%'))
         OR LOWER(a.lastName)  LIKE LOWER(CONCAT('%', :authorName, '%'))
      """)
  List<Book> findByAuthorName(@Param("authorName") String authorName);

  @Query(
      """
      SELECT DISTINCT b FROM Book b
      JOIN b.genres g
      WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :genre, '%'))
      """)
  List<Book> findByGenreName(@Param("genre") String genre);
}
