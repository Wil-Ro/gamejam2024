package com.monjaro.gamejam;

public class RoundData {
    private int rerolls;

    public RoundData(int rerolls){
        this.rerolls = rerolls;
    }

    public int getRerolls() {
        return rerolls;
    }

    public void setRerolls(int rerolls) {
        this.rerolls = rerolls;
    }

    public void reduceRerolls(int i) {
        rerolls -= i;
    }
}
