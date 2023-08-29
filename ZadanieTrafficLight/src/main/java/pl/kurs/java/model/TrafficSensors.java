package pl.kurs.java.model;

import pl.kurs.java.interfaces.*;

public class TrafficSensors {

    public DistanceSensor distanceSensor;
    public SpeedSensor speedSensor;
    public LightController lightController;
    public PedestrianSensor pedestrianSensor;
    public EmergencyModeController emergencyModeController;
    public PrivilegedVehicleSensor privilegedVehicleSensor;
    public WeatherSensor weatherSensor;
    public PedestrianButton pedestrianButton;
    public TemperatureSensor temperatureSensor;
    public TrafficQueueSensor trafficQueueSensor;

    public TrafficSensors(DistanceSensor distanceSensor, SpeedSensor speedSensor, //LightController lightController,
                          PedestrianSensor pedestrianSensor, EmergencyModeController emergencyModeController,
                          PrivilegedVehicleSensor privilegedVehicleSensor, WeatherSensor weatherSensor, PedestrianButton pedestrianButton,
                          TemperatureSensor temperatureSensor, TrafficQueueSensor trafficQueueSensor) {
        this.distanceSensor = distanceSensor;
        this.speedSensor = speedSensor;
        this.pedestrianSensor = pedestrianSensor;
        this.emergencyModeController = emergencyModeController;
        this.privilegedVehicleSensor = privilegedVehicleSensor;
        this.weatherSensor = weatherSensor;
        this.pedestrianButton = pedestrianButton;
        this.temperatureSensor = temperatureSensor;
        this.trafficQueueSensor = trafficQueueSensor;
    }

    public DistanceSensor getDistanceSensor() {
        return distanceSensor;
    }

    public SpeedSensor getSpeedSensor() {
        return speedSensor;
    }

    public PedestrianSensor getPedestrianSensor() {
        return pedestrianSensor;
    }

    public EmergencyModeController getEmergencyModeController() {
        return emergencyModeController;
    }

    public PrivilegedVehicleSensor getPrivilegedVehicleSensor() {
        return privilegedVehicleSensor;
    }

    public WeatherSensor getWeatherSensor() {
        return weatherSensor;
    }

    public PedestrianButton getPedestrianButton() {
        return pedestrianButton;
    }

    public TemperatureSensor getTemperatureSensor() {
        return temperatureSensor;
    }

    public TrafficQueueSensor getTrafficQueueSensor() {
        return trafficQueueSensor;
    }
}
