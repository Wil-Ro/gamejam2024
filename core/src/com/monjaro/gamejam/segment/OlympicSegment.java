package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.Die;

import java.util.List;

public class OlympicSegment extends Segment {

	private final int length;

	public OlympicSegment(int length) {
		this.length = length;

		name = switch (length) {
			case 1 -> "Any";
			case 2 -> "Stumble";
			case 3 -> "Hop";
			case 4 -> "Skip";
			case 5 -> "Jump";
			default -> "Mach " + (length - 5);
		};
	}

	@Override
	public boolean isDestroyedBy(List<Die> dice) {
		List<Integer> values = dice.stream()
				.filter(d -> !d.isFaceBlank())
				.map(Die::getFaceValue)
				.distinct()
				.sorted()
				.toList();

		int last = -1, run = 1, best = run;

		for (int value : values) {
			if (value == last + 1) {
				if (++run > best) best = run;
			} else {
				run = 1;
			}

			last = value;
		}

		return best >= length;
	}

}