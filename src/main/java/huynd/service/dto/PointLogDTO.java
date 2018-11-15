package huynd.service.dto;

import huynd.domain.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PointLog entity.
 */
public class PointLogDTO implements Serializable {


    private Long pointLogID;

    private Long pointLogPointSpent;

    private Timestamp pointLogDateUsed;

    private VoucherDTO voucher;

    private UserDTO user;

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

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PointLogDTO pointLogDTO = (PointLogDTO) o;
        if (pointLogDTO.getPointLogID() == null || getPointLogID() == null) {
            return false;
        }
        return Objects.equals(getPointLogID(), pointLogDTO.getPointLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPointLogID());
    }

    @Override
    public String toString() {
        return "PointLogDTO{" +
            "pointLogID=" + pointLogID +
            ", pointLogPointSpent=" + pointLogPointSpent +
            ", pointLogDateUsed=" + pointLogDateUsed +
            ", voucher=" + voucher +
            ", user=" + user +
            '}';
    }
}
