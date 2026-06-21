package hei.school.libraries.mapper;

import hei.school.libraries.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper {

  public void patch(Library found, Library update) {
    if (update.getName() != null) found.setName(update.getName());
    if (update.getAddress() != null) found.setAddress(update.getAddress());
    if (update.getPhone() != null) found.setPhone(update.getPhone());

    if (update.getBookCopies() != null) found.setBookCopies(update.getBookCopies());
    if (update.getCustomers() != null) found.setCustomers(update.getCustomers());
    if (update.getSales() != null) found.setSales(update.getSales());
  }
}
