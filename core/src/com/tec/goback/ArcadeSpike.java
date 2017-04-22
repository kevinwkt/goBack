package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    private int walkCounter;
    private int walkLimit;
    private boolean walkCond=true;
    private float totalFlyx;
    private float totalFlyy;
    private double myA;

    public ArcadeSpike(World world, int type, float angle,float spawnx,float spawny, Texture tx) {
        super(world,type,angle,spawnx,spawny,tx);
        SPEED=0.5f;
        totalFlyx=ArcadeValues.meterspelletOriginX/2;
        totalFlyy=(72-ArcadeValues.meterspelletOriginY)/2;
        //VELOCITIES
        myA =  angle + Math.PI;
        body.setLinearVelocity(SPEED * MathUtils.cos((float) myA), SPEED * MathUtils.sin((float)myA));

        timeframe = 0;
        walkCounter=0;
        dmg=15f;
        hp=80;

    }

    void fixturer(float density, float restitution) {
        //lizard
        shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(50f), ArcadeValues.pxToMeters(35f));

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
        if(Math.abs(ArcadeValues.pelletOriginX-body.getPosition().x)<totalFlyx&&Math.abs(ArcadeValues.pelletOriginY-body.getPosition().y)<totalFlyy) body.setLinearVelocity(2*SPEED * MathUtils.cos((float) myA), 2*SPEED * MathUtils.sin((float)myA));
        batch.draw(sprite, ArcadeValues.metersToPx(body.getPosition().x)-120, ArcadeValues.metersToPx(body.getPosition().y)-39);
        walkCounter++;
    }
}
