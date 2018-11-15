package huynd.service.dto.response;

import java.sql.Date;
import java.sql.Timestamp;

public class PointLogResponse {


    private Long pointLogID;

    private Long pointLogPointSpent;

    private Timestamp pointLogDateUsed;

    private String username;

    private String voucherNumber;

    private String postTitle;

    private String postBanner;

    private Long postDiscountRate;

    private String storeGroupName;

    private String storeGroupLogo;

    public PointLogResponse(Long pointLogID, Long pointLogPointSpent, Timestamp pointLogDateUsed, String username, String voucherNumber, String postTitle, String postBanner, Long postDiscountRate, String storeGroupName, String storeGroupLogo) {
        this.pointLogID = pointLogID;
        this.pointLogPointSpent = pointLogPointSpent;
        this.pointLogDateUsed = pointLogDateUsed;
        this.username = username;
        this.voucherNumber = voucherNumber;
        this.postTitle = postTitle;
        this.postBanner = postBanner;
        this.postDiscountRate = postDiscountRate;
        this.storeGroupName = storeGroupName;
        this.storeGroupLogo = storeGroupLogo;
    }

    public Long getPointLogID() {
        return pointLogID;
    }

    public void setPointLogID(Long pointLogID) {
        this.pointLogID = pointLogID;
    }

    public Long getPointLogPointSpent() {
        return pointLogPointSpent;
    }

    public void setPointLogPointSpent(Long pointLogPointSpent) {
        this.pointLogPointSpent = pointLogPointSpent;
    }

    public Timestamp getPointLogDateUsed() {
        return pointLogDateUsed;
    }

    public void setPointLogDateUsed(Timestamp pointLogDateUsed) {
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
        return "PointLogResponse{" +
            "pointLogID=" + pointLogID +
            ", pointLogPointSpent=" + pointLogPointSpent +
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
