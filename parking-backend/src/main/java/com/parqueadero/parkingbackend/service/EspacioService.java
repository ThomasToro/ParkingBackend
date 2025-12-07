package com.parqueadero.parkingbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parqueadero.parkingbackend.dto.EspacioDTO;
import com.parqueadero.parkingbackend.entity.Espacio;
import com.parqueadero.parkingbackend.repository.EspacioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public List<Espacio> listarEspacios() {
        return espacioRepository.findAll();
    }

    public Espacio crearEspacio(EspacioDTO dto) {
        //volvemos al dto en una entidad
        Espacio espacio = new Espacio();
        espacio.setNumeroEspacio(dto.getNumeroEspacio());
        espacio.setEstado(dto.getEstado());
        espacio.setTipoVehiculo(dto.getTipoVehiculo());
        return espacioRepository.save(espacio);
    }

    public Espacio actualizarEspacio(Long id, EspacioDTO dto) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
        
        //aca hacemos la actualizacion de los datos
        espacio.setNumeroEspacio(dto.getNumeroEspacio()); //dejamos corregir el nombre
        espacio.setEstado(dto.getEstado());
        espacio.setTipoVehiculo(dto.getTipoVehiculo());
        
        return espacioRepository.save(espacio);
    }

    public void eliminarEspacio(Long id) {
        espacioRepository.deleteById(id);
    }
}