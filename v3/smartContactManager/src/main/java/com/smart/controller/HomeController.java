package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home- Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About- Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register- Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String register(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false")boolean agreement, Model model, HttpSession session){
        try{
            if(!agreement){
                System.out.println("Please Agree Terms and Conditions");
                throw new Exception("Please Agree Terms and Conditions");
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImage("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User result = userRepository.save(user);
            model.addAttribute("user", new User());

            System.out.println(agreement);
            System.out.println(result);
            session.setAttribute("message", new Message("Successfully Signed Up", "alert-success"));

            return "signup";

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something Went wrong!!! : " + e.getMessage(), "alert-danger"));
            return "signup";
        }

    }
}
