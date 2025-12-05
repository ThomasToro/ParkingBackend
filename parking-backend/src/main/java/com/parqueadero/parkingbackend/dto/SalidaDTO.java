package com.parqueadero.parkingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SalidaDTO {

    @NotBlank(message = "La placa del vehiculo a sacar del parqueadero no puede ir vacia")
    private String placaVehiculo;
    
}
