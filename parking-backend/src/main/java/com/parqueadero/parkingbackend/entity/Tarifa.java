package com.parqueadero.parkingbackend.entity;

import jakarta.persistence.*;
import lombok.Data; //Lombok genera Getters, Setters, toString y mas

import java.math.BigDecimal;

@Data//Lombok
@Entity
@Table(name = "tarifas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tipoVehiculo", "unidadTiempo"})
})
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String tipoVehiculo;//carro o moto

    @Column(nullable = false, length = 20)
    private String unidadTiempo;//por hora, dia o fraccion

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}