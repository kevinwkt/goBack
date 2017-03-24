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

/**
 * Created by kevin on 3/20/2017.
 */

class ArcadeLizard extends Enemy {

    private PolygonShape shape;

    public ArcadeLizard(World world, int type, int leftOrRight, Animation tx) {
        super(world,type,leftOrRight,tx);
        SPEED=2f;
        timeframe=0;
    }

    void fixturer(float density, float restitution) {

        //neumann preventive shit
        for (Fixture fix : body.getFixtureList()) {body.destroyFixture(fix);}

        //lizard
        shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(227f), ArcadeValues.pxToMeters(67f));

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
        timeframe +=Gdx.graphics.getDeltaTime();
        region=an.getKeyFrame(timeframe);
        batch.draw(region, ArcadeValues.metersToPx(body.getPosition().x)-120, ArcadeValues.metersToPx(body.getPosition().y)-39);
    }

    private void waitGo(){

    }

}

