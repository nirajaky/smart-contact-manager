package com.smart.controller;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

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
                contact.setImage("default.png");
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
    // per page= 5 contact
    //current page = 0
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal){
        m.addAttribute("title", "Show User Contacts");

        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);

        // For Pagernation : of(current page, reconrd per page)
        Pageable pageable = PageRequest.of(page, 3);
        // Returns user with required pagable
        Page<Contact> contacts = contactRepository.findContactByUserId(user.getId(), pageable);

        m.addAttribute("contacts", contacts);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", contacts.getTotalPages());
        System.out.println(contacts);
        return "normal/show_contacts";
    }

    // Showing specific Contact
    @GetMapping("/{cId}/contact")
    public String showContactDetails(@PathVariable("cId") Integer cId, Model model){
        Optional<Contact> contactOptional = contactRepository.findById(cId);
        Contact contact = contactOptional.get();

        model.addAttribute("contact", contact);
        model.addAttribute("title", "Show Contact");
        return "normal/contact_detail";
    }
}
