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
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String firstName;
  private String lastName;
  private String address;
  private String phone;
  private String notes;

  @ManyToOne
  @JoinColumn(name = "library_id")
  private Library library;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
