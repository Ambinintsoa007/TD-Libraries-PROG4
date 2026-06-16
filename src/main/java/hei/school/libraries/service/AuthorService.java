package hei.school.libraries.service;

import hei.school.libraries.entity.Author;
import hei.school.libraries.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  public Author getAuthorById(String id) {
    return authorRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Author not found : " + id));
  }

  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  public Author patchAuthor(String id, Author author) {
    Author found = getAuthorById(id);
    if (author.getFirstName() != null) found.setFirstName(author.getFirstName());
    if (author.getLastName() != null) found.setLastName(author.getLastName());
    if (author.getBirthDate() != null) found.setBirthDate(author.getBirthDate());
    if (author.getNationality() != null) found.setNationality(author.getNationality());
    if (author.getBiography() != null) found.setBiography(author.getBiography());
    return authorRepository.save(found);
  }

  public void deleteAuthor(String id) {
    Author found = getAuthorById(id);
    authorRepository.delete(found);
  }
}
