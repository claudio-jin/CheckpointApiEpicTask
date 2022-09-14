package br.com.fiap.epictaskapi.dto;

import br.com.fiap.epictaskapi.model.User;

public class UserDtoNoPassword {
    
    private Long id;
    private String name;
    private String email;

    public UserDtoNoPassword () {

    }

    public UserDtoNoPassword (User user) {
        this.id = user.getId();
        this.name = user.getName();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
