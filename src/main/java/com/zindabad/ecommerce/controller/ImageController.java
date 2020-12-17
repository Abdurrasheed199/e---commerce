package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.service.ImageServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
public class ImageController {

    final ImageServiceImpl imageServiceImpl;

    public ImageController(ImageServiceImpl imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }


    //Add more Images to a product.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/images/add", method = RequestMethod.POST)
    public String addImages(@RequestParam long productId, @RequestParam("files") MultipartFile[] files){
//        Arrays.asList(files).stream().forEach(file -> imageServiceImpl.createImage(productId, file));
        for(int i = 0; i < files.length; i++){
            imageServiceImpl.createImage(productId, files[i]);
        }

//        for(MultipartFile f:files){
//            imageServiceImpl.createImage(productId, f);
//        }

        return "Creation Successful";
    }

    //Get all images of a product.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/images/product/{id}", method = RequestMethod.GET)
    public List getImages(@PathVariable("id") long productId){
        List<String> images = imageServiceImpl.getProductImages(productId);

        return images;
    }

}
