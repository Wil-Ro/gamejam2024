package com.monjaro.gamejam.main;

import java.util.List;

public abstract class Decay {

	protected String description;

	public abstract List<Die> getDecayed(List<Die> dice);

	public String getDescription() {
		return description;
	}

}
