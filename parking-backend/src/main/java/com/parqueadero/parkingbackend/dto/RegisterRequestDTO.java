package com.parqueadero.parkingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    //todos estos campos son obligatorios
    @NotBlank(message = "El nombre no puede ir vacio")
    private String nombre;
    
    @NotBlank(message = "El correo no puede ir vacio")
    private String correo;
    
    @NotBlank(message = "La contraseña no puede ir vacia")
    private String password;
    
    // Este campo puede ir vacío, así que no lleva @NotBlank
    private String rol; 
}
