package com.example.todobulgaria.services;

import com.example.todobulgaria.services.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile file) throws IOException;

    boolean delete(String publicId);
}
