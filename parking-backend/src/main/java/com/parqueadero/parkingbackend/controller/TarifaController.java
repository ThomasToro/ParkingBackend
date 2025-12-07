package com.parqueadero.parkingbackend.controller;

import com.parqueadero.parkingbackend.dto.TarifaDTO;
import com.parqueadero.parkingbackend.entity.Tarifa;
import com.parqueadero.parkingbackend.service.TarifaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Vital para roles
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    //con este controller listaremos las tarifas
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public List<Tarifa> listar() {
        return tarifaService.listarTarifas();
    }

    //solo el admin puede eliminar tarifas
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
        return ResponseEntity.ok().build();
    }

    //solo el admin puede crear las tarifas
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Tarifa crear(@RequestBody @Valid TarifaDTO dto) {
        return tarifaService.crearTarifa(dto);
    }

    //solamente al admin puede editar las tarifas
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tarifa> actualizar(@PathVariable Long id, @RequestBody @Valid TarifaDTO dto) {
        return ResponseEntity.ok(tarifaService.actualizarTarifa(id, dto));
    }
}