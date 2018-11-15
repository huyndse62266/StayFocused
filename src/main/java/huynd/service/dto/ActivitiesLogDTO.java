package huynd.service.dto;

import java.sql.Date;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActivitiesLog entity.
 */
public class ActivitiesLogDTO implements Serializable {

    private Long activitiesLogID;

    private Date activitiesLogDate;

    private Long activitiesLogPointReceived;

    private Long activitiesLogAchievedTime;

    private UserDTO userDTO;


    public Long getActivitiesLogID() {
        return activitiesLogID;
    }

    public void setActivitiesLogID(Long activitiesLogID) {
        this.activitiesLogID = activitiesLogID;
    }

    public Date getActivitiesLogDate() {
        return activitiesLogDate;
    }

    public void setActivitiesLogDate(Date activitiesLogDate) {
        this.activitiesLogDate = activitiesLogDate;
    }

    public Long getActivitiesLogPointReceived() {
        return activitiesLogPointReceived;
    }

    public void setActivitiesLogPointReceived(Long activitiesLogPointReceived) {
        this.activitiesLogPointReceived = activitiesLogPointReceived;
    }

    public Long getActivitiesLogAchievedTime() {
        return activitiesLogAchievedTime;
    }

    public void setActivitiesLogAchievedTime(Long activitiesLogAchievedTime) {
        this.activitiesLogAchievedTime = activitiesLogAchievedTime;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivitiesLogDTO activitiesLogDTO = (ActivitiesLogDTO) o;
        if (activitiesLogDTO.getActivitiesLogID() == null || getActivitiesLogID() == null) {
            return false;
        }
        return Objects.equals(getActivitiesLogID(), activitiesLogDTO.getActivitiesLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getActivitiesLogID());
    }

    @Override
    public String toString() {
        return "ActivitiesLogDTO{" +
            "activitiesLogID=" + activitiesLogID +
            ", activitiesLogDate=" + activitiesLogDate +
            ", activitiesLogPointReceived=" + activitiesLogPointReceived +
            ", activitiesLogAchievedTime=" + activitiesLogAchievedTime +
            ", userDTO=" + userDTO +
            '}';
    }
}
