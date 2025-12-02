package com.parqueadero.parkingbackend.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String nombre;
    private String correo;
    private String password;
    private String rol;
}