package com.parqueadero.parkingbackend.controller;

import com.parqueadero.parkingbackend.dto.SuscripcionDTO;
import com.parqueadero.parkingbackend.entity.Suscripcion;
import com.parqueadero.parkingbackend.service.SuscripcionService;
import jakarta.validation.Valid; //validar el DTO
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    //ver esto para validar ingresos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public List<Suscripcion> listar() {
        return suscripcionService.listarSuscripciones();
    }

    //solo el Admin vende suscripciones
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Suscripcion> crear(@RequestBody @Valid SuscripcionDTO dto) {
        return ResponseEntity.ok(suscripcionService.crearSuscripcion(dto));
    }

    //solo el Admin
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Suscripcion> actualizar(@PathVariable Long id, @RequestBody Suscripcion suscripcion) {
        return ResponseEntity.ok(suscripcionService.actualizarSuscripcion(id, suscripcion));
    }

    //solo el Admin puede quitar una sub
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        suscripcionService.eliminarSuscripcion(id);
        return ResponseEntity.ok().build();
    }
}