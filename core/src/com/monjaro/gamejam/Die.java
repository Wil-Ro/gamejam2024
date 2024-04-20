package com.monjaro.gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

public class Die extends Actor {

	private final Rectangle shape;

	/*
	  0
	1 2 3 4
	  5
	 */
	private final Face[] faces = new Face[6];
	private int faceIndex = 3;
	private boolean locked = false;

	private final Random random = new Random(); //TODO use central random

	public Die(float x, float y, float width, float height) {
		int[] pips = {4, 6, 5, 1, 2, 3};
		for (int i = 0; i < faces.length; i++) {
			faces[i] = new Face(pips[i]);
			faces[i].setPosition(x, y);
			faces[i].setSize(width, height);
		}
		shape = new Rectangle(x, y, width, height);

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
		faces[faceIndex].render(batch);
	}

	public void roll() {
		faceIndex = random.nextInt(6);
	}

	public void decay() { //remove a pip from all faces of this die
		for (Face face : faces) {
			List<Face.Pip> pips = face.getPips();
			Face.Pip decayed = pips.get(random.nextInt());
			face.removePip(decayed);
		}
	}

	public Face getFace() {
		return faces[faceIndex];
	}

	public int getFaceValue() {
		return getFace().getValue();
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}
