package com.monjaro.gamejam.main;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Actor {

	public abstract void tick();

	public abstract void render(SpriteBatch batch);

}
