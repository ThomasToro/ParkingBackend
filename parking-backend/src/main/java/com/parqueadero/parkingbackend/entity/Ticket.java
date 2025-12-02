package com.parqueadero.parkingbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String placaVehiculo;

    @Column(nullable = false)
    private LocalDateTime fechaEntrada;

    @Column
    private LocalDateTime fechaSalida;//null mientras el carro está estacionado

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPagar;//calculamos al salir,si es abonado, sera 0

    @Column(nullable = false, length = 20)
    private String tipoCliente;//rotativo o abonado

    @Column(nullable = false, length = 20)
    private String estadoTicket; //activo (en el parqueadero) o pagado si ta salio

    //Un ticket ocupa un espacio específico
    @ManyToOne 
    @JoinColumn(name = "espacio_id", nullable = false)
    private Espacio espacio;
}