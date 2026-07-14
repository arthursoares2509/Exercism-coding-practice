class NeedForSpeed {
    private final int speed;
    private final int batteryDrain;
    private int distanceDriven = 0;
    private int battery = 100;

    NeedForSpeed(int speed, int batteryDrain) {
        this.speed = speed;
        this.batteryDrain = batteryDrain;
    }

    public boolean batteryDrained() {
        return battery < batteryDrain;
    }

    public int distanceDriven() {
        return distanceDriven;
    }

    public void drive() {
        if (!batteryDrained()) {
            distanceDriven += speed;
            battery -= batteryDrain;
        }
    }

    public static NeedForSpeed nitro() {
        return new NeedForSpeed(50, 4);
    }

    int getSpeed() {
        return speed;
    }

    int getBatteryDrain() {
        return batteryDrain;
    }
}

class RaceTrack {
    private final int distance;

    RaceTrack(int distance) {
        this.distance = distance;
    }

    public boolean canFinishRace(NeedForSpeed car) {
        int speed = car.getSpeed();
        int batteryDrain = car.getBatteryDrain();

        int battery = 100;
        int distanceCovered = 0;

        while (battery >= batteryDrain) {
            distanceCovered += speed;
            battery -= batteryDrain;
            if (distanceCovered >= distance) {
                return true;
            }
        }

        return false;
    }
}