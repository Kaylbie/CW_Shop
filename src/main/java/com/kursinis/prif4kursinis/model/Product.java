package com.kursinis.prif4kursinis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String code;
    String price;
    String description;
    String photoName;

    String manufacturer;
    boolean isVisible;
    @ManyToOne
    Warehouse warehouse;

    @ManyToOne
    Cart cart;

    public Product(String title, String code, String price, String description, String photoName, String manufacturer, boolean isVisible) {
        this.title = title;
        this.code=code;
        this.price=price;
        this.description = description;
        this.photoName = photoName;
        this.manufacturer = manufacturer;
        this.isVisible = isVisible;
    }
}
