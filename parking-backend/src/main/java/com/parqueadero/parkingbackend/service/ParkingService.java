package com.parqueadero.parkingbackend.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.parqueadero.parkingbackend.entity.Espacio;
import com.parqueadero.parkingbackend.entity.Tarifa;
import com.parqueadero.parkingbackend.entity.Ticket;
import com.parqueadero.parkingbackend.repository.EspacioRepository;
import com.parqueadero.parkingbackend.repository.SuscripcionRepository;
import com.parqueadero.parkingbackend.repository.TarifaRepository;
import com.parqueadero.parkingbackend.repository.TicketRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //esto inyecta los repositorios automaticamente
public class ParkingService {
    private final TicketRepository ticketRepository;
    private final EspacioRepository espacioRepository;
    private final TarifaRepository tarifaRepository;
    private final SuscripcionRepository suscripcionRepository;
    
    @Transactional 
    public Ticket registrarIngreso (String placaVehiculo, String tipoVehiculo){

        //primero debemos mirar si ya hay un ticker activo para esa placa

        Optional <Ticket> ticketActivo= ticketRepository.findByPlacaVehiculoAndEstadoTicket(placaVehiculo, "ACTIVO");

        if (ticketActivo.isPresent()){
            throw new RuntimeErrorException(null, "El vehiculo ya tiene un Ticket activo en el parqueadero en este momento");

        }

        Espacio espacioLibre = espacioRepository.findFirstByEstadoAndTipoVehiculo("LIBRE", tipoVehiculo).orElseThrow(()-> new RuntimeErrorException(null,"No hay espacios disponibles para el tipo de vehiculo: "+tipoVehiculo));


        Boolean esAbonado = suscripcionRepository.findByPlacaVehiculoAndEstado(placaVehiculo,"ACTIVO").isPresent();


        Ticket nuevoTicket = new Ticket();

        nuevoTicket.setPlacaVehiculo(placaVehiculo);
        nuevoTicket.setFechaEntrada(LocalDateTime.now());
        nuevoTicket.setEstadoTicket("ACTIVO");
        nuevoTicket.setTipoCliente(esAbonado? "ABONADO":"ROTATIVO");
        nuevoTicket.setEspacio(espacioLibre);

        espacioLibre.setEstado("OCUPADO");
    
        espacioRepository.save(espacioLibre);
        return ticketRepository.save(nuevoTicket);

    }

    @Transactional
    public Ticket registrarSalida(String placaVehiculo){

        Ticket ticket= ticketRepository.findByPlacaVehiculoAndEstadoTicket(placaVehiculo,"ACTIVO").orElseThrow(()-> new RuntimeErrorException(null,"No se ha encontrado algun ticket activo asociado a la placa proporcionada: "+ placaVehiculo));

        ticket.setFechaSalida(LocalDateTime.now());

        BigDecimal totalPagar = calcularCosto(ticket);
        ticket.setTotalPagar(totalPagar);

        ticket.setEstadoTicket("PAGADO");


        Espacio espacio = ticket.getEspacio();

        espacio.setEstado("LIBRE");

        espacioRepository.save(espacio); 

        return ticketRepository.save(ticket);



    }
    

    private BigDecimal calcularCosto(Ticket ticket){

        if (ticket.getTipoCliente().equals("ABONADO")) {
            return BigDecimal.ZERO;
            
        }

        long minutos= Duration.between(ticket.getFechaEntrada(), ticket.getFechaSalida()).toMinutes();
        double horas= Math.ceil(minutos/60.0); //redondea de modo que si es de 1 hora y 1 minuto, redondea a 2 horas

        String tipoVehiculo = ticket.getEspacio().getTipoVehiculo();


        Tarifa tarifa = tarifaRepository.findByTipoVehiculoAndUnidadTiempo(tipoVehiculo, "HORA").orElseThrow(()-> new RuntimeErrorException(null,"No se ha encontado la tarifa por HORA, asociada al tipo de vehiculo"+tipoVehiculo ));

        //se calcula como el precio * las horas que ya encontramos
        //tenemos que retornar un bigdecimal porque ese es el tipo de nuestra funcion
        return tarifa.getPrecio().multiply(new BigDecimal(horas));

    }


}
