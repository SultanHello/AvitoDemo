package com.example.AvitoDemo.repository;

import com.example.AvitoDemo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
