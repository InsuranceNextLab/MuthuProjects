package domain;

import java.io.Serializable;

/**
 * Created by ${MUTHU} on 5/15/2015.
 */
public class SideRaiseCount implements Serializable {

    int sideraise_id;
    String date_time, sideraisetime, sideraise_count;

    public int getSideRaiseId() {
        return sideraise_id;
    }

    public void setSideRaiseId(int stepid) {
        this.sideraise_id = stepid;
    }

    public String getSideraiseCount() {
        return sideraise_count;
    }

    public void setSideraiseCount(String step_count) {
        this.sideraise_count = step_count;
    }

    public String getSideRaiseTime() {
        return sideraisetime;
    }

    public void setSideRaiseTime(String step_time) {
        this.sideraisetime = step_time;
    }

    public String getdateTime() {
        return date_time;
    }

    public void setdateTime(String datetime) {
        this.date_time = datetime;
    }
}
