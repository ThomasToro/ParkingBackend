package com.parqueadero.parkingbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "suscripciones")
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String placaVehiculo;

    @Column(nullable = false)
    private LocalDateTime fechaInicio; 

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Column(length = 20)
    private String estado;//activo o vencido

    @Column(precision = 10, scale = 2)
    private BigDecimal montoPagado;//Para saber cuanto pago, como un hisotrial

    //Muchas suscripciones pueden ser del mismo Plan
    @ManyToOne(fetch = FetchType.EAGER) //carga el plan automaticamente cuando consultamos la suscrip
    @JoinColumn(name = "plan_id", nullable = false)//creamos la columna plan_id en la base de datos
    private Plan plan;
}