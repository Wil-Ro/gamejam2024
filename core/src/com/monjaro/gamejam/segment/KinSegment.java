package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.Die;

import java.util.List;
import java.util.Map;

public class KinSegment extends Segment { //multiple dice of the same value

	private final int requirement;

	public KinSegment(int requirement) {
		this.requirement = requirement;

		name = switch (requirement) {
			case 1 -> "Any";
			case 2 -> "Duo";
			case 3 -> "Trio";
			case 4 -> "Quartet";
			case 5 -> "Quintet";
			case 6 -> "What?";
			default -> requirement + " of a kind";
		};
	}

	@Override
	public boolean isDestroyedBy(List<Die> dice) {
		Map<Integer, Integer> counts = countValues(dice);

		for (int count : counts.values()) {
			if (count >= requirement) {
				return true;
			}
		}

		return false;
	}

}