package com.tec.goback;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/*
 * Created by gerry on 3/22/17.
 */
class ArcadeSophie {
    private Body body;
    private Sprite sprite;
    private float life = 100;
    private int color = 1;

    ArcadeSophie(World world, Texture tx){
        this.sprite = new Sprite(tx);
        sprite.setCenter(ArcadeValues.pelletOriginX-100, ArcadeValues.pelletOriginY);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                ArcadeValues.meterspelletOriginX-1,
                ArcadeValues.meterspelletOriginY+(ArcadeValues.pxToMeters(tx.getHeight()/3))
        );
        body = world.createBody(bodyDef);
        fixturer(0.1f, 0.7f);
        body.setLinearVelocity(0f, 0f);
        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {
//        //neumann preventive shit
//        for (Fixture fix : body.getFixt ureList()) {
//            body.destroyFixture(fix);
//        }

        //shape of girl
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                ArcadeValues.pxToMeters(sprite.getWidth()),
                ArcadeValues.pxToMeters(sprite.getHeight())
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = 0; //its category
        fixtureDef.filter.maskBits = ArcadeValues.sophieMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    boolean getHurtDie(int type, float damage){
        if(color == type){
            life -= damage;
        }else{
            life -= damage * 2;
        }
        return life <= 0.0f;
    }

    void draw(SpriteBatch batch) {

        sprite.setCenter(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );
        sprite.setColor(1.0f, 1.0f, 1.0f, life/100f);

        sprite.draw(batch);
    }

    void setColor(int color){
        this.color = color;
    }
}
