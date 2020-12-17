package com.zindabad.ecommerce.service;

import com.zindabad.ecommerce.Exception.FileStorageException;
import com.zindabad.ecommerce.model.Image;
import com.zindabad.ecommerce.model.Product;
import com.zindabad.ecommerce.repository.ImageRepository;
import com.zindabad.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ImageServiceImpl implements IImageService {

    final
    ProductRepository productRepository;
    final
    ImageRepository imageRepository;

    public ImageServiceImpl(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    private final String UPLOAD_DIR = "C:\\Users\\USER\\IdeaProjects\\ecommerce\\src\\main\\resources\\static\\uploads";

    public void createImage(long productId, MultipartFile file){
        Image image = new Image();
        Product product = productRepository.findById(productId).get();
        image.setProduct(product);

        try {
            //Generate Universally Unique Identifier, convert to string and add it to original name...
            String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + file.getOriginalFilename());
            Path imagePath = Paths.get(UPLOAD_DIR + File.separator + fileName);
            Files.copy(file.getInputStream(),imagePath, StandardCopyOption.REPLACE_EXISTING);
            image.setUrl(fileName);

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("File Not Found");
        }
        imageRepository.save(image);
    }

    public List getProductImages(long productId){
        List<String> images = new ArrayList<>();
        List<Image> imageList = imageRepository.findImagesByProductId(productId);
        for(Image image: imageList){
            images.add(image.getUrl());
        }
        return images;
    }
}
