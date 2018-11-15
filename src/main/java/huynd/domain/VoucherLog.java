package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

/**
 * A VoucherLog.
 */
@Entity
@Table(name = "voucher_log")
public class VoucherLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "voucher_log_id")
    private Long voucherLogID;

    @Column(name = "voucher_log_user_used")
    private Boolean voucherLogUserUsed;

    @Column(name = "voucher_log_date_used")
    private Date voucherLogDateUsed;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("voucherLogs")
    private User user;

    @OneToOne
    @JoinColumn(name = "voucher_id")
    @JsonIgnore
    private Voucher voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getVoucherLogID() {
        return voucherLogID;
    }

    public VoucherLog voucherLogID(Long voucherLogID) {
        this.voucherLogID = voucherLogID;
        return this;
    }

    public void setVoucherLogID(Long voucherLogID) {
        this.voucherLogID = voucherLogID;
    }


    public Boolean getVoucherLogUserUsed() {
        return voucherLogUserUsed;
    }

    public VoucherLog voucherLogUserUsed(Boolean voucherLogUserUsed) {
        this.voucherLogUserUsed = voucherLogUserUsed;
        return this;
    }

    public void setVoucherLogUserUsed(Boolean voucherLogUserUsed) {
        this.voucherLogUserUsed = voucherLogUserUsed;
    }

    public Date getVoucherLogDateUsed() {
        return voucherLogDateUsed;
    }

    public VoucherLog voucherLogDateUsed(Date voucherLogDateUsed) {
        this.voucherLogDateUsed = voucherLogDateUsed;
        return this;
    }

    public void setVoucherLogDateUsed(Date voucherLogDateUsed) {
        this.voucherLogDateUsed = voucherLogDateUsed;
    }

    public User getUser() {
        return user;
    }

    public VoucherLog username(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public VoucherLog voucher(Voucher voucher) {
        this.voucher = voucher;
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VoucherLog voucherLog = (VoucherLog) o;
        if (voucherLog.getVoucherLogID() == null || getVoucherLogID() == null) {
            return false;
        }
        return Objects.equals(getVoucherLogID(), voucherLog.getVoucherLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVoucherLogID());
    }

    @Override
    public String toString() {
        return "VoucherLog{" +
            "voucherLogID=" + voucherLogID +
            ", voucherLogUserUsed=" + voucherLogUserUsed +
            ", voucherLogDateUsed=" + voucherLogDateUsed +
            ", user=" + user +
            ", voucher=" + voucher +
            '}';
    }
}
