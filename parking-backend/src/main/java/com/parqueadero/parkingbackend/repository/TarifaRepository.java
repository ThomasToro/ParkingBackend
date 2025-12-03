package com.parqueadero.parkingbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.parkingbackend.entity.Tarifa;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    //podemos buscar cuanto cuesta la hora de un auto
    Optional<Tarifa> findByTipoVehiculoAndUnidadTiempo(String tipoVehiculo, String unidadTiempo);
}