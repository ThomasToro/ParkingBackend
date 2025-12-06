package com.parqueadero.parkingbackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parkingbackend.dto.IngresoDTO;
import com.parqueadero.parkingbackend.dto.SalidaDTO;
import com.parqueadero.parkingbackend.entity.Ticket;

import org.springframework.security.access.prepost.PreAuthorize; // Important

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.parqueadero.parkingbackend.service.ParkingService;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor //inicicia cosas automaticamente reduciendo el bolierplate code 
public class ParkingController {

    private final ParkingService parkingService;

    @PostMapping("/entrada")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    public ResponseEntity<?> registrarIngreso(@RequestBody IngresoDTO ingresoDto) {

        try {
            Ticket ticket = parkingService.registrarIngreso(ingresoDto.getPlacaVehiculo(), ingresoDto.getTipoVehiculo()); 
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/salida")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    public ResponseEntity<?> registrarSalida(@RequestBody SalidaDTO salidaDto) {

        try {
            Ticket ticket = parkingService.registrarSalida(salidaDto.getPlacaVehiculo());
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/vehiculos-activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO')")
    public ResponseEntity<List<Ticket>> verVehiculosParqueados() {
        List<Ticket> ticketsActivos = parkingService.obtenerVehiculosEnParqueadero();
        
        if (ticketsActivos.isEmpty()) {
            return ResponseEntity.noContent().build(); //204 si esta vacio
        }
        
        return ResponseEntity.ok(ticketsActivos); //retorna 200 con la lista de tickets
    }
    
    
    
}
