package com.example.AvitoDemo.service;

import ch.qos.logback.classic.Logger;
import com.example.AvitoDemo.DTO.CommentDto;
import com.example.AvitoDemo.DTO.ItemDto;
import com.example.AvitoDemo.DTO.ItemEdit;
import com.example.AvitoDemo.model.*;
import com.example.AvitoDemo.repository.CommentRepository;
import com.example.AvitoDemo.repository.ItemRepository;
import com.example.AvitoDemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.aspectj.apache.bcel.generic.RET;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class AppService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final AuthenticationManager manager;

    @Autowired
    private final CommentRepository commentRepository;

    private final AlgoService algoService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AppService.class);


    public String reg(RegisterUser userReg ){
        User user =new User();
        user.setName(userReg.getName());
        user.setSurname(userReg.getSurname());
        user.setRole(Role.USER);
        user.setEmail(userReg.getEmail());
        user.setPassword(encoder.encode(userReg.getPassword()));
        user.setCity(userReg.getCity());
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        return token;


    }

    public String auth(AuthUser user){
        manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        User user1 = userRepository.findByEmail(user.getEmail());
        String token = jwtService.generateToken(user1);


        return token;
    }
    public ItemDto addItem(AddItem additem){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        System.out.println(additem.getPrice());


        Item item = Item.builder()
                .city(user.getCity())
                .price(additem.getPrice())
                .photo(additem.getPhotoUrl())
                .nameItem(additem.getNameItem())
                .owner(user)
                .build();


        return algoService.doDtoItem(itemRepository.save(item));


    }
    public ItemDto open(Long id){
        if(!itemRepository.findById(id).isPresent()){
            return null;
        }
        Item item = itemRepository.findById(id).get();
        item.setViews(item.getViews()+1);
        itemRepository.save(item);


        return algoService.doDtoItem(itemRepository.findById(id).get());
    }
    public ItemDto doLike(Long id){
        if(!itemRepository.findById(id).isPresent()){
            return null;
        }
        Item item = itemRepository.findById(id).get();
        item.setLikes(item.getLikes()+1);
        itemRepository.save(item);
        return algoService.doDtoItem(itemRepository.findById(id).get());

    }
    @Transactional
    public ItemDto buyItem(Long id){
        User user  = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!itemRepository.findById(id).isPresent()){
            return null;
        }
        Item item = itemRepository.findById(id).get();

        if(item.getUserBoughted().stream().map(User::getId).anyMatch(idc->idc.equals(user.getId()))){
            System.out.println("error");
            return null;
        }else{
            user.getBoughtItems().add(item);
            item.getUserBoughted().add(user);
            return algoService.doDtoItem(item);

        }




    }
    public List<ItemDto> announcements(){




        User user =userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Item> listsAc = user.getItems();


        return algoService.doListDtoItem(listsAc);

    }
    @Transactional
    public List<ItemDto> myItems(){

        User user =userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Item> listIt = user.getBoughtItems();
        return algoService.doListDtoItem(listIt);
    }

    @Transactional
    public String delete(Long id){

        try {
            logger.info("Attempting to delete item with id {}", id);
            Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
            logger.info("Found item: {}", item.getNameItem());
            itemRepository.delete(item);
            logger.info("Item with id {} deleted", id);
            return "Item deleted";
        } catch (Exception e) {
            logger.error("Failed to delete item with id {}", id, e);
            return "Failed to delete item: " + e.getMessage();
        }
    }
    public ItemDto update(Long id, ItemEdit itemEdit){
        Item item = itemRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        if(itemEdit.getCity()!=null){
            item.setCity(itemEdit.getCity());
        }else if(itemEdit.getNameItem()!=null){
            item.setNameItem(itemEdit.getNameItem());
        }else if(itemEdit.getPhoto()!=null){
            item.setPhoto(itemEdit.getPhoto());
        }else if(itemEdit.getPrice()!=item.getPrice()){
            item.setPrice(itemEdit.getPrice());
        }

        return algoService.doDtoItem(itemRepository.save(item));


    }

    public CommentDto sendComment(Long id,String message){
        System.out.println(12);

        Item item = itemRepository.findById(id).orElseThrow(null);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Comment comment = Comment.builder()
                .sender(user.getName())
                .ownerComment(item)
                .message(message)
                .build();
        item.getComment().add(comment);
        commentRepository.findAll().add(comment);
        commentRepository.save(comment);
        return algoService.doDtoComment(comment);



    }





}
