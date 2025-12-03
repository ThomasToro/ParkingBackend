package com.parqueadero.parkingbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.parkingbackend.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    //buscamos un tikcet pendiente para pagar de un vehiculo
    Optional<Ticket> findByPlacaVehiculoAndEstadoTicket(String placaVehiculo, String estadoTicket);

    //para las estadísticas traemos todos los tickets de una fecha específica, podemos mirar las ganancias
    List<Ticket> findByEstadoTicket(String estadoTicket);
}