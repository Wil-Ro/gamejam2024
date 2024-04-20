package com.monjaro.gamejam;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Die extends Actor {

	private Rectangle shape;
	/*
	  0
	1 2 3 4
	  5
	 */
	private Face[] faces = new Face[6];

	public Die() {
		int[] pips = {4, 6, 5, 1, 2, 3};
		for (int i = 0; i < faces.length; i++) {
			faces[i] = new Face(pips[i]);
		}
		shape = new Rectangle();
	}

	public void setPosition(float x, float y){
		shape.setX(x);
		shape.setY(y);
	}

	public void setSize(float w, float h){
		shape.setSize(w, h);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(SpriteBatch batch) {
		for (Face face : faces) {
			face.render(batch);
		}
	}
}
