package com.monjaro.gamejam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Die extends Actor {

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
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(SpriteBatch batch) {

	}

	public void roll() {

	}

	public void decay() {

	}

}
