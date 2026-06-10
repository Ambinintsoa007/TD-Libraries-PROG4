package hei.school.libraries.entity;

import hei.school.libraries.entity.enums.PaymentMethod;
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

  @OneToOne
  @JoinColumn(name = "id_sale")
  private Sale sale;

  private Double amount;

  @Column(name = "payment_date")
  private LocalDate paymentDate;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  private String reference;
}
