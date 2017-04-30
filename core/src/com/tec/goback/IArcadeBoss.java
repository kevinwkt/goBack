package com.tec.goback;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IArcadeBoss{
    boolean getHurtDie(int color, float damage);
    void move();
    void draw(SpriteBatch batch);
}
