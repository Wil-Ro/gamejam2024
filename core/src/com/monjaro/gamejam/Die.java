package com.monjaro.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Random;

public class Die extends Actor {

	private Transform transform;
	private int rotation;
	/*
	  0
	1 2 3 4
	  5
	 */

	private final Face[] faces = new Face[6];
	private int faceIndex = 3;
	private boolean locked = false;

	private static Texture lockedSprite;

	private final Random random = new Random(); //TODO use central random

	public Die() {
		transform = new Transform();

		int[] pips = {4, 6, 5, 1, 2, 3};
		for (int i = 0; i < faces.length; i++) {
			faces[i] = new Face(pips[i], transform);
		}
	}

	public Die(float x, float y, float width, float height) {
		transform = new Transform(x, y, width, height);

		int[] pips = {4, 6, 5, 1, 2, 3};
		for (int i = 0; i < faces.length; i++) {
			faces[i] = new Face(pips[i], transform);
		}
	}

	public void setPosition(float x, float y){
		transform.setX(x);
		transform.setY(y);
	}

	public void setSize(float w, float h){
		transform.setSize(w, h);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(SpriteBatch batch) {
		faces[faceIndex].render(batch);
	}

	public void roll() {
		if (!locked) {
			faceIndex = random.nextInt(6);
		}
	}

	public static void setLockedSprite(Texture sprite){
		lockedSprite = sprite;
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
		if (locked != this.locked)
		{
			if (locked) {
				transform.y += 64;
				transform.rotation = 20;
			}
			else{
				transform.y -= 64;
				transform.rotation = 0;
			}
		} // terrible

		this.locked = locked;
	}

}
