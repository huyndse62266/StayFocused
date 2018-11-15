package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.StoreDTO;
import huynd.service.dto.StoreGroupDTO;

import huynd.service.dto.StoreTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity StoreGroup and its DTO StoreGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreGroupMapper extends EntityMapper<StoreGroupDTO, StoreGroup> {

    @Mapping(source = "storeGroup.storeType.storeTypeID", target = "storeType.storeTypeID")
    StoreGroupDTO toDto(StoreGroup storeGroup);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "stores", ignore = true)
    default StoreGroup toEntity(StoreGroupDTO storeGroupDTO){
        if(storeGroupDTO == null){
            return  null;
        }else{
            StoreGroup storeGroup = new StoreGroup();
            storeGroup.setStoreGroupID(storeGroupDTO.getStoreGroupID());
            storeGroup.setStoreGroupName(storeGroupDTO.getStoreGroupName());
            storeGroup.setStoreGroupPhone(storeGroupDTO.getStoreGroupPhone());
            storeGroup.setStoreGroupMail(storeGroupDTO.getStoreGroupMail());
            storeGroup.setStoreGroupWeb(storeGroupDTO.getStoreGroupWeb());
            storeGroup.setStoreGroupDescription(storeGroupDTO.getStoreGroupDescription());
            storeGroup.setStoreGroupLogo(storeGroupDTO.getStoreGroupLogo());
            if(storeGroupDTO.getStoreType().getStoreTypeID() != null){
                StoreType storeType = new StoreType();
                storeType.setStoreTypeID(storeGroupDTO.getStoreType().getStoreTypeID());
                storeGroup.setStoreType(storeType);
            }
            return storeGroup;
        }
    }

    default StoreGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreGroup storeGroup = new StoreGroup();
        System.out.println(id);
        storeGroup.setStoreGroupID(id);
        return storeGroup;
    }
}
