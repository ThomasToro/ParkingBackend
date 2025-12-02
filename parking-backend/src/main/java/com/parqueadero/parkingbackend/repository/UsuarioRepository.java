package com.parqueadero.parkingbackend.repository;

import com.parqueadero.parkingbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //SQL automáticamente para buscar por correo
    Optional<Usuario> findByCorreo(String correo);
}

