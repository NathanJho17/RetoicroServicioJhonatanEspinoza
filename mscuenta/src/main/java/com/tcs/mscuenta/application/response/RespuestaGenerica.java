package com.tcs.mscuenta.application.response;

import lombok.Data;

@Data
public class RespuestaGenerica<T> {

    private boolean satisfactorio;
    private String mensaje;
    private T datos;
    private String error;

    public RespuestaGenerica(boolean satisfactorio, String mensaje, T datos) {
        this.satisfactorio = satisfactorio;
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public RespuestaGenerica(boolean satisfactorio, String mensaje, T datos, String error) {
        this.satisfactorio = satisfactorio;
        this.mensaje = mensaje;
        this.datos = datos;
        this.error = error;
    }

}
