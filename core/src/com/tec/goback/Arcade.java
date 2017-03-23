package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
//


/**
 * Created by gerry on 2/18/17.
 */
class Arcade extends Frame{

    private World world;
    private Array<Body> deadThings;
    private ArrayList<Body> wall = new ArrayList<Body>();
    private float fstep;
    //CURRENT COLOR ORB
    private orbColor currentColor = orbColor.YELLOW;

    //MOTHER FUCKER
    private int d;

    // Music
    private Music bgMusic;
    private Sound fx;

    //Textures
    private Texture background; //Background
    
    private ArcadeSophie sophie;

    private Texture eyesred;
    private Texture eyesblue;
    private Texture eyesyellow;
    private Texture orbred;
    private Texture orbblue;
    private Texture orbyellow;
    private Texture pelletyellow;
    private Texture pelletblue;
    private Texture pelletred;

    private Sprite orby;
    private Sprite orbb;
    private Sprite orbr;

    private static final float WIDTH_MAP = 1280;
    private static final float HEIGHT_MAP = 720;

    public Arcade(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        d = pref.getInteger("level");
        super.show();
        textureInit();
        worldInit();
        sophieInit();
        wallsInit();
        Gdx.input.setInputProcessor(new Input());
        Gdx.input.setCatchBackKey(true); //Not important
    }

    private void textureInit() {
        background = new Texture("HARBOR/GoBackHARBORPanoramic.png"); //switch

        switch (d){
            case 1:
                orbyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orby = new Sprite(orbyellow);

                pelletyellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                break;
            case 2:
                orbyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orbblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
                eyesblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png");
                orby=new Sprite(orbyellow);
                orbb=new Sprite(orbblue);

                pelletyellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                pelletblue = aManager.get("PELLET/ATAQUEBluePellet.png");
                break;
            case 3:
                orbyellow = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orbblue = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
                eyesblue = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png");
                orbred = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png");
                eyesred = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrbEyes.png");
                orby = new Sprite(orbyellow);
                orbb = new Sprite(orbblue);
                orbr = new Sprite(orbred);

                pelletyellow = aManager.get("PELLET/ATAQUEYellowPellet.png");
                pelletblue = aManager.get("PELLET/ATAQUEBluePellet.png");
                pelletred = aManager.get("PELLET/ATAQUERedPellet.png");
                break;
        }
    }

    private void worldInit(){
        world = new World(Vector2.Zero, true);
        fstep = 0f;
        deadThings = new Array<Body>();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //check who the fuck is colliding and update deadThings for deletion
                Object ob1 = contact.getFixtureA().getBody().getUserData();
                Object ob2 = contact.getFixtureB().getBody().getUserData();

                //pellets die no matter who they collide with
                if(ob1 instanceof OrbAttack){
                    deadThings.add(contact.getFixtureA().getBody());
                    //Gdx.app.log("array", deadThings.toString());
                }

                if(ob2 instanceof OrbAttack){
                     deadThings.add(contact.getFixtureB().getBody());
                    //Gdx.app.log("array", deadThings.toString());
                }

                    //If sophie got hit
                if(ob1 instanceof ArcadeSophie || ob2 instanceof ArcadeSophie) {
                    if (ob1 instanceof ArcadeSophie) {
                        // if (((ArcadeSophie)ob1).getHurtDie(((Enemy)ob2).getColor(), ((Enemy)ob2).getDamage())
                        //     state = GameState.PAUSED;
                        deadThings.add(contact.getFixtureA().getBody());
                    }
                    if (ob2 instanceof ArcadeSophie) {
                        //if (((ArcadeSophie)ob2).getHurtDie(((Enemy)ob1).getColor(), ((Enemy)ob2).getDamage())
                        //    state = GameState.LOST;
                        deadThings.add(contact.getFixtureB().getBody());
                    }
                }else{//If some bad guy got hit
                   /*
                    if (ob1 instanceof Enemy) {
                        if (((Enemy)ob1).getHurtDie(((OrbAttack)ob2).getColor(), ((Enemy)ob2).getDamage())
                            deadThings.add(contact.getFixtureA().getBody());
                    }
                    if (ob2 instanceof Enemy) {
                        if (((ArcadeSophie)ob2).getHurtDie(((OrbAttack)ob1).getColor(), ((Enemy)ob2).getDamage())
                            deadThings.add(contact.getFixtureB().getBody());
                   */
                }

            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

    }

    private void sophieInit(){
        Texture sophieTx = aManager.get("Interfaces/GAMEPLAY/ARCADE/ARCADESophie.png");
        sophie = new ArcadeSophie(world, sophieTx);
    }

    private void wallsInit(){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(ArcadeValues.pxToMeters(-120),0);

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(0),100,HEIGHT_MAP);

