package com.monjaro.gamejam.main;

import com.monjaro.gamejam.segment.Segment;

import java.util.List;

public class Round {

    private final List<Segment> segments;
    private final List<Decay> decays;
    private final int maxRerolls;
    private int rerolls;

    public Round(List<Segment> segments, List<Decay> decays, int rerolls) {
        this.segments = segments;
        this.decays = decays;
        maxRerolls = rerolls;
        this.rerolls = rerolls;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public List<Decay> getDecays() {
        return decays;
    }

    public int getMaxRerolls() {
        return maxRerolls;
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
