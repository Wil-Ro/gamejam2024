package com.monjaro.gamejam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.monjaro.gamejam.segment.DualSegment;
import com.monjaro.gamejam.segment.KinSegment;
import com.monjaro.gamejam.segment.Segment;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {

	private final List<Die> dice = new ArrayList<>();
	private final List<Segment> segments = new ArrayList<>();

	private SpriteBatch batch;
	private BitmapFont font;
	private Texture img;

	private final static int TICKS_PER_SECOND = 60;
	private double tickProgress = 0;


	private RoundData roundData;
	private UI ui;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().markupEnabled = true;
		img = new Texture("badlogic.jpg");

		ui = new UI(50, 280, 10);
		roundData = new RoundData(10);

		Face.setBlankFaceSprite(new Texture("blank_die_face.png"));
		Face.setPipSprite(new Texture("pip.png"));
		Die.setLockedSprite(new Texture("locked_die_border.png"));
		UI.setRerollTexture(new Texture("reroll_symbol.png"));

		Vector2 dieSize = new Vector2();
		float divide = Gdx.graphics.getWidth() / 6f;
		for (int i = 0; i < 5; i++) {
			dice.add(new Die(divide * (i + 1), 350, 64, 64));
		}

		for (int i = 1; i <= 5; i++) {
			segments.add(new KinSegment(i));
		}
		segments.add(new DualSegment(false));
		segments.add(new DualSegment(true));
	}

	public void tick() {
		processInput();

		ui.setRemainingRerolls(roundData.getRerolls());
	}

	public void processInput() {
		Input input = Gdx.input;

		if (input.isKeyJustPressed(Input.Keys.R) && roundData.getRerolls() > 0) { //reroll dice that aren't locked
			dice.stream().filter(d -> !d.isSelected()).forEach(Die::roll);
			roundData.reduceRerolls(1);

			System.out.println("=".repeat(100));
			for (Segment segment : segments) {
				System.out.println(segment.getName() + ": " + segment.isDestroyedBy(dice));
			}
		}

		for (int i = 0; i < dice.size(); i++) { //lock dice, iterating over for each keycode
			Die die = dice.get(i); //die iterator is looking at
			int keyCode = Input.Keys.NUM_1 + i; //keycode for the current die, 1, 2...9, 0 on keyboard

			if (input.isKeyJustPressed(keyCode)) { //if key corresponding to die has been pressed
				die.setSelected(!die.isSelected()); //flip lock state
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

		//TODO debug
		for (Die die : dice) {
			die.render(batch);
		}

		int y = Gdx.graphics.getHeight() - 50;
		for (Segment segment : segments) {
			String prefix = "[#9E65A8]";
			if (segment.isDestroyed()) prefix = "[#EBE5EC]";
			else if (segment.isDestroyedBy(getSelectedDice())) prefix = "[#528154]";

			font.draw(batch, prefix + segment.getName(), 100, y -= 20);
		}
		//-----

		ui.render(batch);

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

}
