package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueFarHuman;
import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueFarPath3Human;
import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueGoal;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedFarHuman;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedFarPath3Human;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedGoal;

import java.util.List;

@TeleOp(group = "auto")
public class AutoPrompter extends SelectableOpMode {

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    public AutoPrompter() {
        super("Select autonomous configuration", s -> {
            s.folder("RED", l -> {
                l.add("FarHuman", RedFarHuman::new);
                l.add("FarPath3Human", RedFarPath3Human::new);
                l.add("Goal", RedGoal::new);
            });
            s.folder("BLUE", a -> {
                a.add("FarHuman", BlueFarHuman::new);
                a.add("FarPath3Human", BlueFarPath3Human::new);
                a.add("Goal", BlueGoal::new);
            });
        });
    }

    @Override
    public void onSelect() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void onLog(List<String> lines) {}
}

