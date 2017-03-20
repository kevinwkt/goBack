package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//
import java.util.ArrayList;

/**
 * Created by gerry on 2/18/17.
 */
public class Arcade extends Frame implements Screen {

    //Arraylist
    private ArrayList<OrbAttack> myAttacks;

    //CURRENT COLOR ORB
    private orbColor currentColor = orbColor.YELLOW;

    //MOTHER FUCKER
    Preferences pref=Gdx.app.getPreferences("getLevel");
    private int d;

    // Music
    private Music bgMusic;
    private Sound fx;

    //Textures
    private Texture background; //Background
    private Texture sophieTexture;
    private Texture orbred;
    private Texture eyesred;
    private Texture eyesblue;
    private Texture eyesyellow;
    private Texture orbblue;
    private Texture orbyellow;

    public static final float WIDTH_MAP = 1280;
    public static final float HEIGHT_MAP = 720;

    public Arcade(App app) {
        super(app, WIDTH_MAP,HEIGHT_MAP);
    }

    private GameState state = GameState.PLAYING;

    //Call other methods because FIS
    @Override
    public void show() {
        d= pref.getInteger("level");
        super.show();
        textureInit();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());// new input joystick
        Gdx.input.setCatchBackKey(true); //Not important
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(super.camera.combined);
        cls();
        batch.begin();


        batch.draw(background,0,0);
        batch.draw(sophieTexture,640,200);

        switch (d){
            case 1:
                batch.draw(orbyellow,0,0);
                batch.draw(eyesyellow,0,0);
                break;
            case 2:
                batch.draw(orbyellow,0,0);
                batch.draw(eyesyellow,0,0);
                batch.draw(orbblue,0,0);
                batch.draw(eyesblue,0,0);
                break;
            case 3:
                batch.draw(orbyellow,0,0);
                batch.draw(eyesyellow,0,0);
                batch.draw(orbblue,0,0);
                batch.draw(eyesblue,0,0);
                batch.draw(orbred,0,0);
                batch.draw(eyesred,0,0);
                break;
        }
        if (state==GameState.PAUSED) {
            //Gdx.input.setInputProcessor(pauseStage);
            pauseStage.draw();
        }
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

    private void textureInit() {
        sophieTexture= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADESophie.png");
        background = new Texture("HARBOR/GoBackHARBORPanoramic.png");

        switch (d){
            case 1:
                orbyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                break;
            case 2:
                orbyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orbblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
                eyesblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png");
                break;
            case 3:
                orbyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png");
                eyesyellow= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png");
                orbblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png");
                eyesblue= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png");
                orbred= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png");
                eyesred= new Texture("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrbEyes.png");
                break;
        }
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
            Gdx.app.log("color: ", currentColor +" ");
            if (/*ON THE BUTTON TO CHANGE COLORS*/true){
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
            else {
                switch (d) {
                    case 1:
                        myAttacks.add(new OrbAttack(orbyellow, 0, 0, screenX, screenY));
                        break;
                    case 2:
                        switch (currentColor) {
                            case BLUE:
                                myAttacks.add(new OrbAttack(orbblue, 0, 0, screenX, screenY));
                                break;
                            case YELLOW:
                                myAttacks.add(new OrbAttack(orbyellow, 0, 0, screenX, screenY));
                                break;
                        }
                        break;
                    case 3:
                        switch (currentColor) {
                            case BLUE:
                                myAttacks.add(new OrbAttack(orbblue, 0, 0, screenX, screenY));
                                break;
                            case YELLOW:
                                myAttacks.add(new OrbAttack(orbyellow, 0, 0, screenX, screenY));
                                break;
                            case RED:
                                myAttacks.add(new OrbAttack(orbred, 0, 0, screenX, screenY));
                                break;
                        }
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
