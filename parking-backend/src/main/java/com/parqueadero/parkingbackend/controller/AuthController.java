package com.parqueadero.parkingbackend.controller;

import com.parqueadero.parkingbackend.dto.AuthDTO;
import com.parqueadero.parkingbackend.dto.AuthResponseDTO;
import com.parqueadero.parkingbackend.entity.Usuario;
import com.parqueadero.parkingbackend.repository.UsuarioRepository;
import com.parqueadero.parkingbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; //importante 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder; //importante
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthDTO authRequest) {
        //Validamos credenciales, si funiona, el user existe
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword())
        );

        //buscamos al usuario y sacamos el rol
        //pero lo buscamos para obtener el objeto completo.
        Usuario usuario = usuarioRepository.findByCorreo(authRequest.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")); 

        //Generar el token usando el rol
        String token = jwtService.generateToken(usuario.getCorreo(), usuario.getRol());

        return new AuthResponseDTO(token);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthDTO request) {
        
        //miramos si el correo ya existe antes de guardar
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        //lo creamos
        Usuario user = new Usuario();
        user.setNombre(request.getNombre());
        user.setCorreo(request.getCorreo());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encriptamos pass
        
        //si no mandamos rol, sera empleado por default
        if (request.getRol() == null || request.getRol().isEmpty()) {
            user.setRol("EMPLEADO"); 
        } else {
            user.setRol(request.getRol());
        }
        
        usuarioRepository.save(user);

        
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}