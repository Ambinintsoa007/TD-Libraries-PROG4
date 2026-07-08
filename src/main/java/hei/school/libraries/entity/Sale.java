package hei.school.libraries.entity;

import hei.school.libraries.entity.enums.SaleStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "id_customer")
  private Customer customer;

  @Column(name = "sale_date")
  private LocalDate saleDate;

  @Column(name = "total_amount")
  private Double totalAmount;

  @Enumerated(EnumType.STRING)
  private SaleStatus status;

  @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
  private List<SaleItem> saleItems = new ArrayList<>();

  @OneToOne(mappedBy = "sale")
  private Payment payment;
}
