package hei.school.libraries.repository;

import hei.school.libraries.entity.Sale;
import hei.school.libraries.entity.enums.SaleStatus;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {

  @EntityGraph(
      attributePaths = {
        "saleItems",
        "saleItems.bookCopy",
        "saleItems.bookCopy.book",
        "saleItems.bookCopy.book.genre"
      })
  List<Sale> findByStatus(SaleStatus status);
}
