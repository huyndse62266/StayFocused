package huynd.service.dto.response;

public class VoucherPointResponse {

    private String voucherNumber;
    private Long used;
    private Long left;
    private Long earn;


    public VoucherPointResponse(Long used, Long left, Long earn) {
        this.used = used;
        this.left = left;
        this.earn = earn;
    }

    public VoucherPointResponse(String voucherNumber, Long used, Long left, Long earn) {
        this.voucherNumber = voucherNumber;
        this.used = used;
        this.left = left;
        this.earn = earn;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Long getEarn() {
        return earn;
    }

    public void setEarn(Long earn) {
        this.earn = earn;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }
}
