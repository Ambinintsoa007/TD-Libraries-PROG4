package hei.school.libraries.service;

import hei.school.libraries.Dto.SaleItemRequest;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Sale;
import hei.school.libraries.entity.SaleItem;
import hei.school.libraries.entity.enums.SaleStatus;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.exception.BadRequestException;
import hei.school.libraries.repository.BookCopyRepository;
import hei.school.libraries.repository.SaleItemRepository;
import hei.school.libraries.repository.SaleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {

  private final BookCopyRepository bookCopyRepository;
  private final SaleRepository saleRepository;
  private final SaleItemRepository saleItemRepository;

  @Transactional
  public Sale createSale(List<SaleItemRequest> lines) {

    for (SaleItemRequest line : lines) {
      long stock = bookCopyRepository.countByBook_IdAndStatus(line.bookId(), Status.AVAILABLE);
      if (stock < line.quantity()) {
        throw new BadRequestException(
            "Stock insuffisant pour le livre "
                + line.bookId()
                + " : demandé "
                + line.quantity()
                + ", disponible "
                + stock);
      }
    }

    Sale sale = new Sale();
    sale.setSaleDate(LocalDate.now());
    sale.setStatus(SaleStatus.PENDING);
    saleRepository.save(sale);

    double total = 0.0;
    for (SaleItemRequest line : lines) {
      List<BookCopy> copies =
          bookCopyRepository
              .findByBook_IdAndStatus(line.bookId(), Status.AVAILABLE)
              .subList(0, line.quantity());

      for (BookCopy copy : copies) {
        copy.setStatus(Status.SOLD);
        bookCopyRepository.save(copy);

        SaleItem item = new SaleItem();
        item.setSale(sale);
        item.setBookCopy(copy);
        item.setUnitPrice(copy.getPrice());
        saleItemRepository.save(item);

        total += copy.getPrice() != null ? copy.getPrice() : 0.0;
      }
    }

    sale.setTotalAmount(total);
    return saleRepository.save(sale);
  }
}
