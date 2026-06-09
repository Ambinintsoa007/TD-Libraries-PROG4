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
@Table(name = "arrival_line")
public class ArrivalLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_arrival")
    private Arrival arrival;

    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

    private Integer quantity;
}