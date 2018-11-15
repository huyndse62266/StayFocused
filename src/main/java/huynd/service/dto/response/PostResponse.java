package huynd.service.dto.response;

import huynd.service.dto.StoreGroupDTO;

import java.sql.Date;
import java.util.Objects;

public class PostResponse {

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

    private Long postPointRequired;

    private StoreGroupDTO storeGroup;

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

    public Long getPostPointRequired() {
        return postPointRequired;
    }

    public void setPostPointRequired(Long postPointRequired) {
        this.postPointRequired = postPointRequired;
    }

    public StoreGroupDTO getStoreGroup() {
        return storeGroup;
    }

    public void setStoreGroup(StoreGroupDTO storeGroup) {
        this.storeGroup = storeGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return Objects.equals(postID, that.postID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID);
    }

    @Override
    public String toString() {
        return "PostResponse{" +
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
            '}';
    }
}
