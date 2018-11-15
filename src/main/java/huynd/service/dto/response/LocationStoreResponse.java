package huynd.service.dto.response;

public class LocationStoreResponse {
    private String storeLatitude;

    private String storeLongitude;

    public String getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(String storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public String getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(String storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    @Override
    public String toString() {
        return "LocationStoreResponse{" +
            "storeLatitude='" + storeLatitude + '\'' +
            ", storeLongitude='" + storeLongitude + '\'' +
            '}';
    }
}
