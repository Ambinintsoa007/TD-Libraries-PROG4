package hei.school.libraries.entity;

import jakarta.persistence.*;
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
@Table(name = "library")
public class Library {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  @Column(name = "address")
  private String address;

  private String phone;

  @OneToMany(mappedBy = "library")
  private List<BookCopy> bookCopies = new ArrayList<>();
}
