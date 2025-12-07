package com.parqueadero.parkingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    //si viene vacío, mantenemos la contraseña vieja,si trae algo, la actualizamos
    private String password; 
}