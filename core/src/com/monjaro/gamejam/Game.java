package com.monjaro.gamejam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture img;

	private static int TICKS_PER_SECOND;
	private double tickProgress = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	public void tick() {

	}

	@Override
	public void render() {
		Gdx.graphics.getDeltaTime();
		while (tickProgress >= 1) { //tick as many times as needed
			tick();
			tickProgress--;
		}

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

}
