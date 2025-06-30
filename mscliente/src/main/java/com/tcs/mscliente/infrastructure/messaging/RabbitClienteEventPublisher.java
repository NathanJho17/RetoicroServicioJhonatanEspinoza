package com.tcs.mscliente.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.mscliente.domain.events.IClienteEventPublisher;
import com.tcs.mscliente.infrastructure.messaging.dto.ClienteEvent;

@Component
public class RabbitClienteEventPublisher implements IClienteEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void clienteCreadoPublicar(Integer id, String nombre) {
        ClienteEvent evento = new ClienteEvent(id, nombre);
        rabbitTemplate.convertAndSend("clientes.exchange", "cliente.creado", evento);
    }

}
