package com.monjaro.gamejam.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class FallingPip extends Actor {

	private final Transform transform;
	private final Vector2 velocity;
	private final float rotationalVelocity;

	public FallingPip(Transform transform) {
		Random random = new Random();

		this.transform = transform;
		velocity = new Vector2(-1 + random.nextFloat() * 2, random.nextFloat() * 2);
		rotationalVelocity = 40 * (-0.5f + random.nextFloat());
	}

	@Override
	public void tick() {
		transform.x += velocity.x;
		transform.y += velocity.y;
		transform.rotation += rotationalVelocity;
	}

	@Override
	public void render(SpriteBatch batch) {

	}

}
