package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ListStore and its DTO StoreDTO.
 */
@Mapper(componentModel = "spring", uses = {StoreGroupMapper.class})
public interface StoreMapper {

    @Mapping(source = "store.storeGroup.storeGroupID", target = "storeGroup.storeGroupID")
    StoreDTO toDto(Store store);

    @Mapping(source = "storeID", target = "storeGroup")
    default Store toEntity(StoreDTO storeDTO){
        if(storeDTO == null){
            return null;
        }else{
            Store store = new Store();
            if(storeDTO.getStoreID() != null){
                store.setStoreID(storeDTO.getStoreID());
            }
            store.setStoreName(storeDTO.getStoreName());
            store.setStoreAddress(storeDTO.getStoreAddress());
            if(storeDTO.getStoreGroup().getStoreGroupID() != null){
                StoreGroup storeGroup = new StoreGroup();
                storeGroup.setStoreGroupID(storeDTO.getStoreGroup().getStoreGroupID());
                store.setStoreGroup(storeGroup);
            }
            store.setStoreLatitude(storeDTO.getStoreLatitude());
            store.setStoreLongitude(storeDTO.getStoreLongitude());
            return store;
        }
    }

    default Store fromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setStoreID(id);
        return store;
    }


//    default ListStore mapping(StoreDTO listStoreDTO){
//        if(listStoreDTO == null){
//            return null;
//        }else{
//            ListStore listStore = new ListStore();
//            listStore.setListStoreID(listStoreDTO.getListStoreID());
//            listStore.setListStoreName(listStoreDTO.getListStoreName());
//            listStore.setListStoreAddress(listStoreDTO.getListStoreAddress());
//            if(listStoreDTO != null){
//                StoreGroup store = new StoreGroup();
//                store.setStoreID(listStoreDTO.getStoreID());
//                listStore.setStoreGroup(store);
//            }
//            return listStore;
//        }
//    }
}
