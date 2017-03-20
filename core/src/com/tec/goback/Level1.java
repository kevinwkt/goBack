package com.tec.goback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by sergiohernandezjr on 18/03/17.
 * Con ayuda de DON PABLO
 */
public class Level1 extends Frame implements Screen {
    private App app;

    private Sophie sophie;
    private Texture sophieTexture;

    // orb
    private Sprite yellowOrb;
    private boolean orbEncontrado = false;

    // Música
    private Music bgMusic;
    private Sound fx;

    //BG
    Texture background;


    // batch
    private SpriteBatch batch;


    public static final float LEFT_LIMIT = 816;

    private final float SOPHIE_START_X=2272;
    private final float SOPHIE_START_Y=220;

    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    public Level1(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
        //this.app = app;
    }

    @Override
    public void show() {
        super.show();
        objectInit();

        textureInit();
        sophie.setMovementState(Sophie.MovementState.WAKING);
        sophie.sprite.setPosition(SOPHIE_START_X, SOPHIE_START_Y);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(new Input());
    }

    private void objectInit() {
        batch = new SpriteBatch();
        yellowOrb = new Sprite((Texture)aManager.get("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png"));
    }

    @Override
    public void render(float delta) {
        cls();
        batch.setProjectionMatrix(super.camera.combined);
        batch.begin();

        batch.draw(background,0,0);

        batch.draw(pauseButton,camera.position.x-HALFW,camera.position.y-HALFH);
        sophie.update();

        yellowOrb.draw(batch);
        yellowOrb.setPosition(3080,220);


        //pauseSprite.draw(batch);

        if (state==GameState.PAUSED) {
            pauseStage.draw();
            Gdx.input.setInputProcessor(pauseStage);
        }else if(state==GameState.PLAYING){
            Gdx.input.setInputProcessor(new Input());
        }


        if(!orbEncontrado){
            if(yellowOrb.getBoundingRectangle().contains(sophie.sprite.getX()+sophie.sprite.getWidth(),sophie.sprite.getY())){
                orbEncontrado = true;
            }
        }else{
            if(sophie.getMovementState()==Sophie.MovementState.STILL_LEFT||
                    sophie.getMovementState()==Sophie.MovementState.MOVE_LEFT)
                yellowOrb.setPosition(yellowOrb.getWidth()+sophie.sprite.getX(),sophie.sprite.getY());
            else
                yellowOrb.setPosition(sophie.sprite.getX()-yellowOrb.getWidth(),sophie.sprite.getY());
        }
        sophie.draw(batch);
        updateCamera();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

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

    private void updateCamera() {
        float posX = sophie.sprite.getX();
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

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void textureInit() {
        sophieTexture = new Texture("Squirts/Sophie/SOPHIEWalk.png");
        sophie = new Sophie(sophieTexture, 100,100);

        //Background
        background = new Texture("HARBOR/GoBackHARBORPanoramic.png");
    }

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
            if(sophie.getMovementState()==Sophie.MovementState.STILL_LEFT||sophie.getMovementState()==Sophie.MovementState.STILL_RIGHT) {
                if(sophie.sprite.getX() - v.x > 522 && v.y < 135){
                    state = GameState.PAUSED;

                }else{
                    if (v.x >= camera.position.x) {
                        sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);
                    } else {
                        sophie.setMovementState(Sophie.MovementState.MOVE_LEFT);
                    }
                }

            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(sophie.getMovementState()==Sophie.MovementState.MOVE_LEFT)
                sophie.setMovementState(Sophie.MovementState.STILL_LEFT);
            else
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
