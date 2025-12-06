package com.parqueadero.parkingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    //solo necesitamos estas dos cosas.
    @NotBlank(message = "El correo no puede ir vacio")
    private String correo;
    
    @NotBlank(message = "La contraseña no puede ir vacia")
    private String password;
}