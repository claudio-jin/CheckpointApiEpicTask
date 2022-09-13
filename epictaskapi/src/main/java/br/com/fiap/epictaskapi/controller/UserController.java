package br.com.fiap.epictaskapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;
    
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        service.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

}
