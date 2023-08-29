package pl.kurs.java.service;

import pl.kurs.java.interfaces.LightController;
import pl.kurs.java.model.TrafficSensors;

import java.time.LocalTime;

public class TrafficLightService {
    private final TrafficSensors trafficLight;
    private LightController lightController;
    public int currentHour = LocalTime.now().getHour();

    public TrafficLightService(TrafficSensors trafficLight, LightController lightController) {
        this.trafficLight = trafficLight;
        this.lightController = lightController;
    }

    public void checkAndControlTrafficLight() {
        if (isHardWeather()) {
            lightController.increaseBrightness();
        }
        if (isButtonPressed()) {
            lightController.shortenRedForPedestrian();
        }
        if (isTemperatureBelowZero()) {
            lightController.extendingTheYellowLight();
        }
        if (highTraffic()){
            lightController.turnOnGreen();
        }
        if (isNightTime() || isEnergySavingMode()) {
            lightController.turnOnFlashYellow();
        } else if (isEmergencyMode() || isPrivilegedVehicleApproaching() ||
                isVehicleSpeeding(trafficLight.getDistanceSensor().getDistance(),
                trafficLight.speedSensor.getSpeed()) || trafficLight.pedestrianSensor.isCrowdDetected()) {
            lightController.turnOnRed();
        } else {
            lightController.turnOffRed();
        }
    }

    private boolean isNightTime() {
        return currentHour >= 23 || currentHour < 5;
    }

    private boolean isEmergencyMode() {
        return trafficLight.emergencyModeController.isInEmergencyMode();
    }

    private boolean isPrivilegedVehicleApproaching() {
        return trafficLight.privilegedVehicleSensor.isPrivilegedVehicleApproaching();
    }

    private boolean isVehicleSpeeding(double distance, double speed) {
        return speed > 40 && distance <= 50;
    }

    private boolean isEnergySavingMode() {
        double noVehicle = 1000.0;
        boolean noPedestrians = false;

        return trafficLight.distanceSensor.getDistance() > noVehicle && trafficLight.pedestrianSensor.isCrowdDetected() == noPedestrians;
    }

    private boolean isHardWeather() {
        return trafficLight.weatherSensor.isRainDetected() || trafficLight.weatherSensor.isFogDetected();
    }

    private boolean isButtonPressed() {
        return trafficLight.pedestrianButton.isPressed();
    }

    private boolean isTemperatureBelowZero() {
        return trafficLight.temperatureSensor.isBelowZero() < 0;
    }

    private boolean highTraffic(){
        return trafficLight.trafficQueueSensor.getVechicleCount() > 10;
    }


}