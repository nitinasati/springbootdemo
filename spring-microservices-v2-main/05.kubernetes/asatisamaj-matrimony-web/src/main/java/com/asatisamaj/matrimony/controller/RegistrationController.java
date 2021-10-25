package com.asatisamaj.matrimony.controller;


import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asatisamaj.matrimony.domain.UserData;
import com.asatisamaj.matrimony.exception.InvalidTokenException;
import com.asatisamaj.matrimony.exception.UserAlreadyExistException;
import com.asatisamaj.matrimony.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final String REDIRECT_LOGIN= "redirect:/login";
    private static final String REGISTER="account/register";

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String register(final Model model){
        model.addAttribute("userData", new UserData());
        return REGISTER;
    }

    @PostMapping
    public String userRegistration(final @Valid  UserData userData, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userData);
            return REGISTER;
        }
        try {
            userService.register(userData);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userData);
            return REGISTER;
        }
        model.addAttribute("registrationMsg", messageSource.getMessage("user.registration.verification.email.msg", null, LocaleContextHolder.getLocale()));
        return REGISTER;
    }

    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr){
        if(StringUtils.isEmpty(token)){
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        try {
              userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", messageSource.getMessage("user.registration.verification.success", null,LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }

}
