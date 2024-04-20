package com.monjaro.gamejam;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Face extends Actor{

	private Transform transform;

	private final List<Pip> pips = new ArrayList<>();

	private static Texture blankFaceSprite;
	private static Texture pipSprite;


	public Face(int pipCount, Transform transform) {
		addPipsForValue(pipCount);
		this.transform = transform;
	}

	private void addPipsForValue(int value){
		int[][] positions = {{-25, -25}, {25, 25}, {-25, 25}, {25, -25}, {-25, 0}, {25, 0}};

		if (value % 2 == 1) {
			pips.add(new Pip(0, 0));
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
			Random rand = new Random();
			int range = 2;
			location = new Vector2(x + rand.nextInt(-range, range + 1), y+ rand.nextInt(-range, range + 1));
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
		transform.setX(x);
		transform.setY(y);
	}

	public void setSize(float w, float h){
		transform.setSize(w, h);
	}

	public static void setBlankFaceSprite(Texture sprite){
		blankFaceSprite = sprite;
	}

	public static void setPipSprite(Texture sprite){
		pipSprite = sprite;
	}

	public Vector2 calculatePipLocation(Vector2 percentages) {
		double radians = Math.toRadians(transform.rotation);

		float x = (float) (percentages.x * Math.cos(radians) - percentages.y * Math.sin(radians));
		float y = (float) (percentages.x * Math.sin(radians) + percentages.y * Math.cos(radians));

        return new Vector2(
				transform.x + transform.width*x/100f - (float)pipSprite.getWidth()/2,
				transform.y + transform.height*y/100f - (float)pipSprite.getHeight()/2);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(SpriteBatch batch) {
		Sprite face = new Sprite(blankFaceSprite);
		face.setOrigin(face.getWidth()/2, face.getHeight()/2);
		face.rotate(transform.getRotation());
		face.setPosition(transform.x-face.getWidth()/2, transform.y-face.getHeight()/2);
		face.draw(batch);

		for(Pip pip : pips){
			Vector2 position = calculatePipLocation(pip.getVectorLocation());
			batch.draw(pipSprite,
					position.x,
					position.y);
		}
	}

}
