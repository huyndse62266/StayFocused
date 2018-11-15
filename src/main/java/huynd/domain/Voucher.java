package huynd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Voucher.
 */
@Entity
@Table(name = "voucher")
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "voucher_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long voucherID;

    @Column(name = "voucher_number")
    private String voucherNumber;

    @Column(name = "voucher_status")
    private Boolean voucherStatus;

    @Column(name = "voucher_point_required")
    private Long voucherPointRequired;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties("vouchers")
    private Post post;



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getVoucherID() {
        return voucherID;
    }

    public Voucher voucherID(Long voucherID) {
        this.voucherID = voucherID;
        return this;
    }

    public void setVoucherID(Long voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public Voucher voucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
        return this;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Boolean isVoucherStatus() {
        return voucherStatus;
    }

    public Voucher voucherStatus(Boolean voucherStatus) {
        this.voucherStatus = voucherStatus;
        return this;
    }

    public void setVoucherStatus(Boolean voucherStatus) {
        this.voucherStatus = voucherStatus;
    }


    public Long getVoucherPointRequired() {
        return voucherPointRequired;
    }

    public Voucher voucherPointRequired(Long voucherPointRequired){
        this.voucherPointRequired = voucherPointRequired;
        return this;
    }

    public void setVoucherPointRequired(Long voucherPointRequired) {
        this.voucherPointRequired = voucherPointRequired;
    }

    public Post getPost() {
        return post;
    }

    public Voucher post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherID, voucher.voucherID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherID);
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "voucherID=" + voucherID +
            ", voucherNumber='" + voucherNumber + '\'' +
            ", voucherStatus=" + voucherStatus +
            ", post=" + post +
            '}';
    }
}
