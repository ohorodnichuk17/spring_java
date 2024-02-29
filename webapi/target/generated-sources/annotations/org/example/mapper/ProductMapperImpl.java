package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.product.ProductItemDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-29T19:40:08+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Homebrew)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductItemDTO ProductItemDTOByProduct(ProductEntity product) {
        if ( product == null ) {
            return null;
        }

        ProductItemDTO productItemDTO = new ProductItemDTO();

        productItemDTO.setCategory( productCategoryName( product ) );
        productItemDTO.setCategory_id( productCategoryId( product ) );
        productItemDTO.setId( product.getId() );
        productItemDTO.setName( product.getName() );
        productItemDTO.setPrice( product.getPrice() );
        productItemDTO.setDescription( product.getDescription() );

        return productItemDTO;
    }

    private String productCategoryName(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }
        CategoryEntity category = productEntity.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private int productCategoryId(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return 0;
        }
        CategoryEntity category = productEntity.getCategory();
        if ( category == null ) {
            return 0;
        }
        int id = category.getId();
        return id;
    }
}
