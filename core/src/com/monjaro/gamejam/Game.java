package com.monjaro.gamejam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.monjaro.gamejam.segment.DualSegment;
import com.monjaro.gamejam.segment.KinSegment;
import com.monjaro.gamejam.segment.Segment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game extends ApplicationAdapter {

	private final Set<Actor> actors = new HashSet<>();

	private final List<Die> dice = new ArrayList<>();
	private final List<Segment> segments = new ArrayList<>();

	private SpriteBatch batch;
	private BitmapFont font;
	private Texture img;

	private final static int TICKS_PER_SECOND = 60;
	private double tickProgress = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().markupEnabled = true;
		img = new Texture("badlogic.jpg");

		Face.setBlankFaceSprite(new Texture("blank_die_face.png"));
		Face.setPipSprite(new Texture("pip.png"));

		for (int i = 1; i <= 5; i++) {
			dice.add(new Die((i*80), 20, 64, 64));
		}

		for (int i = 1; i <= 5; i++) {
			segments.add(new KinSegment(i));
		}
		segments.add(new DualSegment(false));
		segments.add(new DualSegment(true));
	}

	public void tick() {
		processInput();

		actors.forEach(Actor::tick);
	}

	public void processInput() {
		Input input = Gdx.input;

		if (input.isKeyJustPressed(Input.Keys.R)) { //reroll dice that aren't locked
			dice.stream().filter(d -> !d.isLocked()).forEach(Die::roll);

			System.out.println("=".repeat(100));
			for (Segment segment : segments) {
				System.out.println(segment.getName() + ": " + segment.isDestroyedBy(dice));
			}
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
		for (Die die : dice) {
			die.render(batch);
		}

		int x = 50;
		for (Segment segment : segments) {
			String prefix = "[#9E65A8]";
			if (segment.isDestroyed()) prefix = "[#EBE5EC]";
			else if (segment.isDestroyedBy(dice)) prefix = "[#528154]";

			font.draw(batch, prefix + segment.getName(), x += 75, Gdx.graphics.getHeight() - 100);
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
