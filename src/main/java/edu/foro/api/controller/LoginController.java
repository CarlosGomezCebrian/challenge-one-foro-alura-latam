package edu.foro.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import edu.foro.api.infra.security.LoginUserData;
import edu.foro.api.domain.user.User;
import edu.foro.api.infra.security.JWTTokenData;
import edu.foro.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(
            summary = "obtiene taken para ingresar",
            description = "requiere usuario y contraseña",
            tags = { "Login", "Post" })
    public ResponseEntity loginUser(@RequestBody @Valid LoginUserData loginUserData ) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginUserData.login(),
                loginUserData.password_hash());
        System.out.println("1Login "+loginUserData.login() + " Password " + loginUserData.password_hash());
        System.out.println("2authenticationToken "+ authenticationToken);
        var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);

        System.out.println("3usuarioAutenticado "+ usuarioAutenticado);
        var JWTToken = tokenService.generarToken((User) usuarioAutenticado.getPrincipal());
        System.out.println("4JWTToken "+ JWTToken);
        System.out.println("5usuarioAutenticado.getPrincipal() "+ usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(JWTToken));

    }


}
