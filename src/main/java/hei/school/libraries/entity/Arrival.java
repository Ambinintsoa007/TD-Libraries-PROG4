package hei.school.libraries.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
@Table(name = "arrival")
public class Arrival {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "arrival_date")
  private LocalDateTime arrivalDate;

  private String supplier;

  @OneToMany(mappedBy = "arrival")
  private List<ArrivalLine> arrivalLines = new ArrayList<>();
}