        bd.position.set(ArcadeValues.pxToMeters(WIDTH_MAP+120),0);

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(1),100,HEIGHT_MAP);

        bd.position.set(ArcadeValues.pxToMeters(-120),ArcadeValues.pxToMeters(-120));

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(2),WIDTH_MAP+200,100);

        bd.position.set(ArcadeValues.pxToMeters(-120), ArcadeValues.pxToMeters(HEIGHT_MAP+120));

        wall.add(world.createBody(bd)); //append to body array 
        makeWallFixture(wall.get(3),WIDTH_MAP+200,100);
    }

    private void makeWallFixture(Body b, float x, float y){
        //neumann preventive shit 
        for (Fixture f : b.getFixtureList()){
            b.destroyFixture(f);
        }

        FixtureDef f = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ArcadeValues.pxToMeters(x),ArcadeValues.pxToMeters(y));
        f.shape = shape;

        f.filter.categoryBits = ArcadeValues.wallCat;
        f.filter.maskBits = ArcadeValues.wallMask;
        b.createFixture(f);
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(super.camera.combined);
        cls();
        batch.begin();
        drawShit();

        //TODO DO THE PAUSE LOL
        // if (state==GameState.PAUSED) {
        // }
        stepper(delta);


        batch.end();
    }
    private void stepper(float delta){
        //much steps
        fstep += delta;
        while(fstep > 1/120f){
            world.step(1/120F, 8, 3);
            fstep -= 1/120f;
        }

        //clean dead thinfs
        for(Body b: deadThings){
            for(Fixture f: b.getFixtureList()){
                b.destroyFixture(f);
            }
            world.destroyBody(b);
        }
        deadThings.clear();

    }
    private void drawShit(){
        batch.draw(background,-2560,0);
        Array<Body> squirts = new Array<Body>();
        world.getBodies(squirts);
        Object obj;
        for(Body b: squirts){
            obj = b.getUserData();
            if(obj instanceof OrbAttack){
                ((OrbAttack)obj).draw(batch);
            }else if(obj instanceof ArcadeSophie){
                ((ArcadeSophie)obj).draw(batch);
            }
            //TODO DRAW ALL OF THE REMAINING ENEMIES LIKE ABOVE
        }
        drawOrbes();
    }

    private void drawOrbes(){
        //coords are temporary
        //TODO good coords
        switch (d){
            case 1:
                orby.setPosition(ArcadeValues.pelletOriginX-135,ArcadeValues.pelletOriginY-125);
                orby.draw(batch);
                batch.draw(eyesyellow,ArcadeValues.pelletOriginX,ArcadeValues.pelletOriginY);
                break;
            case 2:
                orby.setPosition(ArcadeValues.pelletOriginX,ArcadeValues.pelletOriginY);
                orbb.setPosition(ArcadeValues.pelletOriginX,ArcadeValues.pelletOriginY);
                batch.draw(eyesblue,ArcadeValues.pelletOriginX,ArcadeValues.pelletOriginY);
                batch.draw(eyesyellow,ArcadeValues.pelletOriginX,ArcadeValues.pelletOriginY);
                orby.draw(batch);
                orbb.draw(batch);
                switch (currentColor){
                    case YELLOW:
                        orbb.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        break;
                    case BLUE:
                        orby.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        break;
                }
                break;
            case 3:
                orby.draw(batch);
                orby.setPosition(0,0);
                batch.draw(eyesyellow,0,0);
                orbb.draw(batch);
                orbb.setPosition(0,0);
                batch.draw(eyesblue,0,0);
                orbr.draw(batch);
                orbr.setPosition(0,0);
                batch.draw(eyesred,0,0);
                switch (currentColor){
                    case YELLOW:
                        orbb.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        orbr.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        break;
                    case BLUE:
                        orby.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        orbr.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        break;
                    case RED:
                        orby.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        orbb.setColor(0.5F, 0.5F, 0.5F, 0.6F);
                        break;
                }
                break;
        }
    }


    //WTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTF
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
    //WTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTFWTF


    private class Input implements InputProcessor {
        private Vector3 v = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            v.set(screenX,screenY,0);
            camera.unproject(v);
            Gdx.app.log("x: ", screenX + " ");
            Gdx.app.log("y: ", screenY + " ");
            Gdx.app.log("color: ", currentColor +" ");
            
            //TODO CHECK FOR BUTTON
            if (!true){//not true temporary
                switch (d) {
                    case 1:
                        break;
                    case 2:
                        switch (currentColor) {
                            case BLUE:
                                currentColor=orbColor.YELLOW;
                                break;
                            case YELLOW:
                                currentColor=orbColor.BLUE;
                                break;
                        }
                        break;
                    case 3:
                        switch (currentColor) {
                            case BLUE:
                                currentColor=orbColor.RED;
                                break;
                            case YELLOW:
                                currentColor=orbColor.BLUE;
                                break;
                            case RED:
                                currentColor=orbColor.YELLOW;
                                break;
                        }
                        break;
                }
            }
            else {//if we're not switching orbes
                float angle = MathUtils.atan2(
                        v.y - ArcadeValues.pelletOriginY,
                        v.x - ArcadeValues.pelletOriginX
                );
                switch(currentColor){
                    case YELLOW:
                        new OrbAttack(world, 1, angle, pelletyellow);
                        break;
                    case BLUE:
                        new OrbAttack(world, 2, angle, pelletblue);
                        break;
                    case RED:
                        new OrbAttack(world, 3, angle, pelletred);
                        break;
                }
            
            }


            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    public enum orbColor {
        RED,
        BLUE,
        YELLOW
    }
}
