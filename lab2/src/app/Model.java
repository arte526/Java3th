package app;

public class Model {
    private final double latitude;
    private  final double longitude;


    public Model( double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double calculateDistance(Model other) {
        // Реалізуйте логіку обчислення відстані між двома точками на основі їх широти та довготи.
        // Наприклад, можна використати формулу гаверсинуса для обчислення відстані між точками на сфері.
        // Ось приблизний приклад:
        double lat1 = Math.toRadians(latitude);
        double lon1 = Math.toRadians(longitude);
        double lat2 = Math.toRadians(other.getLatitude());
        double lon2 = Math.toRadians(other.getLongitude());

        // Формула гаверсинуса для обчислення відстані між двома точками на сфері
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6371 * c; // Радіус Землі в кілометрах

        return distance;
    }




}
