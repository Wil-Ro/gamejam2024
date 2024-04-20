package com.monjaro.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Face extends Actor{

	private Rectangle shape;

	private final List<Pip> pips = new ArrayList<>();

	private static Texture blankFaceSprite;
	private static Texture pipSprite;

	public Face(int pipCount) {
		addPipsForValue(pipCount);
	}

	private void addPipsForValue(int value){
		int[][] positions = {{25, 25}, {75, 75}, {25, 75}, {75, 25}, {25, 50}, {75, 50}};

		if (value % 2 == 1) {
			pips.add(new Pip(50, 50));
			value--;
		}

		for (int i = 0; i < value ; i++) {
			pips.add(new Pip(positions[i][0], positions[i][1]));
		}
	}

	public int getValue() {
		return pips.size();
	}

	public List<Pip> getPips() {
		return Collections.unmodifiableList(pips);
	}

	public void removePip(Pip pip) {
		pips.remove(pip);
	}

	public static class Pip {

		private final float x, y;

		public Pip(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

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
		batch.draw(blankFaceSprite, shape.x, shape.y, shape.width, shape.height);
		for(Pip pip : pips){
			batch.draw(pipSprite,
					shape.x + (shape.width*pip.x/100f),
					shape.y + (shape.width*pip.y/100f));
		}
	}

}
