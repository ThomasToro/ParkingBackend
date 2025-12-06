package com.parqueadero.parkingbackend.dto;

import lombok.Data;

@Data
public class SuscripcionDTO {
    private String placaVehiculo;
    private Long planId; // El Front nos manda el ID del plan seleccionado
}