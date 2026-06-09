package hei.school.libraries.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ManyToMany
  @JoinTable(
          name = "book_author",
          joinColumns = @JoinColumn(name = "id_book"),
          inverseJoinColumns = @JoinColumn(name = "id_author"))
  private List<Author> authors = new ArrayList<>();

  @ManyToMany
  @JoinTable(
          name = "book_genre",
          joinColumns = @JoinColumn(name = "id_book"),
          inverseJoinColumns = @JoinColumn(name = "id_genre"))
  private Set<Genre> genres = new HashSet<>();
}