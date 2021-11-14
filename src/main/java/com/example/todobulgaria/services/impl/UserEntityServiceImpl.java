package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.PictureEntity;
import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.security.UserDetailsImpl;
import com.example.todobulgaria.services.CloudinaryImage;
import com.example.todobulgaria.services.CloudinaryService;
import com.example.todobulgaria.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserDetailsImpl userDetailsImpl;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public UserEntityServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper, UserDetailsImpl userDetailsImpl, CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userDetailsImpl = userDetailsImpl;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void initUsers() {

        if (userRepository.count() > 0) {
            return;
        }

        RoleEntity adminRole = roleRepository.findByRole(RoleEnum.ADMIN).orElse(null);
        RoleEntity userRole = roleRepository.findByRole(RoleEnum.USER).orElse(null);

        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setEmail("admin@abv.bg");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setStatus(true);

        admin.setRoles(List.of(adminRole, userRole));
        userRepository.save(admin);

        UserEntity user = new UserEntity();
        user.setUsername("pesho");
        user.setFirstName("Petyr");
        user.setLastName("Petrov");
        user.setEmail("peshko@mail.bg");
        user.setPassword(passwordEncoder.encode("12345"));
        user.setStatus(true);

        user.setRoles(List.of(userRole));
        userRepository.save(user);
    }

    @Override
    public void registrarUser(UserRegisterServiceModel userRegisterServiceModel) throws IOException {

        if (existByUsername(userRegisterServiceModel.getUsername())) {
            throw new UsernameNotFoundException("There is an account with that email address: "
                    + userRegisterServiceModel.getUsername());
        }

        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));
        user.setRoles(List.of(roleRepository.findByRole(RoleEnum.USER).orElseThrow()));

        var picture = createPictureEntity(userRegisterServiceModel.getProfilePictureUrl());

        pictureRepository.saveAndFlush(picture);

        user.setProfilePictureUrl(picture);

        userRepository.save(user);

        UserDetails principal = userDetailsImpl.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);

    }

    private PictureEntity createPictureEntity(MultipartFile file) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(file);

        PictureEntity picture = new PictureEntity();

        picture.setPublicId(uploaded.getPublicId());
        picture.setTitle(file.getName());
        picture.setUrl(uploaded.getUrl());

        return picture;
    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);

    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {

        return userRepository.findUserEntityByUsername(username);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {

        return userRepository.findUserEntityByEmail(email);
    }
}
