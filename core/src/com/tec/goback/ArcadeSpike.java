package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by kevin on 4/20/2017.
 **/

class ArcadeSpike extends Enemy {
    private PolygonShape shape;
    private double myA;
    private int walkCounter;
    private float walkLimit;
    private boolean walking=true;

    public ArcadeSpike(World world, int type, float angle,float spawnx,float spawny, Texture tx) {
        super(world,type,(angle*MathUtils.radiansToDegrees),spawnx,spawny,tx);
        SPEED=0.4f;
        //VELOCITIES
        myA =  angle + Math.PI;
        body.setLinearVelocity(SPEED * MathUtils.cos((float) myA), SPEED * MathUtils.sin((float)myA));
        timeframe = 0;
        dmg=15f;
        hp=80;
        walkCounter=0;
        walkLimit=ArcadeValues.meterspelletOriginX/2;
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
    }

    void fixturer(float density, float restitution) {
        //lizard

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        loader.attachFixture(body,"spike",fixtureDef,0.5f);
        if(leftRight==1) {
            body.setTransform(body.getPosition() ,angle*MathUtils.degreesToRadians+270*MathUtils.degreesToRadians);
        }
        if(leftRight==0) {
            body.setTransform(body.getPosition(),angle*MathUtils.degreesToRadians-90*MathUtils.degreesToRadians);
        }
    }

    void draw(SpriteBatch batch) {
//        sprite.setCenter(ArcadeValues.metersToPx(body.getPosition().x),ArcadeValues.metersToPx(body.getPosition().y));
//        sprite.setRotation(90);
//        sprite.draw(batch);
//    }
        timeframe += Gdx.graphics.getDeltaTime();
        if(walkCounter>walkLimit+500&&walking) {
            body.setLinearVelocity(-SPEED * MathUtils.cos((float) myA), -SPEED * MathUtils.sin((float)myA));
            Gdx.app.log("Spawn", "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            walking=false;
        }else if(!walking&&walkCounter>walkLimit+570){
            body.setLinearVelocity(SPEED*8f * MathUtils.cos((float) myA), SPEED*8f * MathUtils.sin((float)myA));
        }
        if(leftRight==1) sprite.setRotation(angle+270);//batch.draw(sprite, ArcadeValues.metersToPx(body.getPosition().x)-30, ArcadeValues.metersToPx(body.getPosition().y)-50,24.5f,65f,49f,130f,1f,1f,angle+270);
        else sprite.setRotation(angle-90);//batch.draw(sprite, ArcadeValues.metersToPx(body.getPosition().x)-30, ArcadeValues.metersToPx(body.getPosition().y)-50,24.5f,65f,49f,130f,1f,1f,angle-90);
        sprite.setCenter(ArcadeValues.metersToPx(body.getPosition().x), ArcadeValues.metersToPx(body.getPosition().y));
        sprite.draw(batch);
        walkCounter++;
    }

}
