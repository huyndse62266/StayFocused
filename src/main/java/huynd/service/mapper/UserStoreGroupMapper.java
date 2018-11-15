package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.UserStoreGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserStoreGroup and its DTO UserStoreGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserStoreGroupMapper extends EntityMapper<UserStoreGroupDTO, UserStoreGroup> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "storeGroup.storeGroupID", target = "storeGroupID")
    UserStoreGroupDTO toDto(UserStoreGroup userStoreGroup);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "storeGroupID", target = "storeGroup")

    default UserStoreGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserStoreGroup userStoreGroup = new UserStoreGroup();
        userStoreGroup.getUser().setId(id);
        return userStoreGroup;
    }
}
