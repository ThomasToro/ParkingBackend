package com.parqueadero.parkingbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Importante
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parkingbackend.dto.PlanDTO;
import com.parqueadero.parkingbackend.entity.Plan;
import com.parqueadero.parkingbackend.service.PlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    // LISTAR: Admin y Empleado (Para saber qué vender)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public List<Plan> listar() {
        return planService.listarPlanes();
    }

    // CREAR: Solo Admin
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Plan crear(@RequestBody @Valid PlanDTO dto) {
        return planService.crearPlan(dto);
    }

    // ACTUALIZAR: Solo Admin
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Plan> actualizar(@PathVariable Long id, @RequestBody @Valid PlanDTO dto) {
        return ResponseEntity.ok(planService.actualizarPlan(id, dto));
    }

    // ELIMINAR: Solo Admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminarPlan(id);
        return ResponseEntity.ok().build();
    }
}