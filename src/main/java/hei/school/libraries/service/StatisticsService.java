package hei.school.libraries.service;

import hei.school.libraries.Dto.GenreRevenueResponse;
import hei.school.libraries.entity.Book;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.Sale;
import hei.school.libraries.entity.SaleItem;
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

    List<Sale> sales = saleRepository.findAll();

    for (Sale sale : sales) {
      for (SaleItem item : sale.getSaleItems()) {

        BookCopy copy = item.getBookCopy();
        Book book = copy.getBook();

        String genreName = book.getGenre().getName();

        BigDecimal price = BigDecimal.valueOf(item.getUnitPrice());

        revenueByGenre.merge(genreName, price, BigDecimal::add);
      }
    }

    return revenueByGenre.entrySet().stream()
        .map(e -> new GenreRevenueResponse(e.getKey(), e.getValue()))
        .toList();
  }
}
