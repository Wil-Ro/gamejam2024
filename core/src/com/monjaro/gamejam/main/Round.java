package com.monjaro.gamejam.main;

import com.monjaro.gamejam.segment.Segment;

import java.util.List;

public class Round {

    private final List<Segment> segments;
    private int rerolls;

    public Round(List<Segment> segments, int rerolls){
        this.rerolls = rerolls;
        this.segments = segments;
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
