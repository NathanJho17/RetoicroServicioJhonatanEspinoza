package com.tcs.mscliente.presentation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.mscliente.application.dto.ClienteCrearDTO;
import com.tcs.mscliente.application.dto.ClienteEditarDTO;
import com.tcs.mscliente.application.dto.ClienteVerDTO;
import com.tcs.mscliente.application.response.RespuestaGenerica;
import com.tcs.mscliente.application.usecase.BorrarClienteCase;
import com.tcs.mscliente.application.usecase.CrearClienteCase;
import com.tcs.mscliente.application.usecase.EditarClienteCase;
import com.tcs.mscliente.application.usecase.VerClientesCase;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private CrearClienteCase _clienteCase;
    private VerClientesCase _verClientesCase;
    private BorrarClienteCase _borrarClienteCase;
    private EditarClienteCase _editarClienteCase;

    public ClienteController(CrearClienteCase clienteCase, VerClientesCase verClientesCase,
            BorrarClienteCase borrarClienteCase,
            EditarClienteCase editarClienteCase) {
        _clienteCase = clienteCase;
        _verClientesCase = verClientesCase;
        _borrarClienteCase = borrarClienteCase;
        _editarClienteCase = editarClienteCase;

    }

    @PostMapping()
    public ResponseEntity<RespuestaGenerica<Integer>> saveClient(@Valid @RequestBody ClienteCrearDTO clienteCrearDTO) {
        RespuestaGenerica<Integer> response = _clienteCase.create(clienteCrearDTO);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping()
    public ResponseEntity<RespuestaGenerica<List<ClienteVerDTO>>> getAllClients() {
        RespuestaGenerica<List<ClienteVerDTO>> response = _verClientesCase.listAll();
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaGenerica<Integer>> softDelete(@PathVariable Integer id) {
        RespuestaGenerica<Integer> response = _borrarClienteCase.delete(id);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            if (response.getError() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaGenerica<ClienteVerDTO>> update(@PathVariable Integer id,
            @RequestBody ClienteEditarDTO clienteEditarDTO) {
        RespuestaGenerica<ClienteVerDTO> response = _editarClienteCase.update(id, clienteEditarDTO);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}
