package com.monjaro.gamejam.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI extends Actor{

    private final Game game;
    private final Transform position;
    private static Texture rerollTexture;

    private int rerolls;

    public UI(Game game, int x, int y) {
        this.game = game;
        position = new Transform(x, y, 0, 0);
    }

    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public void setRerolls(int x){
        rerolls = x;
    }

    public static void setRerollTexture(Texture texture){rerollTexture = texture;}

    @Override
    public void tick() {

    }

    @Override
    public void render(SpriteBatch batch) {
        for (int i = 0; i < game.getRound().getMaxRerolls(); i++) {
            if (i >= game.getRound().getRerolls())
                batch.setColor(Color.GRAY);
            batch.draw(rerollTexture, (position.x + (40f*i)), (position.y));
            batch.setColor(Color.WHITE);
        }
    }
}
