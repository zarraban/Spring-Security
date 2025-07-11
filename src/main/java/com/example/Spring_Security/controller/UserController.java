package com.example.Spring_Security.controller;

import com.example.Spring_Security.dto.UserDto;
import com.example.Spring_Security.entity.User;
import com.example.Spring_Security.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@EnableMethodSecurity
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(
            @Qualifier("userService") UserService userService
    ){
        this.userService = userService;
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String usersForm(Model model){
        List<UserDto> users = userService.findAll();
        model.addAttribute("title", "Users Page");
        model.addAttribute("fragmentName", "users");
        model.addAttribute("users", users);
        return "layout";
    }

    @GetMapping("/index")
    public String homePage(Model model){
        model.addAttribute("title", "Home page");
        model.addAttribute("fragmentName", "index");
        model.addAttribute("number", formatNumberOfUsers(userService.countAll()));
        return "layout";
    }


    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title", "Login page");
        model.addAttribute("fragmentName", "login");
        return "layout";

    }

    @GetMapping("/register")
    public String registerPage(Model model){
        UserDto user = new UserDto();
        System.out.println("Register started");
        model.addAttribute("title", "Register page");
        model.addAttribute("fragmentName", "register");
        model.addAttribute("user", user);
        return "fragments/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if(existing!= null){
            result.rejectValue("email","email error",
                    "The email already exists" );
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Register");
            model.addAttribute("user", user);
            return "register";
        }
        userService.save(user);
        return "redirect:/register?success";

    }


    @PostMapping("/users/delete/{email}")
    public String deleteUser(@PathVariable("email") String email){
        userService.deleteByEmail(email);
        return "redirect:/users";

    }

    private String formatNumberOfUsers(int number){
        if(number == 1){
            return "is "+number + " user";
        }else {
            return "are " + number + " users";
        }
    }





}
