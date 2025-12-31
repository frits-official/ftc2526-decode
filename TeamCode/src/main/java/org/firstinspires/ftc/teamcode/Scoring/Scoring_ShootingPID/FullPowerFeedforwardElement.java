package org.firstinspires.ftc.teamcode.Scoring.Scoring_ShootingPID;

import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedforward.FeedforwardElement;

public class FullPowerFeedforwardElement implements FeedforwardElement {
    @Override
    public double calculate(KineticState error) {
        return 1;
    }
    @Override
    public void reset() {

    }
}