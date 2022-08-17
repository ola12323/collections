package com.example.collections.controller;

import com.example.collections.utils.ControllerUtils;
import com.example.collections.model.Collection;
import com.example.collections.utils.FileUploadUtil;
import com.example.collections.model.User;
import com.example.collections.repository.CollectionsRepository;
import com.example.collections.repository.UserRepository;
import com.example.collections.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/personalPage/{user}")
    public String personPage(@PathVariable User user, Model model) {
        model.addAttribute("collections", collectionService.getCollections(user));
        model.addAttribute("owner", user);
        return "personalPage";
    }

    @Autowired
    private CollectionsRepository repos;
    @PreAuthorize("hasAuthority('ADMIN') or #user.username == authentication.name")
    @PostMapping("/personalPage/{user}")
    public String putCollection(@PathVariable User user,
                                @Valid Collection collection,
                                BindingResult bindingResult,
                                Model model,@RequestParam("image") MultipartFile multipartFile
    ) throws IOException {
        collection.setOwner(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("collection", collection);
        } else {

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            collection.setPhotos(fileName);
            //repos.setPhoto(fileName,collection.getId());
            Collection savedUser = repos.save(collection);

            String uploadDir = "user-photos/" + savedUser.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            collectionService.saveCollection(collection);
        }
        model.addAttribute("collections", collectionService.getCollections(user));
        model.addAttribute("owner", user);
        model.addAttribute("collection", null);
        return "personalPage";
    }

    @Autowired
    private UserRepository repo;


    @PostMapping("/personalPage/save/{user}")
    public RedirectView saveUser(@PathVariable User user,
                                 @RequestParam("image") MultipartFile multipartFile) throws IOException {

        Optional<User> memberFromDb = repo.findById(user.getId());
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        repo.setPhoto(user.getUsername(), fileName);
        User savedUser = repo.save(user);

        String uploadDir = "user-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/personalPage/{user}", true);
    }

}
