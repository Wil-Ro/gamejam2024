package com.monjaro.gamejam.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.monjaro.gamejam.SegmentUI;
import com.monjaro.gamejam.segment.DualSegment;
import com.monjaro.gamejam.segment.KinSegment;
import com.monjaro.gamejam.segment.OlympicSegment;
import com.monjaro.gamejam.segment.Segment;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {

	private final List<Die> dice = new ArrayList<>();

	private SpriteBatch batch;
	private BitmapFont font;
	private Texture img;

	private final static int TICKS_PER_SECOND = 60;
	private double tickProgress = 0;

	private Round round;
	private UI ui;
	private SegmentUI segUi;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().markupEnabled = true;
		img = new Texture("badlogic.jpg");

		segUi = new SegmentUI();

		ui = new UI(this, 50, 280);
		round = new Round(List.of(new OlympicSegment(1), new OlympicSegment(3), new KinSegment(3), new DualSegment(false)), List.of(new ParityDecay(true)), 5);

		Face.setBlankFaceSprite(new Texture("blank_die_face.png"));
		Face.setPipSprite(new Texture("pip.png"));
		Die.setLockedSprite(new Texture("locked_die_border.png"));
		UI.setRerollTexture(new Texture("reroll_symbol.png"));

		float divide = Gdx.graphics.getWidth() / 6f;
		for (int i = 0; i < 5; i++) {
			dice.add(new Die(divide * (i + 1), 350, 64, 64));
		}
	}

	public void tick() {
		processInput();

		ui.setRerolls(round.getRerolls());
	}

	public void processInput() {
		Input input = Gdx.input;

		if (input.isKeyJustPressed(Input.Keys.R) && round.getRerolls() > 0) { //reroll dice that aren't locked
			reroll();
			round.reduceRerolls(0);
		}

		if (input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			for (int i = 0; i < round.getSegments().size(); i++) {
				int keyCode = Input.Keys.NUM_1 + i; //keycode for the current segment, shift + 1, 2...9, 0 on keyboard

				if (input.isKeyJustPressed(keyCode)) {
					Segment segment = round.getSegments().get(i);

					if (segment.isDestroyedBy(getSelectedDice())) { //if can be destroyed with selected
						segment.destroy();
						round.getDecays().forEach(d -> d.getDecayed(getSelectedDice()).forEach(Die::decay)); //apply all decay rules
						dice.forEach(d -> d.setSelected(false));
//						reroll();
					}
				}
			}
		} else {
			for (int i = 0; i < dice.size(); i++) { //lock dice, iterating over for each keycode
				int keyCode = Input.Keys.NUM_1 + i; //keycode for the current die, 1, 2...9, 0 on keyboard

				if (input.isKeyJustPressed(keyCode)) { //if key corresponding to die has been pressed
					Die die = dice.get(i); //die iterator is looking at
					die.setSelected(!die.isSelected()); //flip lock state
				}
			}
		}
	}

	private void reroll() {
		dice.stream().filter(d -> !d.isSelected()).forEach(Die::roll);
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

		//TODO debug
		for (Die die : dice) {
			die.render(batch);
		}

		int y = Gdx.graphics.getHeight() - 50;
		for (Segment segment : round.getSegments()) {
			String prefix = "[#9E65A8]";
			if (segment.isDestroyed()) prefix = "[#EBE5EC]";
			else if (segment.isDestroyedBy(getSelectedDice())) prefix = "[#528154]";

			font.draw(batch, prefix + segment.getName(), 100, y -= 20);
		}
		y -= 50;
		for (Decay decay : round.getDecays()) {
			font.draw(batch, "[#9E65A8]" + decay.getDescription(), 100, y -= 20);
		}
		//-----

		ui.render(batch);

		segUi.render(batch);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	public List<Die> getSelectedDice() {
		return dice.stream()
				.filter(Die::isSelected)
				.toList();
	}

	public Round getRound() {
		return round;
	}

}
