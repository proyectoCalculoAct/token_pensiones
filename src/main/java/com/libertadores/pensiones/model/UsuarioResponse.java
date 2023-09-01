package com.libertadores.pensiones.model;

import lombok.Getter;

import java.io.Serializable;

public class UsuarioResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    @Getter
    private String tipoUsuario;

    @Getter
    private String username;

    @Getter
    private Integer idUsuario;

    public UsuarioResponse(String jwttoken, String tipoUsuario,String username, Integer idUsuario) {
        this.jwttoken = jwttoken;
        this.tipoUsuario = tipoUsuario;
        this.username = username;
        this.idUsuario = idUsuario;
    }


    public String getToken() {
        return this.jwttoken;

    }


}
