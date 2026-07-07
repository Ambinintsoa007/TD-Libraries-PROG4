package hei.school.libraries.service;

import hei.school.libraries.entity.Book;
import hei.school.libraries.repository.BookSearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchService {
  private final BookSearchRepository bookSearchRepository;

  public List<Book> searchByTitle(String title) {
    List<Book> results = bookSearchRepository.findByTitleContainingIgnoreCase(title);
    if (results.isEmpty()) {
      throw new RuntimeException("Aucun livre trouvé avec le titre : " + title);
    }
    return results;
  }

  public List<Book> searchByIsbn(String isbn) {
    List<Book> results = bookSearchRepository.findByIsbn(isbn);
    if (results.isEmpty()) {
      throw new RuntimeException("Aucun livre trouvé avec l'ISBN : " + isbn);
    }
    return results;
  }

  public List<Book> searchByAuthor(String authorName) {
    List<Book> results = bookSearchRepository.findByAuthorName(authorName);
    if (results.isEmpty()) {
      throw new RuntimeException("Aucun livre trouvé pour l'auteur : " + authorName);
    }
    return results;
  }

  public List<Book> searchByGenre(String genre) {
    List<Book> results = bookSearchRepository.findByGenreName(genre);
    if (results.isEmpty()) {
      throw new RuntimeException("Aucun livre trouvé pour le genre : " + genre);
    }
    return results;
  }
}
