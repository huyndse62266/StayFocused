package huynd.service.dto.response;

public class NumberOfVoucher {
    private Long totalVoucher;
    private Long usedVoucher;
    private Long unusedVoucher;

    public NumberOfVoucher(Long totalVoucher, Long usedVoucher, Long unusedVoucher) {
        this.totalVoucher = totalVoucher;
        this.usedVoucher = usedVoucher;
        this.unusedVoucher = unusedVoucher;
    }

    public Long getTotalVoucher() {
        return totalVoucher;
    }

    public void setTotalVoucher(Long totalVoucher) {
        this.totalVoucher = totalVoucher;
    }

    public Long getUsedVoucher() {
        return usedVoucher;
    }

    public void setUsedVoucher(Long usedVoucher) {
        this.usedVoucher = usedVoucher;
    }

    public Long getUnusedVoucher() {
        return unusedVoucher;
    }

    public void setUnusedVoucher(Long unusedVoucher) {
        this.unusedVoucher = unusedVoucher;
    }
}
