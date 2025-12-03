package com.parqueadero.parkingbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.parkingbackend.entity.Espacio;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    
    //buscamos el primer espacio libre para un carro o moto
    Optional<Espacio> findFirstByEstadoAndTipoVehiculo(String estado, String tipoVehiculo);

    //listamos todos los espacios libres y ocupados
    List<Espacio> findByEstado(String estado);
    
    //asi podemos buscar un espacio por su nombre: A-01
    Optional<Espacio> findByNumeroEspacio(String numeroEspacio);
}