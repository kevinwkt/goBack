package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

/**
 *
 * Created by gerry on 3/18/17.
 */
public class Fade implements Screen {
    private final App app; //Main app class

    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private AssetManager manager;  // AssetManager
    private LoaderState loaderState; //game state

    private OrthographicCamera camera;
    private Viewport view;
    private SpriteBatch batch;

    public Fade(App app, LoaderState loaderState) {
        this.app = app;
        this.loaderState = loaderState;
    }

    @Override
    public void show() {
        manager = app.getAssetManager();
        cameraInit();
        batch = new SpriteBatch();
        superLoad();
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        cls();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.end();
        //Update
        goNextScreen();
    }

    @Override
    public void resize(int width, int height) {

    }

    private void superLoad(){
            switch (loaderState) {
                case ABOUT:
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    manager.load("Interfaces/ABOUT/ABOUTCast.png", Texture.class);
                    manager.load("Interfaces/ABOUT/ABOUTBack.png", Texture.class);
                    break;
                case MAINMENU:
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    manager.load("Interfaces/MENU/ABOUT.png", Texture.class); 
                    manager.load("Interfaces/MENU/ARCADE.png", Texture.class);
                    manager.load("Interfaces/MENU/SOUND.png", Texture.class);
                    manager.load("Interfaces/MENU/STORY.png", Texture.class);
                    manager.load("Interfaces/MENU/TITLE.png", Texture.class);
                    break;
                case SOUNDSETTINGS:
                    manager.load("Interfaces/SOUND/SOUNDMusicON.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDMusic.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDSoundON.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDSound.png", Texture.class);
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDBack.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDDecoration.png", Texture.class);
                    break;
                case LEVEL0:
                    manager.load("INTRO/INTROBackground.png", Texture.class);
                    manager.load("INTRO/INTROBoat.png", Texture.class);
                    manager.load("INTRO/INTROOar.png", Texture.class);
                    break;
                case LEVEL1:

                    break;
                case LEVEL2:

                    break;
                case LEVEL3:

                    break;
                case BOSS1:

                    break;
                case BOSS2:

                    break;
                case BOSS3:

                    break;
                case ARCADE:

                    break;
            }
    }

    private void goNextScreen() {
        if (manager.update()) { // Done loading?
            switch (loaderState) {
                case ABOUT:
                    app.setScreen(new About(app));
                    break;
                case MAINMENU:
                    app.setScreen(new MainMenu(app));
                    break;
                case SOUNDSETTINGS:
                    app.setScreen(new SoundSettings(app));
                    break;
                case LEVEL0:
                    
                    break;
                case LEVEL1:

                    break;
                case LEVEL2:

                    break;
                case LEVEL3:

                    break;
                case BOSS1:

                    break;
                case BOSS2:

                    break;
                case BOSS3:

                    break;
                case ARCADE:

                    break;
            }
        }
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        // texturaCargando.dispose();
    }
}
