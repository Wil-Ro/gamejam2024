package com.monjaro.gamejam;

import com.badlogic.gdx.math.Rectangle;

public class Face extends Actor{

	private Rectangle shape;

	private int pips;

	public Face(int pips) {
		this.pips = pips;
	}

	public int getPips() {
		return pips;
	}

	public void setPips(int pips) {
		this.pips = pips;
	}

	private static class Pip {

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
	public void render() {

	}

}
