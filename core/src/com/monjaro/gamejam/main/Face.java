package com.monjaro.gamejam.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	int faceNumber;

	public Face(int pipCount) {
		addPipsForValue(pipCount);
		Random rand = new Random();
		faceNumber = rand.nextInt(0, 5);
	}

	public Face(int pipCount, Transform transform) {
		addPipsForValue(pipCount);
		this.transform = transform;
		Random rand = new Random();
		faceNumber = rand.nextInt(0, 5);
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

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public static Texture getPipSprite() {
		return pipSprite;
	}

	public static void setBlankFaceSprite(Texture sprite){
		blankFaceSprite = sprite;
	}

	public static void setPipSprite(Texture sprite){
		pipSprite = sprite;
	}

	public Transform getTransform() {
		return transform;
	}

	public Vector2 calculatePipLocation(Vector2 percentages) {
		double radians = Math.toRadians(transform.rotation);

		float x = (float) (percentages.x * Math.cos(radians) - percentages.y * Math.sin(radians));
		float y = (float) (percentages.x * Math.sin(radians) + percentages.y * Math.cos(radians));

        return new Vector2(
				transform.x + transform.width*x/100f - (float)pipSprite.getWidth()/2,
				transform.y + transform.height*y/100f - (float)pipSprite.getHeight()/2);
	}

	public Vector2 calculatePipLocation(Vector2 percentages, Rectangle rect) {
		return new Vector2(
				rect.x + rect.width/100f - (float)pipSprite.getWidth()/2,
				rect.y + rect.height/100f - (float)pipSprite.getHeight()/2);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(SpriteBatch batch) {
		Sprite face = new Sprite(blankFaceSprite, 64*faceNumber, 0, 64, 64);
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

	public void renderAtRect(SpriteBatch batch, Rectangle rect) {
		Sprite face = new Sprite(blankFaceSprite, 64*faceNumber, 0, (int)rect.width, (int)rect.height);
		face.setOrigin(rect.getWidth()/2, rect.getHeight()/2);
		//face.rotate(transform.getRotation());
		face.setPosition(rect.x-rect.getWidth()/2, rect.y-rect.getHeight()/2);

		face.draw(batch);

		for(Pip pip : pips){
			Vector2 position = calculatePipLocation(pip.getVectorLocation(), rect);
			batch.draw(pipSprite,
					position.x,
					position.y);
		}
	}

}
