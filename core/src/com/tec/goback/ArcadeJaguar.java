package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.Iterator;

/**
 * Created by kevin on 3/20/2017.
 */

class ArcadeJaguar extends Enemy {
    private float vi=5;
    private float jumpFor=0;
    private int walkCounter;
    private int walkLimit;
    private boolean walkCond=true;

    ArcadeJaguar(World world, int type, int leftOrRight, Animation tx) {
        super(world,type,leftOrRight,tx);
        SPEED=1f;
        if(leftOrRight==1) SPEED=SPEED*-1;
        body.setLinearVelocity(SPEED,0f);
        timeframe=0;
        walkCounter=0;
        dmg=15f;
        hp = 80f;
    }

    void fixturer(float density, float restitution) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(100f), ArcadeValues.pxToMeters(35f));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    private float velocityCalc(float time){
        return vi-0.07f*time;
    }
    private void moveJump(){
        float doWhi= velocityCalc(jumpFor);
        if(doWhi>-vi) {
            body.setLinearVelocity(SPEED,doWhi);
        }
        jumpFor++;
    }
    void draw(SpriteBatch batch) {
        timeframe +=Gdx.graphics.getDeltaTime();
        region=an.getKeyFrame(timeframe);
        moveJump();
        if(leftRight==1) batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x)-83, ArcadeValues.metersToPx(body.getPosition().y)-40);
        else batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x)-147, ArcadeValues.metersToPx(body.getPosition().y)-40);
    }
}

