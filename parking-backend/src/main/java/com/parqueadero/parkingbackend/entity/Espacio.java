package com.parqueadero.parkingbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "espacios")
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String numeroEspacio;//A-01 ,B-05

    @Column(nullable = false, length = 20)
    private String estado; //libre o ocupado

    @Column(nullable = false, length = 20)
    private String tipoVehiculo; //auto, moto
}