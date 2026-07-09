package hei.school.libraries.service;

import hei.school.libraries.Dto.GenreRevenueResponse;
import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Sale;
import hei.school.libraries.entity.SaleItem;
import hei.school.libraries.entity.enums.SaleStatus;
import hei.school.libraries.repository.SaleRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final SaleRepository saleRepository;

  public List<GenreRevenueResponse> getRevenueByGenre() {

    Map<String, BigDecimal> revenueByGenre = new HashMap<>();

    List<Sale> paidSales = saleRepository.findByStatus(SaleStatus.PAID);

    for (Sale sale : paidSales) {
      for (SaleItem saleItem : sale.getSaleItems()) {

        BookCopy bookCopy = saleItem.getBookCopy();

        if (bookCopy == null) {
          continue;
        }

        Book book = bookCopy.getBook();

        if (book == null || book.getGenre() == null || saleItem.getUnitPrice() == null) {
          continue;
        }

        String genreName = book.getGenre().getName();

        BigDecimal revenue = BigDecimal.valueOf(saleItem.getUnitPrice());

        revenueByGenre.merge(genreName, revenue, BigDecimal::add);
      }
    }

    return revenueByGenre.entrySet().stream()
        .map(entry -> new GenreRevenueResponse(entry.getKey(), entry.getValue()))
        .toList();
  }
}
