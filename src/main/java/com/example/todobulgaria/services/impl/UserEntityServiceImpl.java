package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.UserDuplicationException;
import com.example.todobulgaria.models.entities.PictureEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;
import com.example.todobulgaria.models.views.TripCategoryTownDurationViewModel;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.security.UserDetailsImpl;
import com.example.todobulgaria.services.CloudinaryImage;
import com.example.todobulgaria.services.CloudinaryService;
import com.example.todobulgaria.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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
    public UserEntity registrarUser(UserRegisterServiceModel userRegisterServiceModel) throws IOException {

        if (existByUsername(userRegisterServiceModel.getUsername())) {
            throw new UserDuplicationException(userRegisterServiceModel.getUsername());
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

        return user;
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
    public void updateUser(UserEntity userByUsername) {
        userRepository.save(userByUsername);
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {

        return userRepository.findUserEntityByUsername(username);
    }

    @Override
    public Set<TripCategoryTownDurationViewModel> getTripCategoryTownDurationViewModels(UserEntity userEntity) {
        return userEntity.getFavouriteTrips()
                .stream().map(t -> {

                    TripCategoryTownDurationViewModel map = modelMapper.map(t, TripCategoryTownDurationViewModel.class);
                    map.setStartPoint(t.getStartPoint());

                    return map;

                })
                .collect(Collectors.toSet());
    }
}

