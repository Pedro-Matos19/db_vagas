package com.ufape.vagas.services;

import com.ufape.vagas.enums.UserStatus;
import com.ufape.vagas.exceptions.IdNotFoundException;
import com.ufape.vagas.models.User;
import com.ufape.vagas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public User create(User user) {
    	user.enableStatus();
    	user = save(user);
    	
    	return user;
    }

    public void disableUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        
        User user = optionalUser.orElseThrow(() -> new IdNotFoundException());
        
        user.disableStatus();;
        
        save(user);
    }
}