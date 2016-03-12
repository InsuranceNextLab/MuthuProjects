package domain;

import java.io.Serializable;

/**
 * Created by ${MUTHU} on 5/15/2015.
 */
public class FrontRaiseCount implements Serializable {

    int frontraise_id;
    String date_time, frontraisetime, frontraisecount;

    public int getFrontRaiseId() {
        return frontraise_id;
    }

    public void setFrontRaiseId(int stepid) {
        this.frontraise_id = stepid;
    }

    public String getFrontRaiseCount() {
        return frontraisecount;
    }

    public void setFrontRaiseCount(String step_count) {
        this.frontraisecount = step_count;
    }

    public String getFrontRaiseTime() {
        return frontraisetime;
    }

    public void setFrontRaiseTime(String step_time) {
        this.frontraisetime = step_time;
    }

    public String getdateTime() {
        return date_time;
    }

    public void setdateTime(String datetime) {
        this.date_time = datetime;
    }
}
