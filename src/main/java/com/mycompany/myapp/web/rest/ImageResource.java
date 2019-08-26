package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Image;
import com.mycompany.myapp.repository.ImageRepository;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Created by mstuban on 3.7.2017..
 */
@Controller
@RequestMapping("/api")
public class ImageResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "image";

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping(value = "/images/uploadImage", consumes = "multipart/form-data")
    public ResponseEntity<Image> uploadImage(@RequestBody MultipartFile image) throws IOException, URISyntaxException {

        Image imageEntity = new Image(image.getBytes());
        Image result = imageRepository.save(imageEntity);

        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
                ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}
