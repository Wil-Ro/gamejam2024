package com.monjaro.gamejam.segment;

import com.monjaro.gamejam.main.Decay;
import com.monjaro.gamejam.main.Die;

import java.util.ArrayList;
import java.util.List;

public class TallDecay extends Decay {

	public TallDecay() {
		description = "All used dice with HIGHEST value decay.";
	}

	@Override
	public List<Die> getDecayed(List<Die> dice) {
		int highestValue = -1;
		List<Die> targets = new ArrayList<>();

		for (Die die : dice) {
			if (die.isFaceBlank()) continue;

			if (die.getFaceValue() < highestValue) continue;

			targets.add(die);
			highestValue = die.getFaceValue();
		}

		return targets;
	}

}
