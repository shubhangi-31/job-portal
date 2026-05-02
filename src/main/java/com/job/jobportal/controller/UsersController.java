package com.job.jobportal.controller;

import com.job.jobportal.entity.Users;
import com.job.jobportal.entity.UsersType;
import com.job.jobportal.service.UsersService;
import com.job.jobportal.service.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

//@RestController
@Controller
public class UsersController {
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService,UsersService usersService){
        this.usersTypeService=usersTypeService;
        this.usersService=usersService;
    }

    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypes= usersTypeService.getAll();
        model.addAttribute("getAllTypes",usersTypes);
        model.addAttribute("user",new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String userRegistration( @Valid Users users, Model model){
        //System.out.println("User::"+users);
        Optional<Users> optionalUsers=usersService.getUserByEmail(users.getEmail());
        if(optionalUsers.isPresent()){
            model.addAttribute("error", "Email is already registered, try to login with some other email.");
            List<UsersType> usersTypes= usersTypeService.getAll();
            model.addAttribute("getAllTypes",usersTypes);
            model.addAttribute("user",new Users());
            return "register";
        }
        usersService.addNew(users);
        return "dashboard";
    }


}
