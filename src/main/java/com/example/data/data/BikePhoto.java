package com.example.data.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bike_photos")
public class BikePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @Column(name = "photo_url")
    private String photoUrl;

    public BikePhoto() {}
}