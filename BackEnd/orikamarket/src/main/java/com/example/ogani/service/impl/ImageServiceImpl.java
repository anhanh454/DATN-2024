package com.example.ogani.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ogani.entity.Image;
import com.example.ogani.exception.NotFoundException;
import com.example.ogani.repository.ImageRepository;
import com.example.ogani.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getListImage() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImageById(long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image not found width id :" + id));

        return image;
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getListByUser(long userId) {
        List<Image> images = imageRepository.getListImageOfUser(userId);
        return images;
    }

    @Override
    public void deleteImage(long id) {
        
    }
}
