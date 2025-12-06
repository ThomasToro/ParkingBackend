package com.parqueadero.parkingbackend.controller;

import com.parqueadero.parkingbackend.dto.AuthDTO;
import com.parqueadero.parkingbackend.dto.RegisterRequestDTO;
import com.parqueadero.parkingbackend.entity.Usuario;
import com.parqueadero.parkingbackend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; //OJO
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios") //esta ruta sí la tenemos protegida
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    //De lo que se encarga este archivo es de crear nuevos usuarios, admin o empleado, solo un admin puede crear nuevos usuarios
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<String> crearUsuario(@RequestBody RegisterRequestDTO registerRequestDto) {
        
        if (usuarioRepository.findByCorreo(registerRequestDto.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya esta registrado");
        }

        Usuario user = new Usuario();
        user.setNombre(registerRequestDto.getNombre());
        user.setCorreo(registerRequestDto.getCorreo());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        
        //si no mandamos rol, se crea como un empleado
        if (registerRequestDto.getRol() == null || registerRequestDto.getRol().isEmpty()) {
            user.setRol("EMPLEADO"); 
        } else {
            user.setRol(registerRequestDto.getRol());
        }
        
        usuarioRepository.save(user);
        return ResponseEntity.ok("El usuario fue registrado exitosamente");
    }

    //aqui lo que hacemos es listar los empeados, solamente el admin puede hacer eso
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}