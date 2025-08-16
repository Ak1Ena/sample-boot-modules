package th.mfu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import th.mfu.Product;
import th.mfu.dto.ProductDTO;

@Mapper(componentModel = "spring",uses = { ProductReviewMapper.class })
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
    
    @Mapping(target = "reviews", ignore = true)
    ProductDTO toDto(Product entity);
    
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "saleOrderItems", ignore = true)
    Product toEntity(ProductDTO dto);

    List<ProductDTO> toDtoList(List<Product> entities);

}
