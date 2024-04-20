package com.monjaro.gamejam;

public class Face {

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

}
