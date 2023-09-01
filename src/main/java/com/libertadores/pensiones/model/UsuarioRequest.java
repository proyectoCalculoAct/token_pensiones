package com.libertadores.pensiones.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class UsuarioRequest implements Serializable {
    private String username;
    private String password;

    public UsuarioRequest() {
    }

    public UsuarioRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
