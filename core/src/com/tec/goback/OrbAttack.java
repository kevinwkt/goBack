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

/**
 * Created by kevin on 3/20/2017.
 */

class OrbAttack extends Squirt {

    private int color;
    private static float SPEED = 4;
    private Body body;
    private CircleShape shape;
    private Sprite sprite;

    public OrbAttack(World world, int type, float angle, Texture tx) {
        this.sprite = new Sprite(tx);
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
        for (Fixture fix : body.getFixtureList()) {
            body.destroyFixture(fix);
        }

        //shape of pellet
        shape = new CircleShape();

        shape.setRadius(
                ArcadeValues.pxToMeters((sprite.getWidth() * sprite.getScaleX()/2f))
        );//sprite translated to meters

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.pelletCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.pelletMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
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

