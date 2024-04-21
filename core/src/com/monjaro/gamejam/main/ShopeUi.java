package com.monjaro.gamejam.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class ShopeUi extends Actor{

    private static Texture backing;
    private static Texture faceBacking;

    public static void setFaceBacking(Texture faceBacking) {
        ShopeUi.faceBacking = faceBacking;
    }

    private static Game game;

    public static void setGame(Game game) {
        ShopeUi.game = game;
    }

    public static void setBacking(Texture backing) {
        ShopeUi.backing = backing;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(backing, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
        List<Face> shope = game.getShope();
        for (int i = 0; i < shope.size(); i++) {
            Face face = shope.get(i);

            if (face == null)
                continue;

            face.setTransform(new Transform(((Gdx.graphics.getWidth()/4)*i)+(Gdx.graphics.getWidth()/4), Gdx.graphics.getHeight()/8, 64, 64));

            face.render(batch);
            batch.draw(faceBacking, face.getTransform().x-faceBacking.getWidth()/2, face.getTransform().y-faceBacking.getHeight()/2);
        }

    }
}
