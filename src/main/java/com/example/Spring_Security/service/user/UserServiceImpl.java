package com.example.Spring_Security.service.user;

import com.example.Spring_Security.dto.UserDto;
import com.example.Spring_Security.entity.User;
import com.example.Spring_Security.repository.role.RoleRepository;
import com.example.Spring_Security.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(
    @Qualifier("userRepository") UserRepository userRepository,
    @Qualifier("roleRepository") RoleRepository roleRepository,
    PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public void save(UserDto request) {

    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
