package com.mt.mtSocialMedia.dto.Post;

import com.mt.mtSocialMedia.model.Topic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PostDto {

    @NotNull
    @Size(min = 2,max=50)
    private String title;

    @NotNull
    @Size(min = 2,max=1000)
    private String content;

    private String imageUrl;

    private Long topicId;

}
