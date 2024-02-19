package org.example.dto.category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryUpdateDTO {
    private int id;
    private String name;
    private MultipartFile image;
    private String description;
}
