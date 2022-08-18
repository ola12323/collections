package com.example.collections.controller;
import com.example.collections.service.CollectionService;
import com.example.collections.service.ItemService;
import com.example.collections.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ItemService itemService;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("maxSizeCollection", collectionService.getMaxSizeCollection());
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("lastAddedItems", itemService.getLastAddedItems());
        model.addAttribute("collection", collectionService.getLargestCol());
        return "index";
    }

}
