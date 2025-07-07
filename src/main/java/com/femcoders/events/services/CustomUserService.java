package com.femcoders.events.services;

import com.femcoders.events.dtos.user.CustomUserMapper;
import com.femcoders.events.dtos.user.CustomUserRequest;
import com.femcoders.events.dtos.user.CustomUserResponse;
import com.femcoders.events.models.CustomUser;
import com.femcoders.events.repositories.CustomUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService {
    private final CustomUserRepository customUserRepository;

    public CustomUserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    public List<CustomUserResponse> getUsers() {
        List<CustomUser> users = customUserRepository.findAll();
        return users.stream().map(user -> CustomUserMapper.entityToDto(user)).toList();
    }

    public CustomUserResponse getUserById(Long id) {
        CustomUser user = customUserRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(CustomUser.class.getSimpleName(), id));
        return CustomUserMapper.entityToDto(user);
    }

    public CustomUserResponse addUser(CustomUserRequest userRequest){
        CustomUser newUser = CustomUserMapper.dtoToEntity(userRequest);
        CustomUser savedUser = customUserRepository.save(newUser);
        return CustomUserMapper.entityToDto(savedUser);
    }
}
