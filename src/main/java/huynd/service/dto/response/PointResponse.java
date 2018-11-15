package huynd.service.dto.response;

public class PointResponse {


    private Long used;
    private Long left;
    private Long earn;


    public PointResponse(Long used, Long left, Long earn) {
        this.used = used;
        this.left = left;
        this.earn = earn;
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
