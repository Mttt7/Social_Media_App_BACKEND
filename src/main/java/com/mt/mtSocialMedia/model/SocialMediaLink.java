package com.mt.mtSocialMedia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "social_media_link")
public class SocialMediaLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "social_media_name")
    private String name;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
