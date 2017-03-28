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


/**
 *
 * Created by gerry on 3/18/17.
 */
class Fade implements Screen {
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

    private MainMenu menu;

    public Fade(App app, LoaderState loaderState) {
        this.app = app;
        this.loaderState = loaderState;
    }

    public Fade(App app, LoaderState loaderState, MainMenu menu) {
        this.app = app;
        this.loaderState = loaderState;
        this.menu = menu;
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
                    manager.load("INTRO/INTROBackground.png", Texture.class);
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    //manager.load(".png", Texture.class); //Level3
                    manager.load("Interfaces/ABOUT/ABOUTCast.png", Texture.class);
                    manager.load("Interfaces/ABOUT/ABOUTBack.png", Texture.class);
                    manager.load("MUSIC/GoBackMusicMainMenu.mp3", Music.class);
                    break;
                case MAINMENU:
                    manager.load("INTRO/INTROBackground.png", Texture.class);
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    //manager.load(".png", Texture.class); //Level3
                    manager.load("Interfaces/MENU/ABOUT.png", Texture.class); 
                    manager.load("Interfaces/MENU/ARCADE.png", Texture.class);
                    manager.load("Interfaces/MENU/SOUND.png", Texture.class);
                    manager.load("Interfaces/MENU/STORY.png", Texture.class);
                    manager.load("Interfaces/MENU/TITLE.png", Texture.class);
                    manager.load("MUSIC/GoBackMusicMainMenu.mp3", Music.class);
                    break;
                case SOUNDSETTINGS:
                    manager.load("INTRO/INTROBackground.png", Texture.class);
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    //manager.load(".png", Texture.class); //Level3
                    manager.load("Interfaces/SOUND/SOUNDMusicON.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDMusic.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDSoundON.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDSound.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDBack.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDDecoration.png", Texture.class);
                    break;
                case LEVEL0:
                    manager.load("MUSIC/GoBackMusicMainMenu.mp3", Music.class);
                    manager.load("INTRO/INTROBackground.png", Texture.class);
                    manager.load("INTRO/INTROBoat.png", Texture.class);
                    manager.load("INTRO/INTROOar.png", Texture.class);
                    manager.load("INTRO/INTROAbundioDialogue.png", Texture.class);
                    manager.load("INTRO/INTROAbundioDialogueBlink.png", Texture.class);
                    break;
                case LEVEL1:
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png",Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill00.png", Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill01.png", Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill02.png", Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill03.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieBlink.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieConcern.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieNormal.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieSurprise.png", Texture.class);
                    manager.load("CLUES/Newspaper/CLUESNewspaper.png",Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaBody.png",Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaBackLeg.png",Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaFrontLeg.png",Texture.class);
                    manager.load("MUSIC/GoBackMusicLevel1.mp3", Music.class);
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
                    manager.load("MUSIC/GoBackMusicArcade.mp3", Music.class);
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
                    //manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    //manager.load(".png", Texture.class); //Level3
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrbEyes.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrbEyes.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrbEyes.png", Texture.class);
                    manager.load("PELLET/ATAQUEYellowPellet.png", Texture.class);
                    manager.load("PELLET/ATAQUERedPellet.png", Texture.class);
                    manager.load("PELLET/ATAQUEBluePellet.png", Texture.class);
                    break;
            }
    }

    private void goNextScreen() {
        if (manager.update()) { // Done loading?
            switch (loaderState) {
                case ABOUT:
                    app.setScreen(new About(app, menu));
                    break;
                case MAINMENU:
                    if(menu!=null) {
                        app.setScreen(menu);
                    }else {
                        app.setScreen(new MainMenu(app));
                    }
                    break;
                case SOUNDSETTINGS:
                    app.setScreen(new SoundSettings(app, menu));
                    break;
                case LEVEL0:
                    app.setScreen(new Level0(app));
                    break;
                case LEVEL1:
                    app.setScreen(new Level1(app));
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
                    app.setScreen(new Arcade(app));
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
