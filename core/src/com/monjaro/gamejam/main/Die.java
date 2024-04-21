package com.monjaro.gamejam.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.Random;

public class Die extends Actor {

	private final Game game;
	private final Transform transform;

	/*
	  0
	1 2 3 4
	  5
	 */
	private final Face[] faces = new Face[6];
	private int faceIndex = 3;
	private boolean selected = false;

	private static Texture lockedSprite;

	private final Random random = new Random(); //TODO use central random

	public Die(Game game, float x, float y, float width, float height) {
		this.game = game;

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
		faceIndex = random.nextInt(6);
	}

	public static void setLockedSprite(Texture sprite){
		lockedSprite = sprite;
	}

	public void decay() { //remove a pip from all faces of this die
		for (Face face : faces) {
			List<Face.Pip> pips = face.getPips();
			if (pips.isEmpty()) continue;
			Face.Pip decayed = pips.get(random.nextInt(pips.size()));
			face.removePip(decayed);

			game.addFallingPip(new FallingPip(new Transform(transform.getX(), transform.getY(), 0, 0), face.equals(getFace())));
		}
	}

	public Face getFace() {
		return faces[faceIndex];
	}

	public int getFaceValue() {
		return getFace().getValue();
	}

	public boolean isFaceBlank() {
		return getFaceValue() <= 0;
	}

	public void setFace(Face face) {
		faces[faceIndex] = face;
		face.setTransform(transform);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (selected != this.selected)
		{
			if (selected) {
				transform.y += 64;
				transform.rotation = 20;
			}
			else{
				transform.y -= 64;
				transform.rotation = 0;
			}
		} // terrible

		this.selected = selected;
	}

}
