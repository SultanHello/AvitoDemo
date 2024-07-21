package com.example.AvitoDemo.DTO;

import com.example.AvitoDemo.model.Item;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String from;
    private String message;
    private String ownerComment;
}
