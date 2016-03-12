package domain;

import java.io.Serializable;

/**
 * Created by ${MUTHU} on 5/15/2015.
 */
public class BicepsCount implements Serializable {

    int step_id;
    String date_time, bicepstime, bicepscount;

    public int getBicepsId() {
        return step_id;
    }

    public void setBicepsId(int stepid) {
        this.step_id = stepid;
    }

    public String getBicepsCount() {
        return bicepscount;
    }

    public void setBicepsCount(String step_count) {
        this.bicepscount = step_count;
    }

    public String getBicepsTime() {
        return bicepstime;
    }

    public void setBicepsTime(String step_time) {
        this.bicepstime = step_time;
    }

    public String getdateTime() {
        return date_time;
    }

    public void setdateTime(String datetime) {
        this.date_time = datetime;
    }
}

