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
@Table(name = "arrival")
public class Arrival {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private LocalDate arrivalDate;
  private String supplier; // fournisseur
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "book_copy_id")
  private BookCopy bookCopy;
}
