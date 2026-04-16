package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.LED;

public class LEDIndicator {
    DigitalChannel led;

    public LEDIndicator(OpMode opMode) {
        //led = opMode.hardwareMap.get(DigitalChannel.class, "led");
        //led.setMode(DigitalChannel.Mode.OUTPUT);
    }

    //public void set(boolean on) {
        //led.setState(on);
    }

    //public boolean get() {
        //return led.getState();
    //}
//}
