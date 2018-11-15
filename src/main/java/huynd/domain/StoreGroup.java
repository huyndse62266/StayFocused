package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A StoreGroup.
 */
@Entity
@Table(name = "store_group")
public class StoreGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "store_group_id")
    private Long storeGroupID;

    @Column(name = "store_group_name")
    private String storeGroupName;

    @Column(name = "store_group_phone")
    private Long storeGroupPhone;

    @Column(name = "store_group_mail")
    private String storeGroupMail;

    @Column(name = "store_group_web")
    private String storeGroupWeb;

    @Column(name = "store_group_description")
    private String storeGroupDescription;

    @Column(name = "store_logo")
    private String storeGroupLogo;

    @ManyToOne
    @JoinColumn(name = "store_type_id")
    @JsonIgnoreProperties("storeGroups")
    private StoreType storeType;

    @OneToMany(mappedBy = "storeGroup")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "storeGroup")
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "storeGroup")
    private Set<UserStoreGroup> userStoreGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Long getStoreGroupID() {
        return storeGroupID;
    }

    public StoreGroup storeGroupID(Long storeGroupID) {
        this.storeGroupID = storeGroupID;
        return this;
    }

    public void setStoreGroupID(Long storeGroupID) {
        this.storeGroupID = storeGroupID;
    }


    public String getStoreGroupName() {
        return storeGroupName;
    }

    public StoreGroup storeGroupName(String storeGroupName) {
        this.storeGroupName = storeGroupName;
        return this;
    }

    public void setStoreGroupName(String storeGroupName) {
        this.storeGroupName = storeGroupName;
    }

    public Long getStoreGroupPhone() {
        return storeGroupPhone;
    }


    public StoreGroup storeGroupPhone(Long storeGroupPhone) {
        this.storeGroupPhone = storeGroupPhone;
        return this;
    }


    public void setStoreGroupPhone(Long storeGroupPhone) {
        this.storeGroupPhone = storeGroupPhone;
    }

    public String getStoreGroupMail() {
        return storeGroupMail;
    }

    public StoreGroup storeGroupMail(String storeGroupMail) {
        this.storeGroupMail = storeGroupMail;
        return this;
    }

    public void setStoreGroupMail(String storeGroupMail) {
        this.storeGroupMail = storeGroupMail;
    }

    public String getStoreGroupWeb() {
        return storeGroupWeb;
    }

    public StoreGroup storeGroupWeb(String storeGroupWeb) {
        this.storeGroupWeb = storeGroupWeb;
        return this;
    }

    public void setStoreGroupWeb(String storeGroupWeb) {
        this.storeGroupWeb = storeGroupWeb;
    }

    public String getStoreGroupDescription() {
        return storeGroupDescription;
    }

    public StoreGroup storeGroupDescription(String storeGroupDescription) {
        this.storeGroupDescription = storeGroupDescription;
        return this;
    }

    public void setStoreGroupDescription(String storeGroupDescription) {
        this.storeGroupDescription = storeGroupDescription;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public StoreGroup posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public StoreGroup addPost(Post post) {
        this.posts.add(post);
        post.setStoreGroup(this);
        return this;
    }

    public StoreGroup removePost(Post post) {
        this.posts.remove(post);
        post.setStoreGroup(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Store> getListStores() {
        return stores;
    }

    public StoreGroup listStores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public StoreGroup addListStore(Store store) {
        this.stores.add(store);
        store.setStoreGroup(this);
        return this;
    }

    public StoreGroup removeListStore(Store store) {
        this.stores.remove(store);
        store.setStoreGroup(null);
        return this;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public void setListStores(Set<Store> stores) {
        this.stores = stores;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public StoreGroup storeType(StoreType storeType) {
        this.storeType = storeType;
        return this;
    }

    public String getStoreGroupLogo() {
        return storeGroupLogo;
    }

    public void setStoreGroupLogo(String storeGroupLogo) {
        this.storeGroupLogo = storeGroupLogo;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Set<UserStoreGroup> getUserStoreGroups() {
        return userStoreGroups;
    }

    public StoreGroup userStoreGroups(Set<UserStoreGroup> userStoreGroups) {
        this.userStoreGroups = userStoreGroups;
        return this;
    }

    public StoreGroup addUserStoreGroup(UserStoreGroup userStoreGroup) {
        this.userStoreGroups.add(userStoreGroup);
        userStoreGroup.setStoreGroup(this);
        return this;
    }

    public StoreGroup removeUserStoreGroup(UserStoreGroup userStoreGroup) {
        this.userStoreGroups.remove(userStoreGroup);
        userStoreGroup.setStoreGroup(null);
        return this;
    }

    public void setUserStoreGroups(Set<UserStoreGroup> userStoreGroups) {
        this.userStoreGroups = userStoreGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreGroup that = (StoreGroup) o;
        return Objects.equals(storeGroupID, that.storeGroupID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeGroupID);
    }

    @Override
    public String toString() {
        return "StoreGroup{" +
            "storeGroupID=" + storeGroupID +
            ", storeGroupName='" + storeGroupName + '\'' +
            ", storeGroupPhone=" + storeGroupPhone +
            ", storeGroupMail='" + storeGroupMail + '\'' +
            ", storeGroupWeb='" + storeGroupWeb + '\'' +
            ", storeGroupDescription='" + storeGroupDescription + '\'' +
            ", storeGroupLogo='" + storeGroupLogo + '\'' +
            ", storeType=" + storeType +
            ", posts=" + posts +
            ", stores=" + stores +
            '}';
    }
}
