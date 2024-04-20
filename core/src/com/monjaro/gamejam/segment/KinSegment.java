package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.Die;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KinSegment extends Segment { //multiple dice of the same value

	private final int requirement;

	public KinSegment(int requirement) {
		this.requirement = requirement;

		name = switch (requirement) {
			case 1 -> "Any";
			case 2 -> "Pair";
			case 3 -> "Trio";
			case 4 -> "Quartet";
			case 5 -> "Quintet";
			default -> requirement + " of a kind";
		};
	}

	@Override
	public boolean destroyedBy(List<Die> dice) {
		Map<Integer, Integer> counts = new HashMap<>();
		for (Die die : dice) {
			int count = counts.getOrDefault(die.getFaceValue(), 1) + 1;

			if (count >= requirement) return true;

			counts.put(die.getFaceValue(), count);
		}

		return false;
	}

}
