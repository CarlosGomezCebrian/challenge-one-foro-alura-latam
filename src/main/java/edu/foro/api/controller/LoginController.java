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
            summary = "Gets taken to enter",
            description = "Requires username and password",
            tags = { "Login", "Post" })
    public ResponseEntity loginUser(@RequestBody @Valid LoginUserData loginUserData){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginUserData.login(), loginUserData.password_hash());
        var authenticatedUser =  authenticationManager.authenticate(authenticationToken);
        var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(JWTToken));

    }
}
