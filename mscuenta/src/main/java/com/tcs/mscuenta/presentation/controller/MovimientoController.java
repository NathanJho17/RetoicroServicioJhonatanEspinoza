package com.tcs.mscuenta.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.tcs.mscuenta.application.dto.movimiento.MovimientoCrearDTO;
import com.tcs.mscuenta.application.dto.movimiento.MovimientoEditarDTO;
import com.tcs.mscuenta.application.dto.movimiento.MovimientoVerDTO;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.application.usecase.movimiento.BorrarMovimientoCase;
import com.tcs.mscuenta.application.usecase.movimiento.CrearMovimientoCase;
import com.tcs.mscuenta.application.usecase.movimiento.EditarMovimientoCase;
import com.tcs.mscuenta.application.usecase.movimiento.VerMovimientosCase;
import com.tcs.mscuenta.application.usecase.movimiento.VerMovimientosCuentaCase;
import com.tcs.mscuenta.application.usecase.movimiento.VerMovimientosNumeroCuentaCase;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private CrearMovimientoCase _crearMovimientoCase;
    private VerMovimientosCase _verMovimientosCase;
    private VerMovimientosCuentaCase _verMovimientosCuentaCase;
    private VerMovimientosNumeroCuentaCase _verMovimientosNumeroCuentaCase;
    private BorrarMovimientoCase _borrarMovimientoCase;
    private EditarMovimientoCase _editarMovimientoCase;

    public MovimientoController(CrearMovimientoCase crearMovimientoCase,
            VerMovimientosCase verMovimientosCase,
            VerMovimientosCuentaCase verMovimientosCuentaCase,
            VerMovimientosNumeroCuentaCase verMovimientosNumeroCuentaCase,
            BorrarMovimientoCase borrarMovimientoCase,
            EditarMovimientoCase editarMovimientoCase) {
        _crearMovimientoCase = crearMovimientoCase;
        _verMovimientosCase = verMovimientosCase;
        _verMovimientosCuentaCase = verMovimientosCuentaCase;
        _verMovimientosNumeroCuentaCase = verMovimientosNumeroCuentaCase;
        _borrarMovimientoCase = borrarMovimientoCase;
        _editarMovimientoCase = editarMovimientoCase;
    }

    @PostMapping()
    public ResponseEntity<RespuestaGenerica<MovimientoVerDTO>> saveMovimiento(
            @Valid @RequestBody MovimientoCrearDTO movimientoCrearDTO) {

        RespuestaGenerica<MovimientoVerDTO> response = _crearMovimientoCase.saveMovimiento(movimientoCrearDTO);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping()
    public ResponseEntity<RespuestaGenerica<List<MovimientoVerDTO>>> getAllMovimientos() {

        RespuestaGenerica<List<MovimientoVerDTO>> response = _verMovimientosCase.listAll();
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/cuenta/{id}")
    public ResponseEntity<RespuestaGenerica<List<MovimientoVerDTO>>> getAllMovimientosCuenta(
            @PathVariable(name = "id") Integer id) {

        RespuestaGenerica<List<MovimientoVerDTO>> response = _verMovimientosCuentaCase.listAllByCuenta(id);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/cuenta")
    public ResponseEntity<RespuestaGenerica<List<MovimientoVerDTO>>> getAllMovimientosNumeroCuenta(
            @RequestParam(name = "numeroCuenta") String numeroCuenta) {

        RespuestaGenerica<List<MovimientoVerDTO>> response = _verMovimientosNumeroCuentaCase
                .listAllByNumeroCuenta(numeroCuenta);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaGenerica<Boolean>> harDeleteMovimiento(
            @PathVariable(name = "id") Integer id) {

        RespuestaGenerica<Boolean> response = _borrarMovimientoCase
                .hardDelete(id);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaGenerica<MovimientoVerDTO>> updateCuenta(@PathVariable(name = "id") Integer id,
            @RequestBody MovimientoEditarDTO movimientoEditarDTO) {
        RespuestaGenerica<MovimientoVerDTO> response = _editarMovimientoCase.updateMovimiento(movimientoEditarDTO,id);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    // Validador DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> ErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}
