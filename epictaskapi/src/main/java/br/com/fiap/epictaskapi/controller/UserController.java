package br.com.fiap.epictaskapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.dto.UserDtoNoPassword;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    //cadastro de usuarios
    //ok
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        String newPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);
        service.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    //listagem de usuarios
    //ok
    @GetMapping
    @PreAuthorize("permitAll()")
    public Page<UserDtoNoPassword> index(@PageableDefault(size = 5) Pageable paginacao){
        Page<User> user = service.listAll(paginacao);
        Page<UserDtoNoPassword> dto = user.map( d -> new UserDtoNoPassword(d));

        return dto;
    }

    //Listagem de um usuario
    //implementar dto sem senha
    //ok
    @GetMapping("{id}")
    @PreAuthorize("permitAll()")    //qualquer um pode listar
    public ResponseEntity<List<UserDtoNoPassword>> show(@PathVariable Long id){
        var lista = service.listDtoUser(id);
        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(lista);
    }

    //atualização de um usuario
    //implementar dto atualização sem senha
    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()") //autenticado
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UserDtoNoPassword newUser){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var user = optional.get();
        BeanUtils.copyProperties(newUser, user);
        user.setId(id);

        service.save(user);
        return ResponseEntity.ok(user);
    }

    //Remove pelo id
    //ok
    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()") //autenticado
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
