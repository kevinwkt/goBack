package com.tec.goback;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by gerry on 3/22/17.
 */
public class ArcadeSophie {
    private Body body;
    private PolygonShape shape;
    private Sprite sprite;

    public ArcadeSophie(World world, Texture tx){
        this.sprite = new Sprite(tx);
        sprite.setCenter(ArcadeValues.pelletOriginX, ArcadeValues.pelletOriginY);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //bodyDef.position.set(ArcadeValues.meterspelletOriginX, ArcadeValues.meterspelletOriginY); // no serían metros?
        bodyDef.position.set(
                1/*ArcadeValues.meterspelletOriginX*/,
                1/*ArcadeValues.meterspelletOriginY*/
        );
        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setLinearVelocity(0f, 0f);
        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {

        //neumann preventive shit
        for (Fixture fix : body.getFixtureList()) {
            body.destroyFixture(fix);
        }

        //shape of girl
        shape = new PolygonShape();
        shape.setAsBox(
                ArcadeValues.pxToMeters(sprite.getHeight()),
                ArcadeValues.pxToMeters(sprite.getWidth())
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.sophieCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.sophieCat; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    public void draw(SpriteBatch batch) {

        sprite.setCenter(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );

        sprite.draw(batch);
    }
}
