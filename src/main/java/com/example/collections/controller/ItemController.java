package com.example.collections.controller;

import com.example.collections.utils.ControllerUtils;
import com.example.collections.model.*;
import com.example.collections.repository.ItemRepository;
import com.example.collections.service.CollectionService;
import com.example.collections.service.CommentService;
import com.example.collections.service.ItemService;
import com.example.collections.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private ItemRepository repo;

    @PreAuthorize("hasAuthority('ADMIN') or #col.owner.username == authentication.name")
    @PostMapping("/addItem/{col}")
    public String add(@AuthenticationPrincipal User user,
                      @PathVariable Collection col,
                      @Valid Item item,
                      BindingResult bindingResult,
                      Model model
    )  {

        item.setCollection(col);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("item", item);
            model.addAttribute("col", col);
            model.addAttribute("items", collectionService.getItems(col));
            return "collection";
        } else {
            itemService.addItem(item);
            return "redirect:/collection/" + col.getId();
        }

    }

    @PreAuthorize("hasAuthority('ADMIN') or #item.collection.owner.username == authentication.name")
    @GetMapping("/deleteItem/{item}")
    public String deleteItem(@AuthenticationPrincipal User user,
                             @PathVariable Item item,
                             Model model
    ) {
        itemService.deleteItem(item);
        return "redirect:/collection/" + item.getCollection().getId();
    }

    @PreAuthorize("hasAuthority('ADMIN') or #item.collection.owner.username == authentication.name")
    @GetMapping("/editItem/{item}")
    public String showItemEditor(@PathVariable Item item, Model model) {
        model.addAttribute("currentItem", item);
        return "itemEditor";
    }

    @PreAuthorize("hasAuthority('ADMIN') or #currentItem.collection.owner.username == authentication.name")
    @PostMapping("/editItem/{currentItem}")
    public String editCollection(@PathVariable Item currentItem,
                                 @Valid Item item,
                                 BindingResult bindingResult,
                                 Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("item", item);
            return "itemEditor";
        } else {
            itemService.editItem(currentItem, item);
            return "redirect:/collection/" + currentItem.getCollection().getId();
        }

    }

    @PostMapping("/filter/{col}")
    public String filter(@PathVariable Collection col, @RequestParam String tag, Model model) {
        Iterable<Item> items;
        if (tag != null && !tag.isEmpty()) {
            items = itemService.getItemsByTagAndCollection(tag, col);
        }
        else
            items = itemService.getItems(col);
        model.addAttribute("col", col);
        model.addAttribute("items", items);
        return "collection";
    }

    @GetMapping("/filter/{col}")
    public String redirectToCollectionAfterFilterLike(@PathVariable Collection col) {
        return "redirect:/collection/" + col.getId();
    }

    @GetMapping("/showTag/{tag}")
    public String showTag(@PathVariable Tag tag, Model model) {
        model.addAttribute("items", itemService.getItemsByTag(tag));
        return "showTag";
    }

    @GetMapping("/likeItem/{item}")
    public String likeItem(@PathVariable Item item,
                           @AuthenticationPrincipal User currentUser,
                           HttpServletRequest request,
                           Model model
    ) {
        itemService.like(item, currentUser);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/{item}/comments")
    public String showComments(@PathVariable Item item, Model model) {
        model.addAttribute("item", item);
        model.addAttribute("comments", commentService.getCommentsByItem(item));
        return "comments";
    }

    @GetMapping("/collection/sortByName/{collection}/{sort}")
    public String getSorted(@PathVariable Collection collection, @PathVariable Boolean sort, Model model) {
        model.addAttribute("col", collection);
        model.addAttribute("items", itemService.getSortByName(collection, sort));
        return "collection";
    }

}
