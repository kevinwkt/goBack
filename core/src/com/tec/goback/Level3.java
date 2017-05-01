package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by kevin on 18/03/17.
 */
class Level3 extends Frame {
    //box2d shit
    private World world;
    private HashSet<Body> deadThings;


    // Sophie
    //private Sophie sophie;
    private Texture sophieTexture;
    boolean sophieInitFlag = true;

    private ArcadeSophie sophie;

    // background
    Texture background;
    Texture arrow1;
    Texture arrow2;
    Texture arrow3;

    //map
    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    protected static final float LEFT_LIMIT = 20;
    protected static float RIGHT_LIMIT = 3650;

    private Input level3Input = new Input();

    // preferences
    Preferences pref = Gdx.app.getPreferences("getLevel");
    Preferences soundPreferences = Gdx.app.getPreferences("My Preferences");

    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;

    private Random randomArrowPosition = new Random();
    private Array<Body> bodies = new Array<Body>();
    private Array<ArcadeArrow> arrows = new Array<ArcadeArrow>();
    private Object obj;

    private float timeForArrow = 0;

    public Level3(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);

    }

    @Override
    public void show(){
        super.show();
        textureInit();
        worldInit();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(level3Input);

        debugRenderer=new Box2DDebugRenderer();

    }

    private void worldInit() {
        world = new World(Vector2.Zero, true);
        deadThings = new HashSet<Body>();
        world.setContactListener(new ContactListener() {
                                     @Override
                                     public void beginContact(Contact contact) {
                                         Object ob1 = contact.getFixtureA().getBody().getUserData();
                                         Object ob2 = contact.getFixtureB().getBody().getUserData();
                                         if(ob1 instanceof ArcadeSophie || ob2 instanceof ArcadeSophie){
                                             if(ob1 instanceof ArcadeSophie){
                                                 deadThings.add(contact.getFixtureB().getBody());
                                             }
                                             if(ob2 instanceof ArcadeSophie){
                                                 deadThings.add(contact.getFixtureA().getBody());
                                             }
                                         }
                                     }

                                     @Override
                                     public void endContact(Contact contact) {

                                     }

                                     @Override
                                     public void preSolve(Contact contact, Manifold oldManifold) {

                                     }

                                     @Override
                                     public void postSolve(Contact contact, ContactImpulse impulse) {

                                     }
                                 }

        );

        sophie = new ArcadeSophie(world,sophieTexture);


    }

    private void textureInit() {
        // background
        background = aManager.get("WOODS/WOODSPanoramic2of2.png");

        arrow1= aManager.get("MINIONS/ARROW/MINIONBlueArrow00.png");
        arrow2= aManager.get("MINIONS/ARROW/MINIONRedArrow00.png");
        arrow3= aManager.get("MINIONS/ARROW/MINIONYellowArrow00.png");

        // sophie
        sophieTexture = new Texture("Squirts/Sophie/SOPHIEWalk.png");
    }

    @Override
    public void render(float delta) {
        debugMatrix = new Matrix4(super.camera.combined);
        debugMatrix.scale(100, 100, 1f);
        cls();

        batch.begin();
        batch.setProjectionMatrix(super.camera.combined);

        if(state == GameState.CLUE){
            batch.end();
            clueStage.draw();
            Gdx.input.setInputProcessor(clueStage);
        }else if(state == GameState.STATS){
            batch.end();
            statsStage.sophieCoins.setText(Integer.toString(statsStage.statsPrefs.getInteger("Coins")));
            statsStage.yellowXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.blueXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.redXPLbl.setText(Integer.toString(statsStage.statsPrefs.getInteger("XP")));
            statsStage.draw();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }else if(state == GameState.PLAYING){

            batch.draw(background,0,0);
            batch.draw(pauseButton,camera.position.x+HALFW-pauseButton.getWidth(),camera.position.y-HALFH);

            if(sophieInitFlag) {
                sophieInitialMove();
            }
            drawArrow(delta);
            sophie.update();
            sophie.draw(batch);

            updateCamera();
            Gdx.input.setInputProcessor(level3Input);
            stepper(delta);
            batch.end();

        }else if(state == GameState.PAUSED){
            batch.end();
            //pauseStage.draw();
            Gdx.input.setInputProcessor(pauseStage);
        }




        batch.begin();
        batch.setProjectionMatrix(super.camera.combined);
        debugRenderer.render(world, debugMatrix);
        batch.end();
    }

    private void drawArrow(float delta) {
        // must appear at 1420
        timeForArrow += delta;
        if(timeForArrow >= .5){
            int rl= MathUtils.random(1);
            int typeshit= MathUtils.random(2);
            switch (typeshit){
                case 0:
                    new ArcadeArrow(world, typeshit,1,rl, arrow1);
                    break;
                case 1:
                    new ArcadeArrow(world, typeshit,1,rl, arrow2);
                    break;
                case 2:
                    new ArcadeArrow(world, typeshit,1,rl, arrow3);
                    break;
            }
            timeForArrow = 0;
        }

        world.getBodies(bodies);
        for(Body b : bodies){
            obj = b.getUserData();
            if( obj instanceof ArcadeArrow){
                ((ArcadeArrow)obj).draw(batch);
                Gdx.app.log("arrows", ((ArcadeArrow) obj).sprite.getY()+"");
                if(((ArcadeArrow) obj).sprite.getX() <= 0){
                    deadThings.add(b);
                }
            }


        }

        bodies.clear();
    }

    private void stepper(float delta){
        world.step(1/60f, 6, 2);

        for(Body b: deadThings){
            while(b.getFixtureList().size > 0){
                b.destroyFixture(b.getFixtureList().get(0));
            }
            world.destroyBody(b);
        }
        deadThings.clear();
    }

    private void sophieInitialMove() {

        if(sophie.getX() < 250){
            sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);
        }else{
            sophieInitFlag = false;
            sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
        }

    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        aManager.unload("MINIONS/ARROW/MINIONBlueArrow00.png");
        aManager.unload("MINIONS/ARROW/MINIONRedArrow00.png");
        aManager.unload("MINIONS/ARROW/MINIONYellowArrow00.png");
        aManager.unload("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");
        aManager.unload("Squirts/Sophie/SOPHIEWalk.png");
    }

    public void updateCamera(){
        float posX = sophie.getX()+sophie.getSprite().getWidth()/2;
        // Si está en la parte 'media'
        if (posX>=HALFW && posX<=WIDTH_MAP-HALFW) {
            // El personaje define el centro de la cámara
            camera.position.set((int)posX, camera.position.y, 0);
        } else if (posX>WIDTH_MAP-HALFW) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camera.position.set(WIDTH_MAP-HALFW, camera.position.y, 0);
        } else if ( posX<HALFH ) { // La primera mitad
            camera.position.set(HALFW, HALFH,0);
        }

        camera.update();
    }

    private class Input implements InputProcessor{

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
            if(camera.position.x - v.x < -522 && v.y < 135){
                state = GameState.PAUSED;
            }
            if(sophie.getMovementState()==ArcadeSophie.MovementState.STILL_LEFT||sophie.getMovementState()==ArcadeSophie.MovementState.STILL_RIGHT) {
                if (v.x >= camera.position.x) {
                    sophie.setMovementState(ArcadeSophie.MovementState.MOVE_RIGHT);

                } else {
                    sophie.setMovementState(ArcadeSophie.MovementState.MOVE_LEFT);
                }
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(sophie.getMovementState()==ArcadeSophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_LEFT);
            else if(sophie.getMovementState() == ArcadeSophie.MovementState.MOVE_RIGHT)
                sophie.setMovementState(ArcadeSophie.MovementState.STILL_RIGHT);
            sophie.update();


            if(camera.position.x - v.x < -522 && v.y < 135){
                state = GameState.PAUSED;

            }

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
}
