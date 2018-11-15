package huynd.service.dto;

import java.sql.Date;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Post entity.
 */
public class PostDTO implements Serializable {


    private Long postID;

    private String postName;

    private String postTitle;

    private String postDescription;

    private Date postStartDate;

    private Date postEndDate;

    private Long postTotalNumberVoucher;

    private Long postRemainNumberVoucher;

    private String postBanner;

    private Long postDiscountRate;

    private StoreGroupDTO storeGroup;

    private Long postPointRequired;

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Date getPostStartDate() {
        return postStartDate;
    }

    public void setPostStartDate(Date postStartDate) {
        this.postStartDate = postStartDate;
    }

    public Date getPostEndDate() {
        return postEndDate;
    }

    public void setPostEndDate(Date postEndDate) {
        this.postEndDate = postEndDate;
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

    public void setPostRemainNumberVoucher(Long postRemainNumberVoucher) {
        this.postRemainNumberVoucher = postRemainNumberVoucher;
    }

    public Long getPostPointRequired() {
        return postPointRequired;
    }

    public void setPostPointRequired(Long postPointRequired) {
        this.postPointRequired = postPointRequired;
    }


    public String getPostBanner() {
        return postBanner;
    }

    public void setPostBanner(String postBanner) {
        this.postBanner = postBanner;
    }

    public Long getPostDiscountRate() {
        return postDiscountRate;
    }

    public void setPostDiscountRate(Long postDiscountRate) {
        this.postDiscountRate = postDiscountRate;
    }

    public StoreGroupDTO getStoreGroup() {
        return storeGroup;
    }

    public void setStoreGroup(StoreGroupDTO storeGroup) {
        this.storeGroup = storeGroup;
    }


    @Override
    public String toString() {
        return "PostDTO{" +
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
            ", storeGroup=" + storeGroup +
            ", postPointRequired=" + postPointRequired +
            '}';
    }
}
