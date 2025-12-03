package com.parqueadero.parkingbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.parkingbackend.entity.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    
    //miramos si hay una suscripcion activa para una placa
    Optional<Suscripcion> findByPlacaVehiculoAndEstado(String placaVehiculo, String estado);
}