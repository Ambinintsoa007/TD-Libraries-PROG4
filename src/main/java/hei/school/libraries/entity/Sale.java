package hei.school.libraries.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
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
  @JoinColumn(name = "customer_id")
  private Customer customer;

  private LocalDate saleDate;
  private Double totalAmount;
  private String paymentMethod; // cash, creditCard
  private String status; // Paid, Pending, Cancelled

  @ManyToOne
  @JoinColumn(name = "library_id")
  private Library library;
}
