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

class ArcadeSkull extends Enemy {
    private PolygonShape shape;
    private int walkCounter;
    private int walkLimit;
    private boolean walkCond=true;

    public ArcadeSkull(World world, int type, float angle,float spawnx,float spawny, Animation tx) {
        super(world,type,angle,spawnx,spawny,tx);
        SPEED=0.5f;

        //VELOCITIES
        double myA =  angle + Math.PI;
        body.setLinearVelocity(SPEED * MathUtils.cos((float) myA), SPEED * MathUtils.sin((float)myA));

        timeframe = 0;
        walkCounter=0;
        dmg=15f;
        hp=80;

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

        loader.attachFixture(body,"skll",fixtureDef,1f);
    }

    void draw(SpriteBatch batch) {
        timeframe += Gdx.graphics.getDeltaTime();
        region=an.getKeyFrame(timeframe);
        batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x)-67, ArcadeValues.metersToPx(body.getPosition().y)-54);
        walkCounter++;
    }
}
