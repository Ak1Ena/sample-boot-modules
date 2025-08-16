package th.mfu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import th.mfu.SaleOrder;
import th.mfu.dto.SaleOrderDTO;

@Mapper(componentModel = "spring", uses = {SaleOrderItemMapper.class, CustomerMapper.class})
public interface SaleOrderMapper {
    
    SaleOrderMapper INSTANCE = Mappers.getMapper(SaleOrderMapper.class);
    

    SaleOrderDTO toDto(SaleOrder saleOrder);
    
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "items", ignore = true)
    SaleOrder toEntity(SaleOrderDTO dto);
    
    List<SaleOrderDTO> toDtoList(List<SaleOrder> saleOrders);
}
