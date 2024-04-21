package com.monjaro.gamejam.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.monjaro.gamejam.SegmentUI;
import com.monjaro.gamejam.segment.*;

import java.util.*;

import static com.badlogic.gdx.graphics.GL20.*;

public class Game extends ApplicationAdapter {

	private final List<Die> dice = new ArrayList<>();

	private SpriteBatch batch;
	private BitmapFont font;
	private Texture img;

	private final static int TICKS_PER_SECOND = 60;
	private double tickProgress = 0;

	private Round round;
	private int roundNumber = 0;
	private final List<Face> shope = new ArrayList<>();

	private final List<FallingPip> fallingPips = new ArrayList<>();

	private UI ui;
	private SegmentUI segUi;
	private ShopeUi shopeUi;
	private DieNetUi dieNet;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		segUi = new SegmentUI(new Rectangle(0, (Gdx.graphics.getHeight()/3)*2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3));
		segUi.setGame(this);

		ui = new UI(this, 50, 280);

		dieNet = new DieNetUi();
		dieNet.setGame(this);

		shopeUi = new ShopeUi();
		ShopeUi.setGame(this);

		Face.setBlankFaceSprite(new Texture("blank_die_faces_sheet.png"));

		Face.setPipSprite(new Texture("pip.png"));
		Die.setLockedSprite(new Texture("locked_die_border.png"));
		UI.setRerollTexture(new Texture("reroll_symbol.png"));
		SegmentUI.setCriteriaSheet(new Texture("criteria.png"));
		ShopeUi.setBacking(new Texture("shope.png"));
		ShopeUi.setFaceBacking(new Texture("shope_die_border.png"));
		ShopeUi.setBanner(new Texture("shope_banner.png"));
		// SegmentUI.setCriteriaSheet(""); not made yet

		// setting up font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/single_day_regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 20;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!s
		font.getData().markupEnabled = true;

		float divide = Gdx.graphics.getWidth() / 6f;
		for (int i = 0; i < 5; i++) {
			dice.add(new Die(this, divide * (i + 1), 350, 64, 64));
		}

		reroll();
		nextRound();
	}

	public void tick() {
		processInput();

		fallingPips.forEach(Actor::tick);
		fallingPips.removeIf(FallingPip::isOffScreen);
	}

	public void processInput() {
		Input input = Gdx.input;

		if (input.isKeyJustPressed(Input.Keys.R) && round.getRerolls() > 0) { //reroll dice that aren't locked
			reroll();
			round.reduceRerolls(1);
		}

		dieNet.setVisible(input.isKeyPressed(Input.Keys.CONTROL_LEFT));

		if (input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			for (int i = 0; i < round.getSegments().size(); i++) {
				int keyCode = Input.Keys.NUM_1 + i; //keycode for the current segment, shift + 1, 2...9, 0 on keyboard

				if (input.isKeyJustPressed(keyCode)) {
					Segment segment = round.getSegments().get(i);

					if (!segment.isDestroyed() && segment.isDestroyedBy(getSelectedDice())) { //if can be destroyed with selected
						segment.destroy();

						round.getDecays().forEach(d -> d.getDecayed(getSelectedDice()).forEach(Die::decay)); //apply all decay rules

						dice.forEach(d -> d.setSelected(false)); //unselect all dice
						reroll(); //reroll

						if (round.getSegments().stream().allMatch(Segment::isDestroyed)) { //if all segments are destroyed, next round
							nextRound();
						}
					}
				}
			}
		} else {
			int shopeIndex = -1;
			if (input.isKeyJustPressed(Input.Keys.I)) shopeIndex = 0;
			else if (input.isKeyJustPressed(Input.Keys.O)) shopeIndex = 1;
			else if (input.isKeyJustPressed(Input.Keys.P)) shopeIndex = 2;

			List<Die> selected = getSelectedDice();
			if (selected.size() == 1 && shopeIndex != -1) {
				Face face = getShopeIndex(shopeIndex);

				if (face != null) {
					Die die = selected.get(0);
					die.setFace(face);
					removeShopeIndex(shopeIndex);
				}
			}

			for (int i = 0; i < dice.size(); i++) { //buy for or select dice, iterating over for each keycode
				int keyCode = Input.Keys.NUM_1 + i; //keycode for the current die, 1, 2...9, 0 on keyboard

				if (!input.isKeyJustPressed(keyCode)) continue;

				Die die = dice.get(i); //die iterator is looking at
				die.setSelected(!die.isSelected()); //flip lock state
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

		for (Die die : dice) {
			die.render(batch);
		}

		for (FallingPip pip : fallingPips) { //on top
			pip.render(batch);
		}

		int y = Gdx.graphics.getHeight() / 3 * 2 - 25;
		for (Decay decay : round.getDecays()) {
			font.draw(batch, "[#9E65A8]" + decay.getDescription(), 100, y -= 20);
		}

		ui.render(batch);

		segUi.render(batch);

		shopeUi.render(batch);

		dieNet.render(batch);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	public void nextRound() {
		round = generateRound(++roundNumber);

		generateShope();
	}

	public Round generateRound(int difficulty) {
		Random random = new Random();
		int points = 2 + difficulty * 3;

		List<Segment> segments = new ArrayList<>();
		List<Decay> decays = new ArrayList<>();

		List<Segment> pool = new ArrayList<>();
		for (int i = 2; i <= 5; i++) {
			for (int j = i; j <= 5; j++) {
				pool.add(new KinSegment(i));
				pool.add(new OlympicSegment(i));
			}
		}
		for (boolean b : new boolean[] {true, false}) pool.add(new DualSegment(b));

		pool.sort(Comparator.comparingInt(Segment::getCost).reversed());

		for (Segment segment : pool) {
			if (random.nextFloat() > (float) segment.getCost() / points) {
				segments.add(segment);
				points -= segment.getCost();
			}
		}

		decays.add(switch (random.nextInt(3)) {
			case 0 -> new ParityDecay(false);
			case 1 -> new ParityDecay(true);
			default -> new TallDecay();
		});

		return new Round(segments, decays, 5);
	}

	public List<Die> getSelectedDice() {
		return dice.stream()
				.filter(Die::isSelected)
				.toList();
	}

	public Round getRound() {
		return round;
	}

	private void generateShope() {
		Random random = new Random();
		shope.clear();

		int x = 64, y = 64;
		while (shope.size() < 3) {
			Face ware = new Face(1 + random.nextInt(6));//TODO RO FIX plz i beg!!!!
			shope.add(ware);
		}
	}

	public Face getShopeIndex(int index) {
		return shope.get(index);
	}

	public void removeShopeIndex(int index) {
		shope.remove(index);
		shope.add(index, null);
	}

	public List<Face> getShope() {
		return Collections.unmodifiableList(shope);
	}

	public void addFallingPip(FallingPip pip) {
		fallingPips.add(pip);
	}

}
