package com.femcoders.events.services;

import com.femcoders.events.dtos.user.CustomUserMapper;
import com.femcoders.events.dtos.user.CustomUserRequest;
import com.femcoders.events.dtos.user.CustomUserResponse;
import com.femcoders.events.exception.UsernameNotFoundException;
import com.femcoders.events.models.CustomUser;
import com.femcoders.events.repositories.CustomUserRepository;
import com.femcoders.events.security.CustomUserDetail;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
    private final CustomUserRepository customUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserService(CustomUserRepository customUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
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
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        CustomUser savedUser = customUserRepository.save(newUser);
        return CustomUserMapper.entityToDto(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserRepository.findByUsername(username)
                .map(user-> new CustomUserDetail(user))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
