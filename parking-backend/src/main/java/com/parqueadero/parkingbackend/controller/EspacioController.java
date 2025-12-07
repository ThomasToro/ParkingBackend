package com.parqueadero.parkingbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parkingbackend.dto.EspacioDTO;
import com.parqueadero.parkingbackend.entity.Espacio;
import com.parqueadero.parkingbackend.service.EspacioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioController {

    private final EspacioService espacioService;

    //ñistar los espacios que hay
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public List<Espacio> listar() {
        return espacioService.listarEspacios();
    }
    
    //eliminamos un espacio en especifico
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        espacioService.eliminarEspacio(id);
        return ResponseEntity.ok().build();
    }

    //crear un espacio
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Espacio crear(@RequestBody @Valid EspacioDTO dto) {
        return espacioService.crearEspacio(dto);
    }

    //actualizar un espacio
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Espacio> actualizar(@PathVariable Long id, @RequestBody @Valid EspacioDTO dto) {
        return ResponseEntity.ok(espacioService.actualizarEspacio(id, dto));
    }
}