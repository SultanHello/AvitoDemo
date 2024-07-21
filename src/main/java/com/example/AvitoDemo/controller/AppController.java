package com.example.AvitoDemo.controller;

import com.example.AvitoDemo.DTO.CommentDto;
import com.example.AvitoDemo.DTO.ItemDto;
import com.example.AvitoDemo.DTO.ItemEdit;
import com.example.AvitoDemo.model.AddItem;
import com.example.AvitoDemo.model.AuthUser;
import com.example.AvitoDemo.model.Item;
import com.example.AvitoDemo.model.RegisterUser;
import com.example.AvitoDemo.service.AppService;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AppController {
    private final AppService appService;
    @PostMapping("/register")
    public String reg(@RequestBody RegisterUser user){
        return appService.reg(user);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello from "+SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @PostMapping("/login")
    public String auth(@RequestBody AuthUser user){
        return appService.auth(user);
    }
    @PostMapping("/addItem")
    public ItemDto addItem(@RequestBody AddItem addItem){
        return appService.addItem(addItem);
    }
    @GetMapping("/open/{id}")
    public ItemDto openItem(@PathVariable Long id){
        return appService.open(id);
    }
    @GetMapping("/like/{id}")
    public ItemDto likeItem(@PathVariable Long id){
        return appService.doLike(id);
    }

    @GetMapping("/buy/{id}")
    private ItemDto buyItem(@PathVariable Long id){
        return appService.buyItem(id);
    }
    @GetMapping("/userAnnouncements")
    private List<ItemDto> myAnnouncements(){
        return appService.announcements();
    }
    @GetMapping("/userItems")
    private List<ItemDto> myItems(){
        return appService.myItems();
    }
    @PreAuthorize("@algoService.check(authentication.principal.username,#id) or hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id){
        return appService.delete(id);
    }

    @PreAuthorize("@algoService.check(authentication.principal.username,#id) or hasAuthority('ADMIN')")
    @PutMapping("/edit/{id}")
    public ItemDto editItem(@PathVariable Long id,@RequestBody ItemEdit item){
        return appService.update(id,item);
    }


    @PostMapping("/sendComment/{id}")
    public CommentDto sendComment(@PathVariable Long id,@RequestBody String message){
        return appService.sendComment(id,message);

    }











}
