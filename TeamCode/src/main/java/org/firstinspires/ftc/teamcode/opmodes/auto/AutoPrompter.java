package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueFarHuman;
import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueFarPath3Human;
import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueGoalPath2_1;
import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.BlueGoalPath2_1_3;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedFarHuman;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedFarPath3Human;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedGoalPath2_1;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.RedGoalPath2_1_3;

import java.util.List;

@Autonomous(group = "auto")
public class AutoPrompter extends SelectableOpMode {

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    public AutoPrompter() {
        super("Select autonomous configuration", s -> {
            s.folder("RED", l -> {
                l.add("FarHuman", RedFarHuman::new);
                l.add("FarPath3Human", RedFarPath3Human::new);
                l.add("GoalPath2_1", RedGoalPath2_1::new);
                l.add("GoalPath2_3_1", RedGoalPath2_1_3::new);
            });
            s.folder("BLUE", a -> {
                a.add("FarHuman", BlueFarHuman::new);
                a.add("FarPath3Human", BlueFarPath3Human::new);
                a.add("GoalPath2_1", BlueGoalPath2_1::new);
                a.add("GoalPath2_3_1", BlueGoalPath2_1_3::new);
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

