package huynd.service.dto;

import huynd.domain.User;

import java.sql.Date;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VoucherLog entity.
 */
public class VoucherLogDTO implements Serializable {


    private Long voucherLogID;

    private Boolean voucherLogUserUsed;

    private Date voucherLogDateUsed;

    private UserDTO user;

    private VoucherDTO voucher;

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

    public Date getVoucherLogDateUsed() {
        return voucherLogDateUsed;
    }

    public void setVoucherLogDateUsed(Date voucherLogDateUsed) {
        this.voucherLogDateUsed = voucherLogDateUsed;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VoucherLogDTO voucherLogDTO = (VoucherLogDTO) o;
        if (voucherLogDTO.getVoucherLogID() == null || getVoucherLogID() == null) {
            return false;
        }
        return Objects.equals(getVoucherLogID(), voucherLogDTO.getVoucherLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVoucherLogID());
    }

    @Override
    public String toString() {
        return "VoucherLogDTO{" +
            "voucherLogID=" + voucherLogID +
            ", voucherLogUserUsed=" + voucherLogUserUsed +
            ", voucherLogDateUsed=" + voucherLogDateUsed +
            ", user=" + user +
            ", voucher=" + voucher +
            '}';
    }
}
