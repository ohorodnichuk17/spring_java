package org.example.mapper;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryCreateDTO(CategoryCreateDTO dto);

    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryUpdateDTO(CategoryUpdateDTO dto);

    @Mapping(target = "dataCreated", source = "creationTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    CategoryItemDTO categoryItemDTO(CategoryEntity category);
}
