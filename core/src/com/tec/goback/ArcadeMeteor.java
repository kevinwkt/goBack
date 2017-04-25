package com.tec.goback;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 Created by gerry on 4/24/17.
 */

class ArcadeMeteor extends Enemy {
    ArcadeMeteor(World world, float x, Texture tx){
        super(world, x, tx);
        this.sprite = new Sprite(tx);
        fixturer(0.0f, 0.0f);
    }

    @Override
    void fixturer(float density, float restitution) {
        CircleShape shape = new CircleShape();
        shape.setRadius(ArcadeValues.pxToMeters(ArcadeValues.meteorRadius));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        body.createFixture(fixtureDef);
    }

    @Override
    void draw(SpriteBatch batch) {
        sprite.setPosition(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );
    }
}
