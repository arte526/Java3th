package laba3.app;

public class Model {
   private final Integer id;
    private final String nameSight;
    private final double latitude;
    private final double longitude;

    private final String region;

    private final String picture;

    public Model(Integer id, String nameSight, double latitude, double longitude, String region, String picture) {
        this.id = id;
        this.nameSight = nameSight;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public String getNameSight() {
        return nameSight;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getRegion() {
        return region;
    }
    public String getPicture() {
        return picture;
    }
}
