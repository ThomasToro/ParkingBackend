package com.parqueadero.parkingbackend.controller;

import com.parqueadero.parkingbackend.dto.RegisterRequestDTO;
import com.parqueadero.parkingbackend.dto.UserUpdateDTO; //ojo
import com.parqueadero.parkingbackend.entity.Usuario;
import com.parqueadero.parkingbackend.repository.UsuarioRepository;
import jakarta.validation.Valid; //ojo
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    //crear usuario
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<String> crearUsuario(@RequestBody @Valid RegisterRequestDTO registerRequestDto) {
        if (usuarioRepository.findByCorreo(registerRequestDto.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        Usuario user = new Usuario();
        user.setNombre(registerRequestDto.getNombre());
        user.setCorreo(registerRequestDto.getCorreo());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        
        if (registerRequestDto.getRol() == null || registerRequestDto.getRol().isEmpty()) {
            user.setRol("EMPLEADO"); 
        } else {
            user.setRol(registerRequestDto.getRol());
        }
        
        usuarioRepository.save(user);
        return ResponseEntity.ok("El usuario fue registrado exitosamente");
    }

    //  listar usuarios
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // editar usuario
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updateDTO) {
        
        //buscamos al usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //actualizamos los datos básicos
        usuario.setNombre(updateDTO.getNombre());
        usuario.setCorreo(updateDTO.getCorreo());
        usuario.setRol(updateDTO.getRol());


        //solo si el campo password no viene vacio,lo encriptamos y actualizamos
        //pero si viene null o vacio lo dejamos la contraseña vieja intacta
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    //eliminar usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        //verificamos si el usuario existe antes de eliminar
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}