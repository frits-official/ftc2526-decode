package Subsystem.PID;

import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.FeedbackElement;

public class FullPowerFeedbackElement implements FeedbackElement {
    @Override
    public double calculate(KineticState error) {
        return 1;
    }
    @Override
    public void reset() {

    }
}