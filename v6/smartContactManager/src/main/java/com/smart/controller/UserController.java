package com.smart.controller;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
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

    @Autowired
    private ContactRepository contactRepository;

    // This will be added to all handler, So, all handler will get user object
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
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam("profileImage") MultipartFile file,
                                 Principal principal, Model model, HttpSession session){
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

            // Send ALERT message to template
            session.setAttribute("message", new Message("Contact Added Successfully", "alert-success"));
        } catch (Exception e){
            System.out.println("Error: "  + e.getMessage());
            e.printStackTrace();

            // Send ALERT message to template in case of any ERROR
            session.setAttribute("message", new Message("Something Went wrong!!! : " + e.getMessage(), "alert-danger"));
        }
        return "normal/add_contact_form";
    }

    // handler for show contact
    @GetMapping("/show-contacts")
    public String showContact(Model m, Principal principal){
        m.addAttribute("title", "Show User Contacts");

        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);

        List<Contact> contacts = contactRepository.findContactByUserId(user.getId());
        m.addAttribute("contacts", contacts);

        System.out.println(contacts);

        return "normal/show_contacts";
    }
}
