package com.femcoders.events.dtos.user;

import com.femcoders.events.models.CustomUser;

public class CustomUserMapper {
    public static CustomUser dtoToEntity (CustomUserRequest dto){
        return new CustomUser(dto.username(),dto.password(),dto.role());
    }

    public static CustomUserResponse entityToDto (CustomUser user){
        return new CustomUserResponse(user.getUsername(),user.getRole());
    }
}
