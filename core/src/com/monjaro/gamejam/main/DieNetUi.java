package com.monjaro.gamejam.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.graphics.GL20.*;

public class DieNetUi extends Actor{
    private Die die;
    private Rectangle rect;
    private boolean visible;

    @Override
    public void tick() {

    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible)
            return;


        batch.end();


        Gdx.gl.glEnable(GL_BLEND);
        Gdx.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.7f);
        renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer.end();

        batch.begin();
    }

    public void setVisible(boolean b) {
        visible = b;
    }
}
