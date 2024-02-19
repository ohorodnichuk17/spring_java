package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() {
        List<CategoryEntity> list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO dto) {
       try {
           CategoryEntity entity = categoryMapper.categoryCreateDTO(dto);
           String image = storageService.saveImage(dto.getImage(), FileSaveFormat.JPG);
           entity.setImage(image);
           categoryRepository.save(entity);
           return new ResponseEntity<>(categoryMapper.categoryItemDTO(entity), HttpStatus.CREATED);

       } catch (Exception ex) {
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryItemDTO> update(@PathVariable Integer id, @ModelAttribute CategoryUpdateDTO dto) {
        try {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
            if (!optionalCategory.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            CategoryEntity entity = optionalCategory.get();
            if (dto.getName() != null) {
                entity.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                entity.setDescription(dto.getDescription());
            }
            if (dto.getImage() != null) {
                String image = storageService.saveImage(dto.getImage(), FileSaveFormat.JPG);
                entity.setImage(image);
            }
            categoryRepository.save(entity);
            return ResponseEntity.ok(categoryMapper.categoryItemDTO(entity));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
            if (!optionalCategory.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
