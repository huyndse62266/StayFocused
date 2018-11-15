package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.StoreTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StoreType and its DTO StoreTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreTypeMapper extends EntityMapper<StoreTypeDTO, StoreType> {

    @Mapping(target = "storeGroups", ignore = true)
    StoreType toEntity(StoreTypeDTO storeTypeDTO);

    default StoreType fromId(String id) {
        if (id == null) {
            return null;
        }
        StoreType storeType = new StoreType();
        storeType.setStoreTypeID(id);
        return storeType;
    }
}
