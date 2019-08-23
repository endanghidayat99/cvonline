package com.endang.cvonline.controllers;

import com.endang.cvonline.models.Company;
import com.endang.cvonline.models.Profile;
import com.endang.cvonline.models.UserCV;
import com.endang.cvonline.repositories.CompanyCVRepositories;
import com.endang.cvonline.repositories.ProfileCVRepositories;
import com.endang.cvonline.repositories.UserCVRepositories;
import com.endang.cvonline.utils.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CVOnlineController {

    @Autowired
    UserCVRepositories userRepositories;

    @Autowired
    ProfileCVRepositories profileRepositories;

    @Autowired
    CompanyCVRepositories companyCVRepositories;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("usercv",new UserCV());
        return "register";
    }

    @PostMapping("/doRegister")
    public String doRegister(@Valid @ModelAttribute UserCV usercv) {
        usercv.setPassword(EncryptedPasswordUtils.encrptPassword(usercv.getPassword()));
        usercv.setRole("ROLE_USER");
        userRepositories.save(usercv);
        return "redirect:/";
    }

    @GetMapping("/loginpage")
    public String login(Model model) {
        return "loginpage";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("profile",new Profile());
        Optional<Profile> dataProfile = profileRepositories.findById(principal.getName());
        if (dataProfile.isPresent()){
            model.addAttribute("profile",dataProfile);
        }
        model.addAttribute("username",principal.getName());
        model.addAttribute("ishidden",true);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(Model model, Principal principal, @ModelAttribute Profile profile) {
        profile.setUsername(principal.getName());
        profileRepositories.save(profile);
        model.addAttribute("username",principal.getName());
        model.addAttribute("ishidden",false);
        model.addAttribute("profile", profile);
        return "profile";
    }

    @GetMapping("/company")
    public String showCompany(Model model,Principal principal){
        model.addAttribute("companies",new ArrayList<Company>());
        List<Company> listCompany = companyCVRepositories.findCompaniesByUsername(principal.getName());
        if (listCompany!=null && listCompany.size()>0){
            model.addAttribute("companies",listCompany);
        }
        model.addAttribute("comp",new Company());
        return "company";
    }


}
