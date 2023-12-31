package com.example.data.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bikes")
public class Bike {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "name_en")
        private String nameEn;

        @Column(name = "name_pl")
        private String namePl;

        @Column(name = "name_ua")
        private String nameUa;

        @Column(name = "name_ru")
        private String nameRu;

        @Column(name = "comment_en")
        private String commentEn;

        @Column(name = "comment_pl")
        private String commentPl;

        @Column(name = "comment_ua")
        private String commentUa;

        @Column(name = "comment_ru")
        private String commentRu;

        @Column(name = "rental")
        private int rental;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "bike_id")
        private List<BikePhoto> photos;

        public Bike() {
        }

        @JsonIgnore
        public boolean isBadBikeData() {
                return nameEn == null || namePl == null || nameUa == null || nameRu == null ||
                        commentEn == null || commentPl == null || commentUa == null || commentRu == null || rental == 0 || photos == null;
        }

        @Override
        @JsonIgnore
        public String toString() {
                return "Bike{" +
                        "id=" + id +
                        ", nameEn='" + nameEn + '\'' +
                        ", namePl='" + namePl + '\'' +
                        ", nameUa='" + nameUa + '\'' +
                        ", nameRu='" + nameRu + '\'' +
                        ", commentEn='" + commentEn + '\'' +
                        ", commentPl='" + commentPl + '\'' +
                        ", commentUa='" + commentUa + '\'' +
                        ", commentRu='" + commentRu + '\'' +
                        ", rental=" + rental +
                        ", photos=" + photos +
                        '}';
        }
}
