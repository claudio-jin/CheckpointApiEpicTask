package br.com.fiap.epictaskapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    public Page<User> listAll(Pageable pagina) {
        return userRepository.findAll(pagina);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

}
