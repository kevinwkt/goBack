package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

class ArcadeBoss implements IArcadeBoss{

    private int color;
    private boolean moving = false;
    private float life = ArcadeValues.yellowBossLife;
    private float ac;
    private Body body;
    private Sprite sprite;
    private BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("Physicshit.json"));

    ArcadeBoss(World world, int color, Texture tx) {
        sprite = new Sprite(tx);
        sprite.setCenter(
                ArcadeValues.pelletOriginX,
                ArcadeValues.pelletOriginY
        );
        this.color = color;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                ArcadeValues.pxToMeters(1280+125),
                ArcadeValues.meterspelletOriginY
        );

        body = world.createBody(bodyDef);
        fixturer(0.0f, 0.0f);

        //float SPEED = 4;
        body.setLinearVelocity(
                0,
                0
        );
        body.setUserData(this);
    }

    private void fixturer(float density, float restitution) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.friction = 0;

        fixtureDef.filter.categoryBits = ArcadeValues.enemyCat; //its category
        fixtureDef.filter.maskBits = ArcadeValues.enemyMask; //or of its category with colliding categories

        if(color == 1) {
            loader.attachFixture(body, "bossLizzard", fixtureDef, 7f);
        }else{
            loader.attachFixture(body, "bossJaguar", fixtureDef, 7f);
        }
    }

    public void move(){
        if (body.getPosition().x > ArcadeValues.pxToMeters((ArcadeValues.WIDTH + 120))){
            body.setLinearVelocity(-1f, 0f);
        }else{
            body.setLinearVelocity(1f, 0f);
        }
        moving = true;
    }

    public boolean getHurtDie(int color, float damage){
        life -= color != this.color ? damage : (damage * 2);
        return life <= 0.0f;
    }

     public void draw(SpriteBatch batch) {
        if(moving && ((body.getPosition().x > ArcadeValues.pxToMeters(ArcadeValues.WIDTH+125) && body.getLinearVelocity().x > 0) || body.getPosition().x < ArcadeValues.pxToMeters(ArcadeValues.bossX) && body.getLinearVelocity().x < 0)){
            body.setLinearVelocity(0f, 0f);
            moving = false;
        }

        sprite.setPosition(
                ArcadeValues.metersToPx(body.getPosition().x),
                ArcadeValues.metersToPx(body.getPosition().y)
        );

        switch((int)(10*(life/(color == 1 ? ArcadeValues.yellowBossLife : ArcadeValues.blueBossLife)))){
            case 10:case 9:
                    sprite.setColor(1f, 1f, 1f, 1f);
                break;
            case 8:case 7:  //low opacity
                    sprite.setColor(1f, 1f, 1f, 0.8f);
                break;
            case 6:case 5:  //lower opacity
                    sprite.setColor(1f, 1f, 1f, 0.6f);
                break;
            case 4:case 3:  //low freq blink
                blink(0.5f);
                break;
            case 2:case 1:  //hi freq blink
                blink(0.18f);
                break;
        }

        sprite.draw(batch);
    }

    private void blink(float speed) {
        ac = ac <= 1.0 ? ac + Gdx.graphics.getDeltaTime() : 0;
        sprite.setColor(1f, 1f, 1f, (0.3f * MathUtils.sin(ac * (2 * MathUtils.PI / speed)) + 0.5f));
    }

    int getColor(){return color;}
    public int getLife(){return (int)(10*(life/(color == 1 ? ArcadeValues.yellowBossLife : ArcadeValues.blueBossLife)));}
}

