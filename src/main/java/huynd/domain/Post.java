package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postID;

    @NotNull
    @Column(name = "post_name")
    private String postName;

    @NotNull
    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_description")
    private String postDescription;

    @Column(name = "post_start_date")
    private Date postStartDate;

    @Column(name = "post_end_date")
    private Date postEndDate;

    @Column(name = "post_total_number_voucher")
    private Long postTotalNumberVoucher;

    @Column(name = "post_remain_number_voucher")
    private Long postRemainNumberVoucher;

    @Column(name = "post_banner")
    private String postBanner;

    @Column(name = "post_discount_rate")
    private Long postDiscountRate;

    @Column(name = "post_point_required")
    private Long postPointRequired;

    @ManyToOne
    @JoinColumn(name = "store_group_id")
    @JsonIgnoreProperties("posts")
    private StoreGroup storeGroup;

    @OneToMany(mappedBy = "post")
    private Set<Voucher> vouchers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getPostID() {
        return postID;
    }

    public Post postID(Long postID) {
        this.postID = postID;
        return this;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public String getPostName() {
        return postName;
    }

    public Post postName(String postName) {
        this.postName = postName;
        return this;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public Post postDescription(String postDescription) {
        this.postDescription = postDescription;
        return this;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Date getPostStartDate() {
        return postStartDate;
    }

    public Post postStartDate(Date postStartDate) {
        this.postStartDate = postStartDate;
        return this;
    }

    public void setPostStartDate(Date postStartDate) {
        this.postStartDate = postStartDate;
    }

    public Date getPostEndDate() {
        return postEndDate;
    }

    public Post postEndDate(Date postEndDate) {
        this.postEndDate = postEndDate;
        return this;
    }

    public void setPostEndDate(Date postEndDate) {
        this.postEndDate = postEndDate;
    }

    public String getPostBanner() {
        return postBanner;
    }

    public Post postBanner(String postBanner) {
        this.postBanner = postBanner;
        return this;
    }

    public void setPostBanner(String postBanner) {
        this.postBanner = postBanner;
    }

    public StoreGroup getStoreGroup() {
        return storeGroup;
    }

    public Post store(StoreGroup storeGroup) {
        this.storeGroup = storeGroup;
        return this;
    }

    public void setStoreGroup(StoreGroup storeGroup) {
        this.storeGroup = storeGroup;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public Post vouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
        return this;
    }

    public Post addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
        voucher.setPost(this);
        return this;
    }

    public Post removeVoucher(Voucher voucher) {
        this.vouchers.remove(voucher);
        voucher.setPost(null);
        return this;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Long getPostTotalNumberVoucher() {
        return postTotalNumberVoucher;
    }

    public void setPostTotalNumberVoucher(Long postTotalNumberVoucher) {
        this.postTotalNumberVoucher = postTotalNumberVoucher;
    }

    public Long getPostRemainNumberVoucher() {
        return postRemainNumberVoucher;
    }

    public Long getPostPointRequired() {
        return postPointRequired;
    }

    public void setPostPointRequired(Long postPointRequired) {
        this.postPointRequired = postPointRequired;
    }

    public void setPostRemainNumberVoucher(Long postRemainNumberVoucher) {
        this.postRemainNumberVoucher = postRemainNumberVoucher;
    }

    public Long getPostDiscountRate() {
        return postDiscountRate;
    }

    public void setPostDiscountRate(Long postDiscountRate) {
        this.postDiscountRate = postDiscountRate;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postID, post.postID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID);
    }

    @Override
    public String toString() {
        return "Post{" +
            "postID=" + postID +
            ", postName='" + postName + '\'' +
            ", postTitle='" + postTitle + '\'' +
            ", postDescription='" + postDescription + '\'' +
            ", postStartDate=" + postStartDate +
            ", postEndDate=" + postEndDate +
            ", postTotalNumberVoucher=" + postTotalNumberVoucher +
            ", postRemainNumberVoucher=" + postRemainNumberVoucher +
            ", postBanner='" + postBanner + '\'' +
            ", postDiscountRate=" + postDiscountRate +
            ", postPointRequired=" + postPointRequired +
            ", storeGroup=" + storeGroup +
            ", vouchers=" + vouchers +
            '}';
    }
}
