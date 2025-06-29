package com.tcs.mscuenta.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.mscuenta.application.dto.cuenta.CuentaCrearDTO;
import com.tcs.mscuenta.application.dto.cuenta.CuentaEditarDTO;
import com.tcs.mscuenta.application.dto.cuenta.CuentaVerDTO;
import com.tcs.mscuenta.application.response.RespuestaGenerica;
import com.tcs.mscuenta.application.usecase.cuenta.BorrarCuentaCase;
import com.tcs.mscuenta.application.usecase.cuenta.CrearCuentaCase;
import com.tcs.mscuenta.application.usecase.cuenta.EditarCuentaCase;
import com.tcs.mscuenta.application.usecase.cuenta.VerCuentaPorNumeroCase;
import com.tcs.mscuenta.application.usecase.cuenta.VerCuentasCase;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private CrearCuentaCase _crearCuentaCase;
    private VerCuentasCase _verCuentasCase;
    private VerCuentaPorNumeroCase _verCuentaPorNumeroCase;
    private BorrarCuentaCase _borrarCuentaCase;
    private EditarCuentaCase _editarCuentaCase;

    public CuentaController(CrearCuentaCase crearCuentaCase, VerCuentasCase verCuentasCase,
            VerCuentaPorNumeroCase verCuentaPorNumeroCase,
            BorrarCuentaCase borrarCuentaCase,
            EditarCuentaCase editarCuentaCase) {
        _crearCuentaCase = crearCuentaCase;
        _verCuentasCase = verCuentasCase;
        _verCuentaPorNumeroCase = verCuentaPorNumeroCase;
        _borrarCuentaCase = borrarCuentaCase;
        _editarCuentaCase = editarCuentaCase;
    }

    @PostMapping()
    public ResponseEntity<RespuestaGenerica<Integer>> saveCuenta(@Valid @RequestBody CuentaCrearDTO cuentaCrearDTO) {

        RespuestaGenerica<Integer> response = _crearCuentaCase.saveCuenta(cuentaCrearDTO);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping()
    public ResponseEntity<RespuestaGenerica<List<CuentaVerDTO>>> getAllCuentas() {

        RespuestaGenerica<List<CuentaVerDTO>> response = _verCuentasCase.listAll();
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/cuenta")
    public ResponseEntity<RespuestaGenerica<CuentaVerDTO>> getByCuenta(
            @RequestParam(name = "numeroCuenta") String numeroCuenta) {
        RespuestaGenerica<CuentaVerDTO> response = _verCuentaPorNumeroCase.getByNumeroCuenta(numeroCuenta);
        if (response.isSatisfactorio()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaGenerica<Integer>> softDeleteCuenta(@PathVariable(name = "id") Integer id) {
        RespuestaGenerica<Integer> response = _borrarCuentaCase.delete(id);
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
    public ResponseEntity<RespuestaGenerica<CuentaVerDTO>> updateCuenta(@PathVariable(name = "id") Integer id,
            @RequestBody CuentaEditarDTO cuentaEditarDTO) {
        RespuestaGenerica<CuentaVerDTO> response = _editarCuentaCase.update(id, cuentaEditarDTO);
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
