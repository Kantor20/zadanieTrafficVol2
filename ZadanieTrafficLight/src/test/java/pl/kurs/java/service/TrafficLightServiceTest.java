package pl.kurs.java.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.kurs.java.interfaces.*;
import pl.kurs.java.model.TrafficSensors;


public class TrafficLightServiceTest {
    private LightController lightController;
    private TrafficLightService service;
    private TrafficSensors sensors;

    @Before
    public void init() {
        sensors = new TrafficSensors(Mockito.mock(DistanceSensor.class), Mockito.mock(SpeedSensor.class),
                Mockito.mock(PedestrianSensor.class), Mockito.mock(EmergencyModeController.class),
                Mockito.mock(PrivilegedVehicleSensor.class), Mockito.mock(WeatherSensor.class),
                Mockito.mock(PedestrianButton.class), Mockito.mock(TemperatureSensor.class),
                Mockito.mock(TrafficQueueSensor.class));
        lightController = Mockito.mock(LightController.class);
        service = new TrafficLightService(sensors, lightController);

    }


    @Test
    public void shouldTurnOnRedLightWhenSpeedAbove40AndDistanceLessThan50() {
        Mockito.when(sensors.getDistanceSensor().getDistance()).thenReturn(40.0);
        Mockito.when(sensors.getSpeedSensor().getSpeed()).thenReturn(45.0);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).turnOnRed();
    }

    @Test
    public void shouldNotTurnOnRedLightWhenSpeedBelow40() {
        Mockito.when(sensors.getDistanceSensor().getDistance()).thenReturn(40.0);
        Mockito.when(sensors.getSpeedSensor().getSpeed()).thenReturn(35.0);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController, Mockito.never()).turnOnRed();
    }

    @Test
    public void shouldTurnOnRedLightWhenCrowdDetected() {
        Mockito.when(sensors.getPedestrianSensor().isCrowdDetected()).thenReturn(true);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).turnOnRed();
    }

    @Test
    public void shouldTurnOnRedLightInEmergencyMode() {
        Mockito.when(sensors.getEmergencyModeController().isInEmergencyMode()).thenReturn(true);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).turnOnRed();
    }

    @Test
    public void shouldTurnOnFlashYellowBetween23And5() {
        service.currentHour = 24;

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).turnOnFlashYellow();
    }

    @Test
    public void shouldTurnOnFlashYellowIfMotionIsNotDetected() {
        Mockito.when(sensors.getDistanceSensor().getDistance()).thenReturn(1100.0);
        Mockito.when(sensors.getPedestrianSensor().isCrowdDetected()).thenReturn(false);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).turnOnFlashYellow();
    }

    @Test
    public void shouldNotTurnOnFlashYellowIfMotionIsNotDetected() {
        Mockito.when(sensors.getDistanceSensor().getDistance()).thenReturn(50.0);
        Mockito.when(sensors.getPedestrianSensor().isCrowdDetected()).thenReturn(false);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController, Mockito.never()).turnOnFlashYellow();
    }

    @Test
    public void shouldIncreaseBrightnessIfRainyOrFoggy() {
        Mockito.when(sensors.getWeatherSensor().isFogDetected()).thenReturn(true);
        Mockito.when(sensors.getWeatherSensor().isRainDetected()).thenReturn(true);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).increaseBrightness();
    }

    @Test
    public void shouldIncreaseBrightnessIfIsOnlyRainy() {
        Mockito.when(sensors.getWeatherSensor().isRainDetected()).thenReturn(true);
        Mockito.when(sensors.getWeatherSensor().isFogDetected()).thenReturn(false);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).increaseBrightness();
    }

    @Test
    public void shouldShortenWaitingTimeForGreenLight() {
        Mockito.when(sensors.getPedestrianButton().isPressed()).thenReturn(true);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).shortenRedForPedestrian();
    }

    @Test
    public void waitingTimeForGreenLightShouldBeNormal() {
        Mockito.when(sensors.getPedestrianButton().isPressed()).thenReturn(false);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController, Mockito.never()).shortenRedForPedestrian();
    }

    @Test
    public void shouldExtendingTheYellowLightIfTempIsBelowZero() {
        Mockito.when(sensors.getTemperatureSensor().isBelowZero()).thenReturn(-2);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).extendingTheYellowLight();
    }

    @Test
    public void shouldNotExtendingTheYellowLightIfTempIsUnderZero() {
        Mockito.when(sensors.getTemperatureSensor().isBelowZero()).thenReturn(5);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController, Mockito.never()).extendingTheYellowLight();
    }

    @Test
    public void shouldShortenedWaitingTimeForGreenLightForVechicles() {
        Mockito.when(sensors.getTrafficQueueSensor().getVechicleCount()).thenReturn(11);

        service.checkAndControlTrafficLight();

        Mockito.verify(lightController).turnOnGreen();
    }
}