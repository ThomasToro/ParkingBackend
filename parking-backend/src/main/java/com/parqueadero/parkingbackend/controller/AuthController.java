package com.parqueadero.parkingbackend.controller;

import com.parqueadero.parkingbackend.dto.AuthDTO;
import com.parqueadero.parkingbackend.dto.AuthResponseDTO;
import com.parqueadero.parkingbackend.dto.LoginRequestDTO;
import com.parqueadero.parkingbackend.entity.Usuario;
import com.parqueadero.parkingbackend.repository.UsuarioRepository;
import com.parqueadero.parkingbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getCorreo(), loginRequestDto.getPassword())
        );

        Usuario usuario = usuarioRepository.findByCorreo(loginRequestDto.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")); 

        String token = jwtService.generateToken(usuario.getCorreo(), usuario.getRol());

        return new AuthResponseDTO(token);
    }
    
    //no puse el register porque ese sí necesita verificar que sea un admin para crear usuarios nuevos
}