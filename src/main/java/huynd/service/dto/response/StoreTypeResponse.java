package huynd.service.dto.response;

public class StoreTypeResponse {
    private String storeTypeID;

    private String storeTypeName;

    private Long size;

    public String getStoreTypeID() {
        return storeTypeID;
    }

    public void setStoreTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
    }

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "StoreTypeResponse{" +
            "storeTypeID='" + storeTypeID + '\'' +
            ", storeTypeName='" + storeTypeName + '\'' +
            ", size=" + size +
            '}';
    }
}
