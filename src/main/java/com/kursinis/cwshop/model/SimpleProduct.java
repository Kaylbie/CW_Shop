package com.kursinis.cwshop.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SimpleProduct extends Product {
    public SimpleProduct(String title, String code,double price, String description, String photoName) {
        super(title, code, price, description, photoName, "NO", true);
    }
}
