public class CarsAssemble {

    public static double productionRatePerHour(int speed) {
        int carsPerHour = speed * 221;
        double successRate = successRate(speed);
        return carsPerHour * successRate;
    }

    public static int workingItemsPerMinute(int speed) {
        double perHour = productionRatePerHour(speed);
        return (int) (perHour / 60);
    }

    private static double successRate(int speed) {
        if (speed <= 0) {
            return 0.0;
        } else if (speed <= 4) {
            return 1.0;
        } else if (speed <= 8) {
            return 0.9;
        } else if (speed == 9) {
            return 0.8;
        } else {
            return 0.77;
        }
    }
}