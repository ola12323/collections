package com.example.collections.controller;

import com.example.collections.utils.ControllerUtils;
import com.example.collections.model.Collection;
import com.example.collections.utils.FileUploadUtil;
import com.example.collections.repository.CollectionsRepository;
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

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/collection/{col}")
    public String showCollection(@PathVariable Collection col, Model model) {
        model.addAttribute("col", col);
        model.addAttribute("items", collectionService.getItems(col));
        return "collection";
    }


    @PreAuthorize("hasAuthority('ADMIN') or #col.owner.username == authentication.name")
    @GetMapping("/collectionEdit/{col}")
    public String showCollectionEditor(@PathVariable Collection col, Model model) {
        model.addAttribute("col", col);
        return "collectionEdit";
    }

    @Autowired
    private CollectionsRepository repo;
    @PreAuthorize("hasAuthority('ADMIN') or #col.owner.username == authentication.name")
    @PostMapping("/collectionEdit/{col}")
    public String editCollection(@PathVariable Collection col,
                                 @Valid Collection collection,
                                 BindingResult bindingResult,
                                 Model model,@RequestParam("image") MultipartFile multipartFile
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("collection", collection);
            return "collectionEdit";
        } else {
            Optional<Collection> memberFromDb = repo.findById(col.getId());
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            col.setPhotos(fileName);
            repo.setPhoto(fileName,col.getId());
            Collection savedUser = repo.save(col);

            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            collectionService.editCollection(col, collection);
            return "redirect:/personalPage/" + col.getOwner().getId();
        }

    }

    @PreAuthorize("hasAuthority('ADMIN') or #col.owner.username == authentication.name")
    @GetMapping("/deleteCollection/{col}")
    public String deleteCollection(@PathVariable Collection col) {
        collectionService.deleteCollection(col);
        return "redirect:/personalPage/" + col.getOwner().getId();
    }

    @GetMapping("/allCollections")
    public String showAllCollection(Model model){
        model.addAttribute("collections", collectionService.getAllCollections());
        return "allCollections";
    }
}
