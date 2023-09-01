package com.libertadores.pensiones.controller;

import com.libertadores.pensiones.model.UsuarioRequest;
import com.libertadores.pensiones.model.UsuarioResponse;
import com.libertadores.pensiones.persistence.repository.UserRepository;
import com.libertadores.pensiones.service.impl.JwtUserDetailsService;
import com.libertadores.pensiones.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("libertadores")
@CrossOrigin("*")
public class JwtAuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<UsuarioResponse> createAuthenticationToken(@RequestBody UsuarioRequest authenticationRequest) throws Exception {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        String tipoUsuario = jwtUtil.validationOfUserFields(authenticationRequest.getUsername());

        String username = jwtUtil.obtainUser(authenticationRequest.getUsername());


        Integer idUsuario = jwtUtil.obtainIdUser(authenticationRequest.getUsername());
        UsuarioResponse tokenInfo = new UsuarioResponse(jwt,tipoUsuario, username, idUsuario );

        return ResponseEntity.ok(tokenInfo);
    }
}


