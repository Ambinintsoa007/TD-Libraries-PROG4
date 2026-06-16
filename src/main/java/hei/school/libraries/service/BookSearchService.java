package hei.school.libraries.service;

import hei.school.libraries.entity.Book;
import hei.school.libraries.repository.BookSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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

}
