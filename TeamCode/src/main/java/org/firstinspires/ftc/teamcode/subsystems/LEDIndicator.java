package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.LED;

public class LEDIndicator {
    LED led;

    public LEDIndicator(OpMode opMode) {
        led = opMode.hardwareMap.get(LED.class, "led");
    }

    public void set(boolean on) {
        led.enable(on);
    }

    public boolean get() {
        return led.isLightOn();
    }
}
