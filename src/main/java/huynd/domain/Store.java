package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ListStore.
 */
@Entity
@Table(name = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeID;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "store_latitude")
    private String storeLatitude;

    @Column(name = "store_longitude")
    private String storeLongitude;

    @ManyToOne
    @JoinColumn(name = "store_group_id")
    @JsonIgnoreProperties("stores")
    private StoreGroup storeGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Long getStoreID() {
        return storeID;
    }

    public Store storeID(Long storeID) {
        this.storeID = storeID;
        return this;
    }

    public void setStoreID(Long storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public Store storeName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public Store storeAddress(String storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }


    public String getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(String storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public String getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(String storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    public StoreGroup getStoreGroup() {
        return storeGroup;
    }

    public Store store(StoreGroup storeGroup) {
        this.storeGroup = storeGroup;
        return this;
    }

    public void setStoreGroup(StoreGroup storeGroup) {
        this.storeGroup = storeGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(storeID, store.storeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeID);
    }

    @Override
    public String toString() {
        return "Store{" +
            "storeID='" + storeID + '\'' +
            ", storeName='" + storeName + '\'' +
            ", storeAddress='" + storeAddress + '\'' +
            ", storeLatitude=" + storeLatitude +
            ", storeLongitude=" + storeLongitude +
            ", storeGroup=" + storeGroup +
            '}';
    }
}
