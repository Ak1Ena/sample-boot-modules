package th.mfu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import th.mfu.SaleOrderItem;
import th.mfu.dto.SaleOrderItemsDTO;

@Mapper(componentModel = "spring",uses = { SaleOrderMapper.class , ProductMapper.class })
public interface SaleOrderItemMapper {
    SaleOrderItemMapper INSTANCE = Mappers.getMapper( SaleOrderItemMapper.class );
    
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "saleOrder", ignore = true)
    SaleOrderItemsDTO toDto(SaleOrderItem entity);
    
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "saleOrder", ignore = true)
    SaleOrderItem toEntity(SaleOrderItemsDTO dto);

    List<SaleOrderItemsDTO> toDtoList(List<SaleOrderItem> entities);
}
