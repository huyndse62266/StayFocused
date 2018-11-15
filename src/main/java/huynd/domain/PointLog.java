package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/**
 * A PointLog.
 */
@Entity
@Table(name = "point_log")
public class PointLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "point_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointLogID;

    @Column(name = "point_log_point_spent")
    private Long pointLogPointSpent;

    @Column(name = "point_log_date_used")
    private Timestamp pointLogDateUsed;

    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.REFRESH},targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id")
    @JsonIgnore
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.REFRESH}, targetEntity = User.class)
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("pointLogs")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getPointLogID() {
        return pointLogID;
    }

    public PointLog pointLogID(Long pointLogID) {
        this.pointLogID = pointLogID;
        return this;
    }

    public void setPointLogID(Long pointLogID) {
        this.pointLogID = pointLogID;
    }

    public Long getPointLogPointSpent() {
        return pointLogPointSpent;
    }

    public PointLog pointLogPointSpent(Long pointLogPointSpent) {
        this.pointLogPointSpent = pointLogPointSpent;
        return this;
    }

    public void setPointLogPointSpent(Long pointLogPointSpent) {
        this.pointLogPointSpent = pointLogPointSpent;
    }

    public Timestamp getPointLogDateUsed() {
        return pointLogDateUsed;
    }

    public PointLog pointLogDateUsed(Timestamp pointLogDateUsed) {
        this.pointLogDateUsed = pointLogDateUsed;
        return this;
    }

    public void setPointLogDateUsed(Timestamp pointLogDateUsed) {
        this.pointLogDateUsed = pointLogDateUsed;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public PointLog voucher(Voucher voucher) {
        this.voucher = voucher;
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public User getUser() {
        return user;
    }

    public PointLog user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        PointLog pointLog = (PointLog) o;
        if (pointLog.getPointLogID() == null || getPointLogID() == null) {
            return false;
        }
        return Objects.equals(getPointLogID(), pointLog.getPointLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPointLogID());
    }

    @Override
    public String toString() {
        return "PointLog{" +
            "pointLogID=" + pointLogID +
            ", pointLogPointSpent=" + pointLogPointSpent +
            ", pointLogDateUsed=" + pointLogDateUsed +
            ", voucher=" + voucher +
            ", user=" + user +
            '}';
    }
}
