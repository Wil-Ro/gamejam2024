package com.monjaro.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.monjaro.gamejam.main.Actor;
import com.monjaro.gamejam.main.Round;

public class SegmentUI extends Actor {

    private static Texture separator;
    private static Texture criteriaSheet;
    private Rectangle rect;

    public SegmentUI(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void tick() {

    }

    public static void setSeparator(Texture texture) {
        separator = texture;
    }

    public static  void setCriteriaSheet(Texture texture) {
        criteriaSheet = texture;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.end();

        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0.7f, 0.4f, 0.6f, 1);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();

        batch.begin();

        int spriteWidth = 75;
        int spriteHeight = 200;
        Round round = Game.getRound();
        // change 3
        for (int i = 0; i < 3; i++) {
            int criteriaType = 0 ;
            int criteriaQuantity = 0;
            batch.draw(criteriaSheet, rect.x + (spriteWidth*i), rect.y, spriteWidth, rect.height, spriteWidth*criteriaType, spriteHeight*criteriaQuantity, spriteWidth, spriteHeight, false, false);
        }


    }
}
