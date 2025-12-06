package com.parqueadero.parkingbackend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.parqueadero.parkingbackend.dto.SuscripcionDTO;
import com.parqueadero.parkingbackend.entity.Plan;
import com.parqueadero.parkingbackend.entity.Suscripcion;
import com.parqueadero.parkingbackend.repository.PlanRepository;
import com.parqueadero.parkingbackend.repository.SuscripcionRepository;
 
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final PlanRepository planRepository;

    public List<Suscripcion> listarSuscripciones() {
        return suscripcionRepository.findAll();
    }

    @Transactional
    public Suscripcion crearSuscripcion(SuscripcionDTO dto) {
        //buscamos el plan completo usando el id que viene en el dto
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        //calculamos fechas 
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = inicio.plusDays(plan.getDuracionDias());

        //armamos 
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setPlacaVehiculo(dto.getPlacaVehiculo());
        suscripcion.setFechaInicio(inicio);
        suscripcion.setFechaFin(fin);
        suscripcion.setEstado("ACTIVO");
        suscripcion.setMontoPagado(plan.getPrecio());
        suscripcion.setPlan(plan); //se guarda la relacion

        return suscripcionRepository.save(suscripcion);
    }

    @Transactional
    public Suscripcion actualizarSuscripcion(Long id, Suscripcion info) {
        Suscripcion sus = suscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));
        
        //solo editar cosas administrativas, no el plan ni la placa
        sus.setEstado(info.getEstado());
        sus.setFechaFin(info.getFechaFin());
        
        return suscripcionRepository.save(sus);
    }

    public void eliminarSuscripcion(Long id) {
        suscripcionRepository.deleteById(id);
    }
}