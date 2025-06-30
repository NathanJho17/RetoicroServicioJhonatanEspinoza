package com.tcs.mscuenta.infrastructure.messaging;

import java.util.Random;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.tcs.mscuenta.application.dto.cuenta.CuentaCrearDTO;
import com.tcs.mscuenta.application.usecase.cuenta.CrearCuentaCase;
import com.tcs.mscuenta.infrastructure.messaging.config.RabbitConfig;
import com.tcs.mscuenta.infrastructure.messaging.dto.ClienteCreadoEvent;

@Component
public class ClienteCreadoListener {

    private CrearCuentaCase _crearCuentaCase;

    public ClienteCreadoListener(CrearCuentaCase crearCuentaCase) {
        _crearCuentaCase = crearCuentaCase;

    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void onClienteCreado(ClienteCreadoEvent event) {
        System.out.println("Evento recibido: " + event.getNombre());

        CuentaCrearDTO cuenta = new CuentaCrearDTO();
        cuenta.setCliente(event.getNombre());
        cuenta.setSaldoInicial(0.1);
        cuenta.setSaldoDisponible(0.1);
        cuenta.setTipoCuenta("Ahorro");
        cuenta.setNumeroCuenta(generarNumeroCuenta());
        _crearCuentaCase.saveCuenta(cuenta);
    }

    public String generarNumeroCuenta() {
        int longitud = 15;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < longitud; i++) {
            int digito = random.nextInt(10);
            sb.append(digito);
        }

        return sb.toString();
    }
}
