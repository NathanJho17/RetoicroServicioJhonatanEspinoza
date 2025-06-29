package com.tcs.mscuenta.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.mscuenta.application.dto.movimiento.MovimientoCrearDTO;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.application.usecase.movimiento.CrearMovimientoCase;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private CrearMovimientoCase _crearMovimientoCase;

    public MovimientoController(CrearMovimientoCase crearMovimientoCase) {
        _crearMovimientoCase = crearMovimientoCase;
    }

    @PostMapping()
    public ResponseEntity<RespuestaGenerica<Integer>> saveMovimiento(@Valid @RequestBody MovimientoCrearDTO movimientoCrearDTO) {

        RespuestaGenerica<Integer> response = _crearMovimientoCase.saveMovimiento(movimientoCrearDTO);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }




      //Validador DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> ErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}
