package com.monjaro.gamejam.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI extends Actor{
    private Transform position;
    private static Texture rerollTexture;

    private int rerolls;
    private int remainingRerolls;

    public UI(int x, int y, int rerolls)
    {
        position = new Transform(x, y, 0, 0);
        this.rerolls = rerolls;
        this.remainingRerolls = rerolls;
    }

    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public void setRemainingRerolls(int x){remainingRerolls = x;}

    public static void setRerollTexture(Texture texture){rerollTexture = texture;}

    @Override
    public void tick() {

    }

    @Override
    public void render(SpriteBatch batch) {
        for (int i = 0; i < rerolls; i++) {
            if (i > remainingRerolls-1)
                batch.setColor(Color.GRAY);
            batch.draw(rerollTexture, (position.x + (40f*i)), (position.y));
            batch.setColor(Color.WHITE);
        }
    }
}
