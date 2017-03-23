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
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
//


/**
 * Created by gerry on 2/18/17.
 */
public class Arcade extends Frame{

    private World world;
    private Array<Squirt> deadThings;
    private ArrayList<Body> wall = new ArrayList<Body>();

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

    public static final float WIDTH_MAP = 1280;
    public static final float HEIGHT_MAP = 720;

    public Arcade(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    @Override
    public void show() {
        d = pref.getInteger("level");
        Gdx.app.log(""+d, "el d");
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
                orby=new Sprite(orbyellow);

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
        world = new World(new Vector2(0,0), true);
        deadThings = new Array<Squirt>();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //check who the fuck is colliding and update deadThings for deletion
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

        /*
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;

        bdef.position.set(Constantes.toWorldSize(-120),0);
        bordes.add(world.createBody(bdef));
        crearFixtureBordes(bordes.get(0),100,Constantes.ALTO_PANTALLA);

        bdef.position.set(Constantes.toWorldSize(Constantes.ANCHO_PANTALLA+120),0);
        bordes.add(world.createBody(bdef));
        crearFixtureBordes(bordes.get(1),100,Constantes.ALTO_PANTALLA);

        bdef.position.set(Constantes.toWorldSize(-120),Constantes.toWorldSize(-120));
        bordes.add(world.createBody(bdef));
        crearFixtureBordes(bordes.get(2),Constantes.ANCHO_PANTALLA+200,100);
        bdef.position.set(Constantes.toWorldSize(-120),
                Constantes.toWorldSize(Constantes.ALTO_PANTALLA+120));
        bordes.add(world.createBody(bdef));
        crearFixtureBordes(bordes.get(3),Constantes.ANCHO_PANTALLA+200,100);
        */
        BodyDef bd = new BodyDef();

        bd.type = BodyDef.BodyType.StaticBody;
        //bd.position.set();
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
        world.step(1/60f, 8, 3);

        // if (state==GameState.PAUSED) {
        //     //Gdx.input.setInputProcessor(pauseStage);
        //     pauseStage.draw();
        // }

        batch.end();
    }
    private void drawShit(){
        batch.draw(background,0,0);
        drawOrbes();
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
        }
    }

    private void drawOrbes(){
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
          
            if (/*ON THE BUTTON TO CHANGE COLORS*/!true){
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
            else {//if we're not switching


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
