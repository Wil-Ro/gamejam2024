package com.monjaro.gamejam;

import com.badlogic.gdx.math.Rectangle;

public class Transform extends Rectangle {
    float rotation;

    public Transform(float x, float y, float width, float height){
        super(x, y, width, height);
    }

    public Transform(){
        super();
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
