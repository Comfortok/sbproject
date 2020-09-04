package kz.ok.training.controller;

import kz.ok.training.model.Message;
import kz.ok.training.model.User;
import kz.ok.training.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("main")
    public String main(@AuthenticationPrincipal User author,
                       @RequestParam(required = false, defaultValue = "") String filter,
                       Model model) {
        Iterable<Message> messageList = messageRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            messageList = messageRepository.findByTag(filter);
        }

        model.addAttribute("messages", messageList);
        model.addAttribute("filter", filter);
        model.addAttribute("username", author.getUsername());
        return "main";
    }

    @PostMapping("addMessage")
    public String add(@Valid Message message,
                      BindingResult bindingResult,
                      Model model,
                      @AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
        } else {

            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFileName));

                message.setFilename(resultFileName);
            }

            model.addAttribute("message", null);

            messageRepository.save(message);
        }
        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("message", message);
        return "main";
    }
}