package huynd.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserStoreGroup entity.
 */
public class UserStoreGroupDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long storeGroupID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoreGroupID() {
        return storeGroupID;
    }

    public void setStoreGroupID(Long storeGroupID) {
        this.storeGroupID = storeGroupID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStoreGroupDTO that = (UserStoreGroupDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserStoreGroupDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", storeGroupID=" + storeGroupID +
            '}';
    }
}
