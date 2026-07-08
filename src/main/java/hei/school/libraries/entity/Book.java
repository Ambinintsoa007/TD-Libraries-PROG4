package hei.school.libraries.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String title;

  @Column(unique = true)
  private String isbn;

  @Column(nullable = false)
  private String language;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "cover_url")
  private String coverUrl;

  private LocalDate publicationDate;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  @JsonIgnore
  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookCopy> bookCopies = new ArrayList<>();

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "book_author",
      joinColumns = @JoinColumn(name = "id_book"),
      inverseJoinColumns = @JoinColumn(name = "id_author"))
  private List<Author> authors = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "id_genre")
  private Genre genre;
}
