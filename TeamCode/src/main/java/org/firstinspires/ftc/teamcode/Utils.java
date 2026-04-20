package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Utils {
    public static boolean joyStickIsMoving(Gamepad gamepad, double threshold) {
        return (Math.abs(gamepad.left_stick_x) > threshold || Math.abs(gamepad.left_stick_y) > threshold
                || Math.abs(gamepad.right_stick_x) > threshold || Math.abs(gamepad.right_stick_y) > threshold);
    }
}
