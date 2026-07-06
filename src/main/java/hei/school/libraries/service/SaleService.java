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
import java.util.Map;
import java.util.stream.Collectors;
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

    if (lines == null || lines.isEmpty()) {
      throw new BadRequestException("Sale must contain at least one item");
    }

    for (SaleItemRequest line : lines) {
      if (line.quantity() <= 0) {
        throw new BadRequestException("Quantity must be > 0 for book " + line.bookId());
      }
    }

    Map<String, Integer> grouped =
        lines.stream()
            .collect(
                Collectors.groupingBy(
                    SaleItemRequest::bookId, Collectors.summingInt(SaleItemRequest::quantity)));

    for (Map.Entry<String, Integer> entry : grouped.entrySet()) {
      long stock = bookCopyRepository.countByBook_IdAndStatus(entry.getKey(), Status.AVAILABLE);

      if (stock < entry.getValue()) {
        throw new BadRequestException(
            "Stock insuffisant pour le livre "
                + entry.getKey()
                + " : demandé "
                + entry.getValue()
                + ", disponible "
                + stock);
      }
    }

    Sale sale = new Sale();
    sale.setSaleDate(LocalDate.now());
    sale.setStatus(SaleStatus.PENDING);
    sale = saleRepository.save(sale);

    double total = 0.0;

    for (Map.Entry<String, Integer> entry : grouped.entrySet()) {

      List<BookCopy> copies =
          bookCopyRepository.findByBook_IdAndStatus(entry.getKey(), Status.AVAILABLE);

      if (copies.size() < entry.getValue()) {
        throw new BadRequestException("Stock insuffisant pour book " + entry.getKey());
      }

      for (int i = 0; i < entry.getValue(); i++) {

        BookCopy copy = copies.get(i);
        copy.setStatus(Status.SOLD);
        bookCopyRepository.save(copy);

        SaleItem item = new SaleItem();
        item.setSale(sale);
        item.setBookCopy(copy);
        item.setUnitPrice(copy.getPrice());
        saleItemRepository.save(item);

        sale.getSaleItems().add(item);

        total += copy.getPrice() != null ? copy.getPrice() : 0.0;
      }
    }

    sale.setTotalAmount(total);
    return saleRepository.save(sale);
  }
}
