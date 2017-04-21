package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by kevin on 4/20/2017.
 **/

class ArcadeGoo extends Enemy {
    private PolygonShape shape;
    private int walkCounter;
    private int walkLimit;
    private boolean walkCond=true;

    public ArcadeGoo(World world, int type, float angle,float spawnx,float spawny, Animation tx) {
        super(world,type,angle,spawnx,spawny,tx);
        SPEED=0.5f;
        //VELOCITIES
        if(x>0) body.setLinearVelocity(-MathUtils.cos(90-angle) * SPEED, -MathUtils.sin(90-angle) * SPEED);
        else body.setLinearVelocity(MathUtils.cos(180-angle) * SPEED, -MathUtils.sin(180-angle) * SPEED);
        timeframe=0;
        walkCounter=0;
        dmg=15f;
        hp=80;

    }

    void fixturer(float density, float restitution) {
        //lizard
        shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(x), ArcadeValues.pxToMeters(y));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    void draw(SpriteBatch batch) {
        timeframe += Gdx.graphics.getDeltaTime();
        region=an.getKeyFrame(timeframe);
        batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x), ArcadeValues.metersToPx(body.getPosition().y));
        walkCounter++;
    }
}
