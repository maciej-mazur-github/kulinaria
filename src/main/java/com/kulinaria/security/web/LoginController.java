package com.kulinaria.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/zaloguj")
    String loginForm(@RequestParam(required = false) String error,
                     @RequestParam(required = false) String logout,
                     RedirectAttributes redirectAttributes) {
        if (error != null || logout != null) {
            redirectAttributes.addFlashAttribute("error", error);
            redirectAttributes.addFlashAttribute("logout", logout);
            return "redirect:/zaloguj";
        }
        return "login-form";
    }

}
