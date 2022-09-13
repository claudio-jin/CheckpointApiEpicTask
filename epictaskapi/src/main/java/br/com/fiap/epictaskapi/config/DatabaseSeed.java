package br.com.fiap.epictaskapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.TaskRepository;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Configuration
@Profile("dev")
@Transactional
public class DatabaseSeed implements CommandLineRunner {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        userRepository.save( new User()
                        .name("Cláudio")
                        .email("claudio@hotmail.com")
                        .password(passwordEncoder.encode("123"))
                        // .roles(new Role("ADMIN"))
                        //.getRoles().add(new Role("USER"))   
                        );

        taskRepository.saveAll(List.of( 
            new Task("BD", "Modelar as tabelas", 150, 0),
            new Task("Debug", "Modelar as tabelas", 20, 0),
            new Task("Cadastro", "Modelar as tabelas", 10, 0),
            new Task("Login", "Modelar as tabelas", 50, 0),
            new Task("Deploy", "Modelar as tabelas", 25, 0),
            new Task("Testes", "Modelar as tabelas", 80, 0),
            new Task("Bug", "Modelar as tabelas", 75, 0),
            new Task("Logout", "Modelar as tabelas", 5, 0),
            new Task("Internacionalização", "Modelar as tabelas", 15, 0),
            new Task("Prototipo", "Prototipar as telas do app", 20, 0),
            new Task("Compliance", "Prototipar as telas do app", 20, 0)
        ));
    }
    
}
