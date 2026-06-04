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
@Table(name = "payment")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private Double amount;
  private LocalDate paymentDate;
  private String paymentMethod; // cash, creditCard
  private String reference;
}
