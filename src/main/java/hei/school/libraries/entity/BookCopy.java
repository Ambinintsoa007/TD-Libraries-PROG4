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
@Table(name = "book_copy")
public class BookCopy {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private String format; // PaperBack, HardBack, Pocket
  private Double price;
  private Integer quantity;

  @Column(name = "shelf_location")
  private String shelfLocation; // rayon A, B

  private String status; // Available, OutOfStock

  @ManyToOne
  @JoinColumn(name = "library_id")
  private Library library;
}
