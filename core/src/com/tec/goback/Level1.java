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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by sergiohernandezjr on 18/03/17.
 */
public class Level1 extends Frame {
    // Mario
    private Sophie sophie;
    private Texture sophieTexture;

    // Música
    private Music bgMusic;
    private Sound fx;

    //BG
    Texture background;

    public static final float WIDTH_MAP = 3840;
    public static final float HEIGHT_MAP = 720;

    public Level1(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    private GameState state = GameState.PLAYING;

    @Override
    public void show() {
        super.show();
        textureInit();
        sophie.setMovementState(Sophie.MovementState.WAKING);

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(super.camera.combined);
        cls();
        batch.begin();
        batch.draw(background,0,0);
        updateCamera();
        if (state==GameState.PAUSED) {
            //Gdx.input.setInputProcessor(pauseStage);
            pauseStage.draw();
        }else if(state==GameState.PLAYING){
            //Gdx.input.setInputProcessor(frameStage);
            sophie.draw(batch);
            sophie.sprite.setPosition(HALFW-sophie.sprite.getWidth()/2, HALFH-sophie.sprite.getHeight()/2);
        }
        batch.end();
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
        sophie = new Sophie(sophieTexture, 200,200);

        //Background
        background = new Texture("HARBOR/GoBackHARBORPanoramic.png");
        /*Image bgImg = new Image(background);
        bgImg.setPosition(HALFW-bgImg.getWidth()/2, HALFH-bgImg.getHeight()/2);
        aboutScreenStage.addActor(bgImg);

        //Cast  overlay image
        Image castImg = new Image(castOverlay);
        castImg.setPosition(HALFW-castImg.getWidth()/2, HALFH-castImg.getHeight()/2);
        aboutScreenStage.addActor(castImg);*/
    }

    private class ProcesadorEntrada implements InputProcessor {
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
            if (v.x>=HALFH) {
                sophie.setMovementState(Sophie.MovementState.MOVE_RIGHT);
            } else {
                sophie.setMovementState(Sophie.MovementState.MOVE_LEFT);
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            sophie.setMovementState(Sophie.MovementState.STILL);
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
