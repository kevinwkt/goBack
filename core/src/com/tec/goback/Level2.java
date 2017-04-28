package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashSet;

/**
 * Created by sergiohernandezjr on 18/03/17.
 */
class Level2 extends Frame {
    //box2d shit
    private World world;
    private HashSet<Body> deadThings;


    // Sophie
    //private Sophie sophie;
    private Texture sophieTexture;
    //boolean sophieInitFlag = true;

    private ArcadeSophie sophie;

    // background
    Texture background;

    //map
    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    // preferences
    Preferences pref = Gdx.app.getPreferences("getLevel");
    Preferences soundPreferences = Gdx.app.getPreferences("My Preferences");



    public Level2(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);

    }

    @Override
    public void show(){
        super.show();
        textureInit();
        worldInit();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(new Input());



        
    }

    private void worldInit() {
        world = new World(Vector2.Zero, true);
        deadThings = new HashSet<Body>();
        world.setContactListener(new ContactListener() {
             @Override
             public void beginContact(Contact contact) {

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
        background = aManager.get("MOUNTAINS/GoBackMOUNTAINSPanoramic.png");

        // sophie
        sophieTexture = new Texture("Squirts/Sophie/SOPHIEWalk.png");

        
    }

    @Override
    public void render(float delta) {
        cls();

        batch.setProjectionMatrix(super.camera.combined);
        batch.begin();

        batch.draw(background,0,0);
        updateCamera();
        sophieInitialMove();
        sophie.draw(batch);
        stepper(delta);
        batch.end();
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
            sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);
        }else{
            sophie.setMovementState(Sophie.MovementState.STILL_RIGHT);
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

            if(sophie.getMovementState()==Sophie.MovementState.STILL_LEFT||sophie.getMovementState()==Sophie.MovementState.STILL_RIGHT) {
                if(camera.position.x - v.x < -522 && v.y < 135){
                    state = GameState.PAUSED;
                }else{
                    //if(!dialogueOn){
                        if (v.x >= camera.position.x) {
                            sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);

                        } else {
                            sophie.setMovementState(Sophie.MovementState.MOVE_LEFT);
                        }
                    //}

                }
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(sophie.getMovementState()==Sophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(Sophie.MovementState.STILL_LEFT);
            else if(sophie.getMovementState() == Sophie.MovementState.MOVE_RIGHT)
                sophie.setMovementState(Sophie.MovementState.STILL_RIGHT);
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
