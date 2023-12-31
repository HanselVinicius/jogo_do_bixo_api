package com.vinicius.br.jogo_do_bixo.controllers;

import com.vinicius.br.jogo_do_bixo.infra.security.TokenJWTDTO;
import com.vinicius.br.jogo_do_bixo.infra.security.TokenService;
import com.vinicius.br.jogo_do_bixo.models.users.AuthDTO;
import com.vinicius.br.jogo_do_bixo.models.users.RegisterDTO;
import com.vinicius.br.jogo_do_bixo.models.users.Usuario;
import com.vinicius.br.jogo_do_bixo.models.users.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO dto){
        var token = new UsernamePasswordAuthenticationToken(dto.login(),dto.password());
        var authentication = manager.authenticate(token);
        var jwtToken = tokenService.geraToken((Usuario) authentication.getPrincipal());
        var user = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(new TokenJWTDTO(jwtToken,user));
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){
        if (this.repository.findByLogin(dto.login()) != null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(repository.save(tokenService.getUser(dto)));
    }
}
