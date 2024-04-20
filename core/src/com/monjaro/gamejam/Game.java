package com.monjaro.gamejam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.vm.ci.hotspot.JFR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game extends ApplicationAdapter {

	private final Set<Actor> actors = new HashSet<>();

	private SpriteBatch batch;
	private Texture img;

	private final static int TICKS_PER_SECOND = 60;
	private double tickProgress = 0;
	

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	public void tick() {
		actors.forEach(Actor::tick);
	}

	@Override
	public void render() {
		tickProgress += Gdx.graphics.getDeltaTime() / TICKS_PER_SECOND;
		while (tickProgress >= 1) { //tick as many times as needed
			tick();
			tickProgress--;
		}

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		actors.forEach(a -> a.render(batch));

		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	private void addActor(Actor actor) {
		actors.add(actor);
	}

	private void removeActor(Actor actor) {
		actors.remove(actor);
	}

}
