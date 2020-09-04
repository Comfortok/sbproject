package kz.ok.training.controller;

import freemarker.template.utility.StringUtil;
import kz.ok.training.model.User;
import kz.ok.training.model.dto.CaptchaResponseDto;
import kz.ok.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private UserService userService;
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("registration")
    public String registration(
            @RequestParam("passwordConfirm") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto responseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!responseDto.isSuccess()) {
            model.addAttribute("captchaError", "Please, fill the captcha");
        }

        boolean isConfirmPasswordEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmPasswordEmpty) {
            model.addAttribute("passwordConfirmError", "Password confirmation error");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Password confirmation error. Check your password");
        }

        if (isConfirmPasswordEmpty || bindingResult.hasErrors() || !responseDto.isSuccess()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }

        if (!userService.addUserToDb(user)) {
            model.addAttribute("usernameError", "Such username is already exists.");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated.");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "No such activation code.");
        }
        return "login";
    }
}