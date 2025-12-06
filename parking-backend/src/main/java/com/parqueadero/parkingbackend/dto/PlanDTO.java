package com.parqueadero.parkingbackend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlanDTO {
    @NotBlank(message = "El nombre del plan es obligatorio")
    private String nombrePlan;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos de 1 día")
    private Integer duracionDias;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipoVehiculo;
}