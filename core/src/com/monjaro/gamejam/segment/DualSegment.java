package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.main.Die;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DualSegment extends Segment {

	private final boolean firstTrio;

	public DualSegment(boolean firstTrio) { //first is a trio or duo, second is a duo
		this.firstTrio = firstTrio;

		if (firstTrio) {
			Random random = new Random();

			if (random.nextInt(10) != 0) {
				name = "Full House";
			} else {
				String[] names = new String[]{
						"Complete Household",
						"Entire Residence",
						"Occupied Home",
						"Family Residence",
						"Filled Dwelling",
						"Packed Abode",
						"Crowded Domicile",
						"Busy Household",
						"Well-occupied Home",
						"Houseful"
				}; //thank you ChatGPT for alternate names
				name = names[random.nextInt(names.length)];
			}
		} else {
			name = "Dual Duo";
		}
	}

	@Override
	public boolean isDestroyedBy(List<Die> dice) {
		Map<Integer, Integer> counts = countValues(dice);

		Map<Integer, Integer> countCounts = new HashMap<>(); //count of counts
		for (int count : counts.values()) {
			for (int i = count; i >= 1; i--) { //add from count to 1 - this is so a trio is also a pair, e.g. 11122 is a dual duo
				int countCount = countCounts.getOrDefault(i, 0) + 1;
				countCounts.put(i, countCount);
			}
		}

		return countCounts.getOrDefault(2, 0) >= 2
				&& (!firstTrio || countCounts.getOrDefault(3, 0) >= 1);
	}

	@Override
	public int getSpriteColumn() {
		return 2;
	}

	@Override
	public int getSpriteRow() {
		return firstTrio ? 1 : 0;
	}
}