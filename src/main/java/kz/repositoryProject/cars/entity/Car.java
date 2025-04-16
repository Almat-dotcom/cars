package kz.repositoryProject.cars.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "model", length = 200)
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "price")
    private int price;

    @Column(name = "engine_volume")
    private double engineVolume;
}
