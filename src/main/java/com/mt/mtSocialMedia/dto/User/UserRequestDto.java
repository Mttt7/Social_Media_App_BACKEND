package com.mt.mtSocialMedia.dto.User;

import com.mt.mtSocialMedia.model.Role;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String about;
    private String phone;
    private String photoUrl;
    private String backgroundUrl;
}
