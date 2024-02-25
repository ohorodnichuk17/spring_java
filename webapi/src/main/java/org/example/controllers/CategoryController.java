package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @PutMapping("/{id}")
//    public ResponseEntity<CategoryItemDTO> update(@PathVariable Integer id, @ModelAttribute CategoryEditDTO dto) {
//        try {
//            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
//            if (!optionalCategory.isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//            CategoryEntity entity = optionalCategory.get();
//            if (dto.getName() != null) {
//                entity.setName(dto.getName());
//            }
//            if (dto.getDescription() != null) {
//                entity.setDescription(dto.getDescription());
//            }
//            if (dto.getFile() != null) {
//                String image = storageService.saveImage(dto.getFile(), FileSaveFormat.JPG);
//                entity.setImage(image);
//            }
//            categoryRepository.save(entity);
//            return ResponseEntity.ok(categoryMapper.categoryItemDTO(entity));
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Integer id) {
//        try {
//            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
//            if (!optionalCategory.isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//            categoryRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryItemDTO> getById(@PathVariable int categoryId) {
        var entity = categoryRepository.findById(categoryId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var result =  categoryMapper.categoryItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> edit(@ModelAttribute CategoryEditDTO model) {
        var old = categoryRepository.findById(model.getId()).orElse(null);
        if (old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var entity = categoryMapper.categoryEditDto(model);
        if(model.getFile()==null) {
            entity.setImage(old.getImage());
        }
        else {
            try {
                storageService.deleteImage(old.getImage());
                String fileName = storageService.saveImage(model.getFile(), FileSaveFormat.JPG);
                entity.setImage(fileName);
            }
            catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        entity.setCreationTime(old.getCreationTime());
        categoryRepository.save(entity);
        var result = categoryMapper.categoryItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Method to delete a category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable int categoryId) {
        var entity = categoryRepository.findById(categoryId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            storageService.deleteImage(entity.getImage());
            categoryRepository.deleteById(categoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryItemDTO>> searchByName(@RequestParam(required = false) String name,
                                                              Pageable pageable) {
        Page<CategoryEntity> categories = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        Page<CategoryItemDTO> result = categories.map(categoryMapper::categoryItemDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
