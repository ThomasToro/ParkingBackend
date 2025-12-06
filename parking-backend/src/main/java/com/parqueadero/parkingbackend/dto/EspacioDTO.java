package com.parqueadero.parkingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspacioDTO {
    @NotBlank(message = "El número del espacio es obligatorio")
    private String numeroEspacio;

    @NotBlank(message = "El estado es obligatorio")
    private String estado; // "LIBRE", "MANTENIMIENTO"

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipoVehiculo;
}