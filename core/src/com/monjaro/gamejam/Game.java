package com.monjaro.gamejam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game extends ApplicationAdapter {

	private final Set<Actor> actors = new HashSet<>();

	private final List<Die> dice = new ArrayList<>();

	private SpriteBatch batch;
	private BitmapFont font;
	private Texture img;

	private final static int TICKS_PER_SECOND = 60;
	private double tickProgress = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		img = new Texture("badlogic.jpg");

		Face.setBlankFaceSprite(new Texture("blank_die_face.png"));
		Face.setPipSprite(new Texture("pip.png"));
		Die.setLockedSprite(new Texture("locked_die_border.png"));

		Vector2 dieSize = new Vector2();
		float divide = Gdx.graphics.getWidth() / 6f;
		for (int i = 0; i < 5; i++) {
			dice.add(new Die(divide * (i + 1), 350, 64, 64));
		}
	}

	public void tick() {
		processInput();

		actors.forEach(Actor::tick);
	}

	public void processInput() {
		Input input = Gdx.input;

		if (input.isKeyJustPressed(Input.Keys.R)) { //reroll dice
			dice.forEach(Die::roll);
		}

		for (int i = 0; i < dice.size(); i++) { //lock dice, iterating over for each keycode
			Die die = dice.get(i); //die iterator is looking at
			int keyCode = Input.Keys.NUM_1 + i; //keycode for the current die, 1, 2...9, 0 on keyboard

			if (input.isKeyJustPressed(keyCode)) { //if key corresponding to die has been pressed
				die.setLocked(!die.isLocked()); //flip lock state
			}
		}
	}

	@Override
	public void render() {
		tickProgress += Gdx.graphics.getDeltaTime() * TICKS_PER_SECOND;
		while (tickProgress >= 1) { //tick as many times as needed
			tick();
			tickProgress--;
		}

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		actors.forEach(a -> a.render(batch));

		//TODO debug
		int x = 100;
		for (Die die : dice) {
			die.render(batch);
		}
		//-----

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
