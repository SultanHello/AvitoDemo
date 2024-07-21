package com.example.AvitoDemo.DTO;

import com.example.AvitoDemo.model.Item;
import com.example.AvitoDemo.model.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
 @Data
 @Builder
 @AllArgsConstructor
 @NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String city;
    private Role role;

    private List<String> items=new ArrayList<>();

    private List<String> boughtItems=new ArrayList<>();
}
