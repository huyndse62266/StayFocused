package huynd.service.mapper;

import huynd.domain.ActivitiesLog;
import huynd.domain.User;
import huynd.service.dto.ActivitiesLogDTO;
import huynd.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ActivitiesLog and its DTO ActivitiesLogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ActivitiesLogMapper extends EntityMapper<ActivitiesLogDTO, ActivitiesLog> {

    @Mapping(source = "activitiesLog.user.id", target = "id")
    default ActivitiesLogDTO toDto(ActivitiesLog activitiesLog) {
        if (activitiesLog == null) {
            return null;
        } else {
            ActivitiesLogDTO activitiesLogDTO = new ActivitiesLogDTO();
            activitiesLogDTO.setActivitiesLogID(activitiesLog.getActivitiesLogID());
            activitiesLogDTO.setActivitiesLogDate(activitiesLog.getActivitiesLogDate());
            activitiesLogDTO.setActivitiesLogPointReceived(activitiesLog.getActivitiesLogPointReceived());
            activitiesLogDTO.setActivitiesLogAchievedTime(activitiesLog.getActivitiesLogAchievedTime());
            if (activitiesLog.getUser().getLogin() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(activitiesLog.getUser().getId());
                userDTO.setUsername(activitiesLog.getUser().getLogin());
                userDTO.setPassword(activitiesLog.getUser().getPassword());
                userDTO.setFirstName(activitiesLog.getUser().getFirstName());
                userDTO.setLastName(activitiesLog.getUser().getLastName());
                userDTO.setEmail(activitiesLog.getUser().getEmail());
                userDTO.setPhone(activitiesLog.getUser().getPhone());
                userDTO.setDateOfBirth(activitiesLog.getUser().getDateOfBirth());
                userDTO.setLocation(activitiesLog.getUser().getLocation());
                userDTO.setImageUrl(activitiesLog.getUser().getImageUrl());
                activitiesLogDTO.setUserDTO(userDTO);
            }
            return activitiesLogDTO;
        }
    }

    @Mapping(source = "activitiesLogID", target = "user")
    default ActivitiesLog toEntity(ActivitiesLogDTO activitiesLogDTO) {
        if (activitiesLogDTO == null)
            return null;
        else {
            ActivitiesLog activitiesLog = new ActivitiesLog();
            if (activitiesLogDTO.getActivitiesLogID() != null)
                activitiesLog.setActivitiesLogID(activitiesLog.getActivitiesLogID());
            activitiesLog.setActivitiesLogID(activitiesLogDTO.getActivitiesLogID());
            activitiesLog.setActivitiesLogAchievedTime(activitiesLogDTO.getActivitiesLogAchievedTime());
            activitiesLog.setActivitiesLogDate(activitiesLogDTO.getActivitiesLogDate());
            activitiesLog.setActivitiesLogPointReceived(activitiesLogDTO.getActivitiesLogPointReceived());
            if (activitiesLogDTO.getUserDTO().getUsername() != null) {
                User user = new User();
                user.setId(activitiesLogDTO.getUserDTO().getId());
                user.setLogin(activitiesLogDTO.getUserDTO().getUsername());
                user.setPassword(activitiesLogDTO.getUserDTO().getPassword());
                if(activitiesLogDTO.getUserDTO().getFirstName() != null);
                user.setFirstName(activitiesLogDTO.getUserDTO().getFirstName());
                if(activitiesLogDTO.getUserDTO().getLastName() != null);
                user.setLastName(activitiesLogDTO.getUserDTO().getLastName());
                if(activitiesLogDTO.getUserDTO().getEmail() != null);
                    user.setEmail(activitiesLogDTO.getUserDTO().getEmail());
                if(activitiesLogDTO.getUserDTO().getPhone() != null);
                    user.setPhone(activitiesLogDTO.getUserDTO().getPhone());
                if(activitiesLogDTO.getUserDTO().getDateOfBirth() != null);
                    user.setDateOfBirth(activitiesLogDTO.getUserDTO().getDateOfBirth());
                if(activitiesLogDTO.getUserDTO().getLocation() != null);
                user.setLocation(activitiesLogDTO.getUserDTO().getLocation());
                if(activitiesLogDTO.getUserDTO().getImageUrl() != null);
                user.setImageUrl(activitiesLogDTO.getUserDTO().getImageUrl());
                activitiesLog.setUser(user);
            }
            System.out.println(activitiesLog.toString());
            return activitiesLog;
        }
    }

    default ActivitiesLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivitiesLog activitiesLog = new ActivitiesLog();
        activitiesLog.setActivitiesLogID(id);
        return activitiesLog;
    }
}
