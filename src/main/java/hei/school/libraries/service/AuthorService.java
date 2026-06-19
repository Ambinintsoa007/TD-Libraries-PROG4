package hei.school.libraries.service;

import hei.school.libraries.entity.Author;
import hei.school.libraries.exception.ForbiddenException;
import hei.school.libraries.exception.NotFoundException;
import hei.school.libraries.mapper.AuthorMapper;
import hei.school.libraries.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  public Author getAuthorById(String id) {
    return authorRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Author not found : " + id));
  }

  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  public Author patchAuthor(String id, Author author) {
    Author found = getAuthorById(id);
    authorMapper.patch(found, author);
    return authorRepository.save(found);
  }

  @Transactional
  public void deleteAuthor(String id) {
    Author found = getAuthorById(id);
    if (!found.getBooks().isEmpty()) {
      throw new ForbiddenException("Cannot delete author with existing books");
    }
    authorRepository.delete(found);
  }
}
