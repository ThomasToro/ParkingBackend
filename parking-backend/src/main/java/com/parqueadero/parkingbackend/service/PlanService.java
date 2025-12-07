package com.parqueadero.parkingbackend.service;

import com.parqueadero.parkingbackend.dto.PlanDTO;
import com.parqueadero.parkingbackend.entity.Plan;
import com.parqueadero.parkingbackend.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
		
		//listamos los planes
    public List<Plan> listarPlanes() {
        return planRepository.findAll();
    }
		
		//creamos un plan
    @Transactional
    public Plan crearPlan(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setNombrePlan(dto.getNombrePlan());
        plan.setPrecio(dto.getPrecio());
        plan.setDuracionDias(dto.getDuracionDias());
        plan.setTipoVehiculo(dto.getTipoVehiculo());
        return planRepository.save(plan);
    }
		
		//actualizamos un plan
    @Transactional
    public Plan actualizarPlan(Long id, PlanDTO dto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        
        //aca actualizamos el tipoVehiculo para evitar
        //inconsistencias si ya hay suscripciones activas relaionadas a este plan
        plan.setNombrePlan(dto.getNombrePlan());
        plan.setPrecio(dto.getPrecio());
        plan.setDuracionDias(dto.getDuracionDias());
        
        return planRepository.save(plan);
    }
		
		//eliminamos un plan
    public void eliminarPlan(Long id) {
        planRepository.deleteById(id);
    }
}