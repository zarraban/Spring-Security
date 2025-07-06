package com.example.Spring_Security.service.user;

import com.example.Spring_Security.dto.UserDto;
import com.example.Spring_Security.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public interface UserService {
    void save(UserDto userDto);
    User findById(Long id);
    List<User> findAll();
}
