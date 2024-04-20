package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.Die;

import java.util.List;

public abstract class Segment {

	private boolean destroyed = false;

	protected String name;

	public void destroy() {
		destroyed = true;
	}

	public abstract boolean destroyedBy(List<Die> die);

	public String getName() {
		return name;
	}

}
