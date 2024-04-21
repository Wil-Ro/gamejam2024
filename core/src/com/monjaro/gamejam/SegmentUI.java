package com.monjaro.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.monjaro.gamejam.main.Actor;
import com.monjaro.gamejam.main.Game;
import com.monjaro.gamejam.main.Round;

public class SegmentUI extends Actor {

    private static Texture separator;
    private static Texture criteriaSheet;
    private Rectangle rect;
    private Game game;

    public SegmentUI(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void tick() {

    }

    public void setGame(Game game){
        this.game = game;
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
        Round round = game.getRound();

        int numOfSegments = round.getSegments().size();
        for (int i = 0; i < numOfSegments; i++) {
            if (round.getSegments().get(i).isDestroyedBy(game.getSelectedDice()))
                batch.setColor(0.5f, 0.3f, 0.5f, 1);
            else if (round.getSegments().get(i).isDestroyed())
                batch.setColor(0,0,0,1);




            int criteriaType = round.getSegments().get(i).getSpriteColumn();
            int criteriaQuantity = round.getSegments().get(i).getSpriteRow();
            batch.draw(criteriaSheet,
                    rect.x + ((rect.width/(numOfSegments+1))*(i+1))-spriteWidth/2, ((rect.y + rect.height/2)-spriteHeight/2),
                    spriteWidth, spriteHeight,
                    spriteWidth*criteriaType, spriteHeight*(criteriaQuantity),
                    spriteWidth, spriteHeight,
                    false, false);

            batch.setColor(Color.WHITE);
        }


    }
}
