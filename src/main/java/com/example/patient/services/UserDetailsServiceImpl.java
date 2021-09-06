package com.example.patient.services;

import com.example.patient.entities.UserEntity;
import com.example.patient.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameField) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmailOrUsername(usernameField, usernameField);
        if(user == null){
            throw new UsernameNotFoundException("adresse mail non reconnu " + usernameField);
        }else{
            return new UserDetailsImpl(user);
        }

    }
}
