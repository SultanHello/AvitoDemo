package com.example.AvitoDemo.DTO;

import com.example.AvitoDemo.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String nameItem;
    private int price;
    private String photo;
    private int likes;
    private int star;
    private int views;
    private String city;
    private String owner;
    private List<String> userBoughted=new ArrayList<>();
    private List<CommentDto> comment;
}
