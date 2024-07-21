package com.example.AvitoDemo.service;

import com.example.AvitoDemo.DTO.CommentDto;
import com.example.AvitoDemo.DTO.ItemDto;
import com.example.AvitoDemo.DTO.UserDto;
import com.example.AvitoDemo.model.Comment;
import com.example.AvitoDemo.model.Item;
import com.example.AvitoDemo.model.User;
import com.example.AvitoDemo.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlgoService {
    @Autowired
    private final ItemRepository itemRepository;
    public List<UserDto> doListDtoUser(List<User> user){
        List<UserDto> userDtoList = new ArrayList<>();
        for(int i = 0;i<user.size();i++){

            User userSingle = user.get(i);
            userDtoList.add(UserDto.builder().id(userSingle.getId())
                    .city(userSingle.getCity())
                    .items(userSingle.getItems().stream().map(Item::getNameItem).collect(Collectors.toList()))
                    .boughtItems(userSingle.getBoughtItems().stream().map(Item::getNameItem).collect(Collectors.toList()))
                    .role(userSingle.getRole())
                    .email(userSingle.getEmail())
                    .name(userSingle.getName())
                    .surname(userSingle.getSurname())

                    .build());
        }
        return userDtoList;
    }
    @Transactional
    public List<ItemDto> doListDtoItem(List<Item> item){
        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i = 0;i<item.size();i++){
            Item itemSingle = item.get(i);
            itemDtoList.add(ItemDto.builder().id(itemSingle.getId())
                    .city(itemSingle.getCity())

                    .userBoughted(itemSingle.getUserBoughted().stream().map(User::getName).collect(Collectors.toList()))
                    .price(itemSingle.getPrice())
                    .photo(itemSingle.getPhoto())
                    .likes(itemSingle.getLikes())
                    .star(itemSingle.getStar())
                            .views(itemSingle.getViews())
                            .nameItem(itemSingle.getNameItem())
                            .owner(itemSingle.getOwner().getName())

                    .build());
        }
        return itemDtoList;
    }

    public ItemDto doDtoItem(Item item){
        Item itemSingle = item;
        if(itemSingle.getComment() != null ||itemSingle.getUserBoughted()!=null){
            ItemDto itemDto = ItemDto.builder()
                    .id(itemSingle.getId())
                    .city(itemSingle.getCity())
                    .comment(doListCommentDto(itemSingle.getComment()))
                    .userBoughted(itemSingle.getUserBoughted().stream().map(User::getName).collect(Collectors.toList()))
                    .price(itemSingle.getPrice())
                    .photo(itemSingle.getPhoto())
                    .likes(itemSingle.getLikes())
                    .star(itemSingle.getStar())
                    .views(itemSingle.getViews())
                    .nameItem(itemSingle.getNameItem())
                    .owner(itemSingle.getOwner().getName())
                    .build();
            return itemDto;

        }else{
            ItemDto itemDto = ItemDto.builder()
                    .id(itemSingle.getId())
                    .city(itemSingle.getCity())
                    .price(itemSingle.getPrice())
                    .photo(itemSingle.getPhoto())
                    .likes(itemSingle.getLikes())
                    .star(itemSingle.getStar())
                    .views(itemSingle.getViews())
                    .nameItem(itemSingle.getNameItem())
                    .owner(itemSingle.getOwner().getName())
                    .build();
            return itemDto;

        }



    }
    public UserDto doDtoUser(User user){
        User userSingle = user;
        UserDto userDto = UserDto.builder().id(userSingle.getId())
                .city(userSingle.getCity())
                .items(userSingle.getItems().stream().map(Item::getNameItem).collect(Collectors.toList()))
                .boughtItems(userSingle.getBoughtItems().stream().map(Item::getNameItem).collect(Collectors.toList()))
                .role(userSingle.getRole())
                .email(userSingle.getEmail())
                .name(userSingle.getName())
                .surname(userSingle.getSurname())

                .build();

        return userDto;

    }
    public CommentDto doDtoComment(Comment comment){
        Comment commentSingle = comment;
        CommentDto commentDto = CommentDto.builder()
                .id(commentSingle.getId_comment())
                .from(commentSingle.getSender())
                .ownerComment(comment.getOwnerComment().getNameItem())
                .message(comment.getMessage())
                .build();
        return commentDto;
    }
    public List<CommentDto> doListCommentDto(List<Comment> comments){
        List<CommentDto> commentDtos = new ArrayList<>();
        for(int i = 0;i<comments.size();i++){
            Comment commentSingle = comments.get(i);
            commentDtos.add(CommentDto.builder()
                    .id(commentSingle.getId_comment())
                    .from(commentSingle.getSender())
                    .ownerComment(commentSingle.getOwnerComment().getNameItem())
                    .message(commentSingle.getMessage())
                    .build());


        }
        return commentDtos;

    }


    public boolean check(String email, Long id) {

        System.out.println(1);
        Item item = itemRepository.findById(id).orElse(null);
        return item != null && item.getOwner().getEmail().equals(email);
    }





}
