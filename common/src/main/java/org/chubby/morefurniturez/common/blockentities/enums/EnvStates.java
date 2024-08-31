package org.chubby.morefurniturez.common.blockentities.enums;

public enum EnvStates
{
    DAY(90,100),
    THUNDER(50,60),
    RAIN(60,65),
    CLOUDY(70,80),
    NIGHT(1,5);

    private final int min;
    private final int max;

    EnvStates(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
