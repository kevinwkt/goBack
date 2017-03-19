package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by gerry on 3/18/17.
 */
class Fade extends Screen
{
    private final App app; //Main app class

    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private AssetManager manager;  // AssetManager
    private LoaderState loaderState; //game state

    private OrthographicCamera camera;
    private Sprite
    private Viewport view;

    public Fade(App app, LoaderState loaderState) {
        this.app = app;
        this.loaderState = loaderState;
    }

    @Override
    public void show() {
        manager = app.getAssetManager();
        cameraInit();
        objectInit();

        // switch (loaderState) {
        //     case MENU:
        //         cargarRecursosMenu();
        //         break;
        //     case NIVEL_MARIO:
        //         cargarRecursosMario();
        //         break;
        //     case NIVEL_WHACK_A_MOLE:
        //         cargarRecursosWhackAMole();
        //         break;
        // }
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT);
    }

    private void textureInit() {
        background = new Texture("HARBOR/GoBackHARBOR0.png");
        aboutBtn = new Texture("Interfaces/MENU/ABOUT.png"); 
        arcadeBtn = new Texture("Interfaces/MENU/ARCADE.png");
        soundBtn = new Texture("Interfaces/MENU/SOUND.png");
        storyBtn = new Texture("Interfaces/MENU/STORY.png");
        title = new Texture("Interfaces/MENU/TITLE.png");
    }

    private void objectInit() {}

/*
    LEVEL0,
    LEVEL1,
    LEVEL2,
    LEVEL3,
    BOSS1,
    BOSS2,
    BOSS3,
    ARCADE
*/
    private void loadLevel0() {
        // manager.load("whackamole/fondoPasto.jpg", Texture.class);
        // manager.load("whackamole/hoyo.png", Texture.class);
        // manager.load("whackamole/mole.png", Texture.class);
        // manager.load("whackamole/estrellasGolpe.png", Texture.class);
        // manager.load("whackamole/mazo.png", Texture.class);
        // manager.load("whackamole/golpe.mp3", Sound.class);
        // manager.load("whackamole/risa.mp3", Sound.class);
        // manager.load("comun/btnPausa.png", Texture.class);
        // manager.load("whackamole/btnSalir.png", Texture.class);
        // manager.load("whackamole/btnReintentar.png", Texture.class);
        // manager.load("whackamole/btnContinuar.png", Texture.class);
    }

    private void cargarRecursosMario() {
        // manager.load("mario/marioSprite.png", Texture.class);
        // manager.load("mario/mapaMario.tmx", TiledMap.class);
        // manager.load("mario/marioBros.mp3",Music.class);
        // manager.load("mario/moneda.mp3",Sound.class);
        // manager.load("mario/padBack.png", Texture.class);
        // manager.load("mario/padKnob.png", Texture.class);
    }

    private void cargarRecursosMenu() {
        // manager.load("menu/btnJugarMario.png", Texture.class);
        // manager.load("menu/btnJugarMarioP.png", Texture.class);
        // manager.load("menu/btnJugarRunner.png", Texture.class);
        // manager.load("menu/btnJugarRunnerP.png", Texture.class);
        // manager.load("menu/btnJugarWhackAMole.png", Texture.class);
        // manager.load("menu/btnJugarWhackAMoleP.png", Texture.class);
        // manager.load("menu/fondo.jpg", Texture.class);
    }

    @Override
    public void render(float delta) {
        cls();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.end();
        //Update
        goNextScreen();
    }

    private void goNextScreen() {
        if (manager.update()) { // Done loading?
            // switch (loaderState) {
            //     case MENU:
            //         juego.setScreen(new PantallaMenu(juego));   // 100% de carga
            //         break;
            //     case NIVEL_MARIO:
            //         juego.setScreen(new PantallaMario(juego));   // 100% de carga
            //         break;
            //     case NIVEL_WHACK_A_MOLE:
            //         juego.setScreen(new PantallaWhackAMole(juego));
            //         break;
            // }
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
    public void dispose() {
        // texturaCargando.dispose();
    }
}
