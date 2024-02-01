package com.mt.mtSocialMedia.dto.User;

import com.mt.mtSocialMedia.model.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String about;
    private String phone;
    private String photoUrl;
    private String backgroundUrl;
    private Timestamp createdAt;
    private List<Role> roles;
}
