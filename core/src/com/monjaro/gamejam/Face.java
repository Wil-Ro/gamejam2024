package com.monjaro.gamejam;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Face extends Actor{

	private Rectangle shape;

	private final List<Pip> pips = new ArrayList<>();

	public Face(int pipCount) {
		//ro adds pips here
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

		private final double x, y;

		public Pip(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
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

	}

}
