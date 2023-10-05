package edu.foro.api.controller;


import edu.foro.api.domain.user.*;
import edu.foro.api.infra.errors.IntegrityValidity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Register a new user",
            description = "",
            tags = { "User", "Post" })
    public ResponseEntity<DataDetailUser>  setDataRegistrationUser(@RequestBody @Valid DataRegistrationUser dataRegistrationUser) throws IntegrityValidity {
        var response = userService.setDataRegistrationUser(dataRegistrationUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Gets a list of active users",
            description = "",
            tags = { "User", "Get" })
    public ResponseEntity<Page<DataDetailUser>> userList(@PageableDefault(size = 15, sort = {"id"}) Pageable pageable){
        var response = userService.listarActivated(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @GetMapping
    @Operation(
            summary = "Inactivate users",
            description = "",
            tags = { "User", "Delete" })
    public ResponseEntity inactiveUser(@RequestBody @Valid InactiveUserData inactiveUserData){
        userService.inactive(inactiveUserData);
        return  ResponseEntity.ok("Topic with ID " + inactiveUserData.id() + " successfully inactive");
    }
    @PutMapping
    @Operation(
            summary = "Update users",
            description = "",
            tags = { "User", "Put" })
    public ResponseEntity<DataDetailUser> updateUser(@RequestBody @Valid UpdateUserData updateUserData) throws IntegrityValidity{
        var response = userService.setDataUpdateUser(updateUserData);
        return ResponseEntity.ok(response);
    }
}
