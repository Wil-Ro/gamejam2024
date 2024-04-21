package com.monjaro.gamejam.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class FallingPip extends Actor {

	private final Transform transform;
	private final Vector2 velocity;
	private final float rotationalVelocity;

	private final boolean onTop;

	public FallingPip(Transform transform, boolean onTop) {
		Random random = new Random();

		this.transform = transform;
		velocity = new Vector2(5 * (-0.5f + random.nextFloat()), 2 + random.nextFloat() * 3);
		rotationalVelocity = 40 * (-0.5f + random.nextFloat());

		this.onTop = onTop;
	}

	@Override
	public void tick() {
		velocity.y -= 9.81 / 60; //gravity

		transform.x += velocity.x;
		transform.y += velocity.y;
		transform.rotation += rotationalVelocity;
	}

	@Override
	public void render(SpriteBatch batch) {
		Texture sprite = Face.getPipSprite();
		batch.draw(sprite, transform.x, transform.y);
	}

	public boolean isOffScreen() {
		return transform.y <= 25;
	}

	public boolean isOnTop() {
		return onTop;
	}

}
