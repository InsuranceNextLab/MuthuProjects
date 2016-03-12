package domain;

import java.io.Serializable;

/**
 * Created by ${MUTHU} on 4/29/2015.
 */
public class Stepcounts implements Serializable {

    int step_id;
    String date_time, steptime, stepcount;

    public int getStepId() {
        return step_id;
    }

    public void setStepId(int stepid) {
        this.step_id = stepid;
    }

    public String getStepCount() {
        return stepcount;
    }

    public void setStepCount(String step_count) {
        this.stepcount = step_count;
    }

    public String getWalkTime() {
        return steptime;
    }

    public void setWalkTime(String step_time) {
        this.steptime = step_time;
    }

    public String getdateTime() {
        return date_time;
    }

    public void setdateTime(String datetime) {
        this.date_time = datetime;
    }
}
