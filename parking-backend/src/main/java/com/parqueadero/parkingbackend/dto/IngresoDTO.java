package com.parqueadero.parkingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IngresoDTO {

    @NotBlank(message = "La placa del vehiculo no puede estar vacia")
    private String placaVehiculo;

    @NotBlank(message = "El tipo del vehiculo a ingresar no puede ir vacio")
    private String tipoVehiculo;
    
}
