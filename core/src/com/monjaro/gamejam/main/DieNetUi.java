package com.monjaro.gamejam.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;

import static com.badlogic.gdx.graphics.GL20.*;

public class DieNetUi extends Actor{
    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;
    private Rectangle rect;
    private boolean visible;

    @Override
    public void tick() {

    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible)
            return;
        if (game.getSelectedDice().size() == 0)
            return;

        Die die = game.getSelectedDice().get(0);


        batch.end();


        Gdx.gl.glEnable(GL_BLEND);
        Gdx.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.7f);
        renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer.end();

        batch.begin();

        for (int i = 0; i < 6; i++) {
            Face face = die.getFaces()[i];
            Rectangle rect = getDieFaceLocation(i);
            rect.width = Gdx.graphics.getWidth()/4;
            rect.height = Gdx.graphics.getWidth()/4;;

            face.renderAtRect(batch, rect);
        }

    }

    private Rectangle getDieFaceLocation(int i)
    {
        Rectangle result = new Rectangle();
        switch(i){
            case 0:
                result.x = (Gdx.graphics.getWidth()/4)*3;
                result.y = (Gdx.graphics.getHeight()/3)*2;
                break;
            case 5:
                result.x = (Gdx.graphics.getWidth()/4)*3;
                result.y = (Gdx.graphics.getHeight()/3)*0;
                break;
            default:
                result.x = (Gdx.graphics.getWidth()/4)*i;
                result.y = (Gdx.graphics.getHeight()/3)*1;
                break;
        }

        return result;
    }

    public void setVisible(boolean b) {
        visible = b;
    }
}
