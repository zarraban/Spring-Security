package com.example.Spring_Security.service.user;

import com.example.Spring_Security.dto.UserDto;
import com.example.Spring_Security.entity.Role;
import com.example.Spring_Security.entity.User;
import com.example.Spring_Security.repository.role.RoleRepository;
import com.example.Spring_Security.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(
    @Qualifier("userRepository") UserRepository userRepository,
    @Qualifier("roleRepository") RoleRepository roleRepository,
    PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional
    public void save(UserDto userDto) {
        User newUser = new User();
        newUser.setFirstName(userDto.getName());
        newUser.setLastName(userDto.getSurname());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEmail(userDto.getEmail());

        Role roles = roleRepository.findByName("ADMIN");
        if(roles==null){
            roles = createNewRole();
        }
        newUser.setRoles(Set.of(roles));
        userRepository.save(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            return null;
        }else {
            return users.stream().map(this::entityToDto).collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public int countAll(){
       List<User> users = userRepository.findAll();
       if(users.isEmpty()){
           return 0;
       }else {
           return users.size();
       }
    }

    @Transactional
    public void deleteByEmail(String email){
        Objects.requireNonNull(email, "Email should not be null!");
        userRepository.deleteByEmail(email);

    }
    private UserDto entityToDto(User user){
        Objects.requireNonNull(user, "User should not be null!");
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getFirstName());
        userDto.setSurname(user.getLastName());
        return userDto;
    }
    private Role createNewRole(){
        Role role = new Role();
        role.setName("ADMIN");
        return roleRepository.save(role);
    }
}
