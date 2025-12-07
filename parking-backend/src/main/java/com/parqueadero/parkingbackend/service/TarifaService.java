package com.parqueadero.parkingbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parqueadero.parkingbackend.dto.TarifaDTO;
import com.parqueadero.parkingbackend.entity.Tarifa; //esto lo usamos para ops de escritura
import com.parqueadero.parkingbackend.repository.TarifaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;
		
		//simplemente listamos las tarifas
    public List<Tarifa> listarTarifas() {
        return tarifaRepository.findAll();
    }
		
		//creamos una tarifa
    @Transactional
    public Tarifa crearTarifa(TarifaDTO dto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setTipoVehiculo(dto.getTipoVehiculo());
        tarifa.setUnidadTiempo(dto.getUnidadTiempo());
        tarifa.setPrecio(dto.getPrecio());
        return tarifaRepository.save(tarifa);
    }
		
		//actualizamos una tarifa
    @Transactional
    public Tarifa actualizarTarifa(Long id, TarifaDTO dto) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));
        
        //actualizamos el precio, asumiendo que el tipo y unidad no cambian
        tarifa.setPrecio(dto.getPrecio());
        
        return tarifaRepository.save(tarifa);
    }
	
	  //eliminamos una tarifa
    public void eliminarTarifa(Long id) {
        tarifaRepository.deleteById(id);
    }
}