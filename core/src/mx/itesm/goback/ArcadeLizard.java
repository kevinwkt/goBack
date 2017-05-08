package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by kevin on 3/20/2017.
 */

class ArcadeLizard extends Enemy {

    private int walkCounter;
    private int walkLimit;
    private boolean walkCond=true;

    ArcadeLizard(World world, int type, int leftOrRight, Animation tx) {
        super(world,type,leftOrRight,tx);
        SPEED=1f;
        if(leftOrRight==1) body.setLinearVelocity(-SPEED, 0f);
        if(leftOrRight==0) body.setLinearVelocity(SPEED,0f);
        timeframe=0;
        walkCounter=0;
        dmg=15f;
        hp = 80f;
    }

    void fixturer(float density, float restitution) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        if(leftRight==1) {
            loader.attachFixture(body,"lizardRight",fixtureDef,2.4f);
        }
        if(leftRight==0) {
            loader.attachFixture(body,"lizardLeft",fixtureDef,2.4f);
        }
    }

    void draw(SpriteBatch batch) {
        timeframe +=Gdx.graphics.getDeltaTime();
        region=an.getKeyFrame(timeframe);
        if (!region.isFlipX()) {
            if(leftRight==0) region.flip(true,false);
        }
        if(region.isFlipX()){
            if(leftRight==1) region.flip(true,false);
        }
        if(walkCounter==0){
            walkLimit=50 + (int)(Math.random() * 100);
        }
        if(walkCounter==walkLimit&&walkCond) {
            walkCounter=0;
            walkCond=false;
            body.setLinearVelocity(0f,0f);
        }
        if(walkCounter==walkLimit&&!walkCond) {
            walkCounter=0;
            walkCond=true;
            if(leftRight==1) body.setLinearVelocity(-SPEED, 0f);
            if(leftRight==0) body.setLinearVelocity(SPEED,0f);
        }
        if(leftRight==1) batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x)-83, ArcadeValues.metersToPx(body.getPosition().y)-40);
        else batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x)-147, ArcadeValues.metersToPx(body.getPosition().y)-40);
        walkCounter++;
    }
}

