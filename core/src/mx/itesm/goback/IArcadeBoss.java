package mx.itesm.goback;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

interface IArcadeBoss{
    boolean getHurtDie(int color, float damage);
    void move();
    void draw(SpriteBatch batch);
    int getLife();
}
