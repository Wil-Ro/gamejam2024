package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.Die;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Segment {

	private boolean destroyed = false;

	protected String name;

	public void destroy() {
		destroyed = true;
	}

	public abstract boolean isDestroyedBy(List<Die> dice);

	protected Map<Integer, Integer> countValues(List<Die> dice) {
		Map<Integer, Integer> counts = new HashMap<>();

		for (Die die : dice) {
			if (die.isFaceBlank()) continue;

			int count = counts.getOrDefault(die.getFaceValue(), 0) + 1;
			counts.put(die.getFaceValue(), count);
		}

		return counts;
	}

	public String getName() {
		return name;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

}
