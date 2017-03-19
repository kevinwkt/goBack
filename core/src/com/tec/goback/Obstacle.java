package com.tec.goback;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by kevin on 3/19/2017.
 */

public class Obstacle
{
    protected Sprite sprite;    // Imagen

    public Obstacle() {

    }

    public Obstacle(Texture texture, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }

    public boolean contains(Vector3 v) {
        float x = v.x;
        float y = v.y;

        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

//    public boolean sophieCollision(){
//
//    }

}
