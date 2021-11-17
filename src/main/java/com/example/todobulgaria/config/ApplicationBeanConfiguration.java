package com.example.todobulgaria.config;

import com.example.todobulgaria.interceptors.LastModelAndViewInterceptor;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Configuration
public class ApplicationBeanConfiguration {

    private final CloudinaryConfiguration config;

    public ApplicationBeanConfiguration(CloudinaryConfiguration config) {
        this.config = config;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        "cloud_name", config.getCloudName(),
                        "api_key", config.getApiKey(),
                        "api_secret", config.getApiSecret()
                )
        );
    }

    @Bean
    public ModelMapper modelMapper(){
    return new ModelMapper();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson (){
        return new Gson();
    }

}
