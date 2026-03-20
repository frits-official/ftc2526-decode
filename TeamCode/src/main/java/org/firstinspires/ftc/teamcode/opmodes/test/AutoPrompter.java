package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.FarZone.BlueFarZoneLeverPath3_2_1Path2Ball12NoIndulge;
import org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.NearZone.BlueNearZoneLeverPath1_2_3Path1Ball12NoIndulge;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.FarZone.RedFarZoneLeverPath3_2_1Path2Ball12NoIndulge;
import org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.NearZone.RedNearZoneLeverPath1_2_3Path2Ball12NoIndulge;

import java.util.List;

@TeleOp(group = "test")
public class AutoPrompter extends SelectableOpMode {

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    public AutoPrompter() {
        super("Select autonomous configuration", s -> {
            s.folder("RED", l -> {
                l.add("Far zone", RedFarZoneLeverPath3_2_1Path2Ball12NoIndulge::new);
                l.add("Goal zone", RedNearZoneLeverPath1_2_3Path2Ball12NoIndulge::new);
            });
            s.folder("BLUE", a -> {
                a.add("Far zone", BlueFarZoneLeverPath3_2_1Path2Ball12NoIndulge::new);
                a.add("Goal zone", BlueNearZoneLeverPath1_2_3Path1Ball12NoIndulge::new);
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

