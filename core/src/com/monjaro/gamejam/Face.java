package com.monjaro.gamejam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Face {

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

}
