package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.security.UserDetailsImpl;
import com.example.todobulgaria.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public UserEntityServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper,  UserDetailsImpl userDetailsImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userDetailsImpl = userDetailsImpl;
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
    public void registrarUser(UserRegisterServiceModel userRegisterServiceModel) {

        if (existByUsername(userRegisterServiceModel.getUsername())) {
            throw new UsernameNotFoundException("There is an account with that email address: "
                    + userRegisterServiceModel.getUsername());
        }

        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));
        user.setRoles(List.of(roleRepository.findByRole(RoleEnum.USER).orElseThrow()));

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
