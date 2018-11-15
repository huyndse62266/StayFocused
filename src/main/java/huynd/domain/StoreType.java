package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StoreType.
 */
@Entity
@Table(name = "store_type")
public class StoreType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "store_type_id")
    private String storeTypeID;

    @Column(name = "store_type_name")
    private String storeTypeName;

    @OneToMany(mappedBy = "storeType")
    private Set<StoreGroup> storeGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public String getStoreTypeID() {
        return storeTypeID;
    }

    public StoreType storeTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
        return this;
    }

    public void setStoreTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
    }

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public StoreType storeTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
        return this;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    public Set<StoreGroup> getStoreGroups() {
        return storeGroups;
    }

    public StoreType storeGroups(Set<StoreGroup> storeGroups) {
        this.storeGroups = storeGroups;
        return this;
    }

    public StoreType addStoreGroup(StoreGroup storeGroup) {
        this.storeGroups.add(storeGroup);
        storeGroup.setStoreType(this);
        return this;
    }

    public StoreType removeStoreGroup(StoreGroup storeGroup) {
        this.storeGroups.remove(storeGroup);
        storeGroup.setStoreType(null);
        return this;
    }

    public void setStoreGroups(Set<StoreGroup> storeGroups) {
        this.storeGroups = storeGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreType storeType = (StoreType) o;
        return Objects.equals(storeTypeID, storeType.storeTypeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeTypeID);
    }

    @Override
    public String toString() {
        return "StoreType{" +
            "storeTypeID='" + storeTypeID + '\'' +
            ", storeTypeName='" + storeTypeName + '\'' +
            ", storeGroups=" + storeGroups +
            '}';
    }
}
