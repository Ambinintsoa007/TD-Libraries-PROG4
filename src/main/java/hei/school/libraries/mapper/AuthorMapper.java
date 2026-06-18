package hei.school.libraries.mapper;

import hei.school.libraries.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

  public void patch(Author found, Author update) {
    if (update.getFirstName() != null) found.setFirstName(update.getFirstName());
    if (update.getLastName() != null) found.setLastName(update.getLastName());
    if (update.getBirthDate() != null) found.setBirthDate(update.getBirthDate());
    if (update.getNationality() != null) found.setNationality(update.getNationality());
    if (update.getBiography() != null) found.setBiography(update.getBiography());
  }
}
