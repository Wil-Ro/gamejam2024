package com.monjaro.gamejam;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Face extends Actor{

	private Rectangle shape = new Rectangle();

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

		private final Vector2 location;

		public Pip(float x, float y) {
			location = new Vector2(x, y);
		}

		public float getX() {
			return location.x;
		}

		public float getY() {
			return location.y;
		}

		public Vector2 getVectorLocation(){return location;}

	}

	public void setPosition(float x, float y){
		shape.setX(x);
		shape.setY(y);
	}

	public void setSize(float w, float h){
		shape.setSize(w, h);
	}

	public static void setBlankFaceSprite(Texture sprite){
		blankFaceSprite = sprite;
	}

	public static void setPipSprite(Texture sprite){
		pipSprite = sprite;
	}

	public Vector2 getPipLocationFromPercentage(Vector2 percentages)
	{
		Vector2 position = new Vector2(
				shape.x + (shape.width*percentages.x/100f) - (float)pipSprite.getWidth()/2,
				shape.y + shape.width*percentages.y/100f - (float)pipSprite.getHeight()/2);

		return position;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(blankFaceSprite, shape.x, shape.y, shape.width, shape.height);
		for(Pip pip : pips){
			Vector2 position = getPipLocationFromPercentage(pip.getVectorLocation());
			batch.draw(pipSprite,
					position.x,
					position.y);
		}
	}

}
