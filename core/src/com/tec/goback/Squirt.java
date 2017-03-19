package com.tec.goback;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Representa un elemento gráfico del juego
 */

public class Squirt
{
    protected Sprite sprite;    // Imagen

    public Squirt() {

    }

    public Squirt(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
