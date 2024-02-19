package org.example.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-18T15:57:42+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_ss_11397504320 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm:ss" );

    @Override
    public CategoryEntity categoryCreateDTO(CategoryCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName( dto.getName() );
        categoryEntity.setDescription( dto.getDescription() );

        return categoryEntity;
    }

    @Override
    public CategoryItemDTO categoryItemDTO(CategoryEntity category) {
        if ( category == null ) {
            return null;
        }

        CategoryItemDTO categoryItemDTO = new CategoryItemDTO();

        if ( category.getCreationTime() != null ) {
            categoryItemDTO.setDataCreated( dateTimeFormatter_dd_MM_yyyy_HH_mm_ss_11397504320.format( category.getCreationTime() ) );
        }
        categoryItemDTO.setId( category.getId() );
        categoryItemDTO.setName( category.getName() );
        categoryItemDTO.setImage( category.getImage() );
        categoryItemDTO.setDescription( category.getDescription() );

        return categoryItemDTO;
    }
}
