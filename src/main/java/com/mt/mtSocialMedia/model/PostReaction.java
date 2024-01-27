package com.mt.mtSocialMedia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "post_reaction")
public class PostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
