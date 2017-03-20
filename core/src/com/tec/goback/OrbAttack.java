package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by kevin on 3/20/2017.
 */

public class OrbAttack extends Squirt {

    private int vx = 2;      // Total Velocity
    private Vector3 v = new Vector3();
    private float dirx;
    private float diry;
    private float gradient;
    private attackState currentstate = attackState.MOVING;

    public OrbAttack(Texture textura, float x, float y, float dirx, float diry) {

        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(textura);    // QUIETO
        sprite.setPosition(x, y);    // Posición inicial
        v.set(x,y,0);

        this.dirx=dirx-x;
        this.diry=diry-y;
        this.gradient=this.diry/this.dirx;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, sprite.getX(), sprite.getY());
    }

    public void update() {
        switch (currentstate) {
            case MOVING:
                moveTowards();
                break;
            case HIT:
                dispose();
                break;
        }
    }

    public void moveTowards(){
        float movex= v.x;
        float movey=v.y;

        movex+=gradient*vx;
        movey+=gradient*vx;
        sprite.setPosition(movex,movey);
        v.set(movex,movey,0);
    }

    public void dispose(){
        sprite.getTexture().dispose();
    }

    public attackState getEstadoMovimiento() {
        return currentstate;
    }

    // Modificador de estadoMovimiento
    public void setMovementState(attackState ms) {
        this.currentstate = ms;
    }

    public Vector3 getPosition(){
        return v;
    }

    public enum attackState {
        MOVING,
        HIT
    }
}

