package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principle){
        String userName = principle.getName();

        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);
    }

    @RequestMapping("/index")             // When user will fire : /user/index = then only this controller will work
    public String dashboard(Model model, Principal principle){
        model.addAttribute("title", "User Dashboard");
        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model){
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal){
        try {
            String userName = principal.getName();
            User user = userRepository.getUserByUserName(userName);

            if (file.isEmpty()){
                System.out.println("Enter your image file");
            } else{
                // Save your file in img directory and the name of file to database
                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image is uploaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            //user.setContacts((List<Contact>) contact);   This dosen't work
            User result = userRepository.save(user);

//            System.out.println("User : " + result);
//            System.out.println("Contact : " + contact);
        } catch (Exception e){
            System.out.println("Error: "  + e.getMessage());
            e.printStackTrace();
        }

        return "normal/add_contact_form";
    }
}
