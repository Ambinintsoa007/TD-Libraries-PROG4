package hei.school.libraries.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre")
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToMany(mappedBy = "genres")
  private Set<Book> books = new HashSet<>();
}
