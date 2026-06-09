package hei.school.libraries.entity;

import hei.school.libraries.enums.Format;
import hei.school.libraries.entity.enums.Status;
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
  @JoinColumn(name = "id_book")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "id_library")
  private Library library;

  @Enumerated(EnumType.STRING)
  private Format format;

  private Double price;

  @Column(name = "shelf_location")
  private String shelfLocation;

  @Enumerated(EnumType.STRING)
  private Status status;
}