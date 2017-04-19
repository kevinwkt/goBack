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
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;

/**
 * Created by kevin on 3/20/2017.
 */

class OrbAttack extends Squirt {

    private int color;
    private static float SPEED = 4;
    private Body body;
    private Sprite sprite;

    protected float dmg=20;

    OrbAttack(World world, int type, float angle, Texture tx) {
        Texture t = tx;
        sprite = new Sprite(t);
        sprite.setCenter(
                ArcadeValues.pelletOriginX,
                ArcadeValues.pelletOriginY
        );
        this.color = type;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                ArcadeValues.meterspelletOriginX,
                ArcadeValues.meterspelletOriginY
        );

        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setBullet(true);


        body.setLinearVelocity(MathUtils.cos(angle) * SPEED,
                MathUtils.sin(angle) * SPEED);
        // body.setLinearVelocity(1,1);
        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {
        //neumann preventive shit
        //for (Fixture fix : body.getFixtureList()) body.destroyFixture(fix);

        /*
        Iterator<Fixture> it = body.getFixtureList().iterator();
        while(it.hasNext()){
            body.destroyFixture(it.next());
        }
        */


        //shape of pellet
        CircleShape shape = new CircleShape();

        shape.setRadius(
                ArcadeValues.pxToMeters(0.05f)
        );//sprite translated to meters

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.pelletCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.pelletMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );
        sprite.draw(batch);
    }

    public int getColor(){
        return color;
    }

}

