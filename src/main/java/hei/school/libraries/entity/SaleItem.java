package hei.school.libraries.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale_item")
public class SaleItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "id_sale")
  private Sale sale;

  @ManyToOne
  @JoinColumn(name = "id_book_copy")
  private BookCopy bookCopy;

  @Column(name = "unit_price")
  private Double unitPrice;
}
