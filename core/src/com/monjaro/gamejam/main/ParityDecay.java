package com.monjaro.gamejam.main;

import java.util.List;

public class ParityDecay extends Decay {

	private final int parity;

	public ParityDecay(boolean odd) {
		parity = odd ? 1 : 0;
		description = "All used dice with " + (odd ? "ODD" : "EVEN") + " value decay.";
	}

	@Override
	public List<Die> getDecayed(List<Die> dice) {
		return dice.stream()
				.filter(d -> !d.isFaceBlank())
				.filter(d -> d.getFaceValue() % 2 == parity)
				.toList();
	}

}
