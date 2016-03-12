package com.insurancenext.rtwcwear.entity;

/**
 * Created by ${MUTHU} on 4/9/2015.
 */
public interface Item {
    float getProximityMinValue();

    float getProximityMaxValue();

    float getCurrentProximityValue();

    void setScalingAnimatorValue(float v);

    void onScaleUpStart();

    void onScaleDownStart();
}
