package com.mt.mtSocialMedia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "comment_reaction")
public class CommentReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
