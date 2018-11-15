package huynd.service.dto.response;

import huynd.service.dto.UserDTO;
import huynd.service.dto.VoucherDTO;

import java.sql.Date;

public class VoucherLogReponse {

    private Long voucherLogID;

    private Boolean voucherLogUserUsed;

    private Date pointLogDateUsed;

    private String username;

    private String voucherNumber;

    private String postTitle;

    private String postBanner;

    private Long postDiscountRate;

    private String storeGroupName;

    private String storeGroupLogo;

    public VoucherLogReponse(Long voucherLogID, Boolean voucherLogUserUsed, Date pointLogDateUsed, String username, String voucherNumber, String postTitle, String postBanner, Long postDiscountRate, String storeGroupName, String storeGroupLogo) {
        this.voucherLogID = voucherLogID;
        this.voucherLogUserUsed = voucherLogUserUsed;
        this.pointLogDateUsed = pointLogDateUsed;
        this.username = username;
        this.voucherNumber = voucherNumber;
        this.postTitle = postTitle;
        this.postBanner = postBanner;
        this.postDiscountRate = postDiscountRate;
        this.storeGroupName = storeGroupName;
        this.storeGroupLogo = storeGroupLogo;
    }

    public Long getVoucherLogID() {
        return voucherLogID;
    }

    public void setVoucherLogID(Long voucherLogID) {
        this.voucherLogID = voucherLogID;
    }

    public Boolean getVoucherLogUserUsed() {
        return voucherLogUserUsed;
    }

    public void setVoucherLogUserUsed(Boolean voucherLogUserUsed) {
        this.voucherLogUserUsed = voucherLogUserUsed;
    }

    public Date getPointLogDateUsed() {
        return pointLogDateUsed;
    }

    public void setPointLogDateUsed(Date pointLogDateUsed) {
        this.pointLogDateUsed = pointLogDateUsed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
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

    public String getStoreGroupName() {
        return storeGroupName;
    }

    public void setStoreGroupName(String storeGroupName) {
        this.storeGroupName = storeGroupName;
    }

    public String getStoreGroupLogo() {
        return storeGroupLogo;
    }

    public void setStoreGroupLogo(String storeGroupLogo) {
        this.storeGroupLogo = storeGroupLogo;
    }

    @Override
    public String toString() {
        return "VoucherLogReponse{" +
            "voucherLogID=" + voucherLogID +
            ", voucherLogUserUsed=" + voucherLogUserUsed +
            ", pointLogDateUsed=" + pointLogDateUsed +
            ", username='" + username + '\'' +
            ", voucherNumber='" + voucherNumber + '\'' +
            ", postTitle='" + postTitle + '\'' +
            ", postBanner='" + postBanner + '\'' +
            ", postDiscountRate=" + postDiscountRate +
            ", storeGroupName='" + storeGroupName + '\'' +
            ", storeGroupLogo='" + storeGroupLogo + '\'' +
            '}';
    }
}
