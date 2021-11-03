package com.example.todobulgaria.init;

import com.example.todobulgaria.services.CategoryEntityService;
import com.example.todobulgaria.services.RoleEntityService;
import com.example.todobulgaria.services.UserEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppDataInitializer implements CommandLineRunner {

    private final CategoryEntityService categoryEntityService;
    private final RoleEntityService roleEntityService;
    private final UserEntityService userEntityService;

    public AppDataInitializer(CategoryEntityService categoryEntityService, RoleEntityService roleEntityService, UserEntityService userEntityService) {
        this.categoryEntityService = categoryEntityService;
        this.roleEntityService = roleEntityService;
        this.userEntityService = userEntityService;
    }

    @Override
    public void run(String... args) throws Exception {

        categoryEntityService.initCategories();
        roleEntityService.initRoles();
        userEntityService.initUsers();
    }
}
