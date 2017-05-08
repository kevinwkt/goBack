package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * SplashScreen
 */

class SplashScreen implements Screen
{

    private final App app; //Main app class
    private AssetManager aManager;

    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private float upTime = 1.2f;

    // Logo del tec
    private Texture textureLogo;
    private Sprite spriteLogo;

    SplashScreen(App app) {
        this.app = app;
    }

    @Override
    public void show() {
        cameraInit();
        textureInit();
        batch = new SpriteBatch();
        logoScale();
        aManager = app.getAssetManager();
        aManager.load("INTRO/INTROBackground.png",Texture.class);
        aManager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
        aManager.load("WOODS/WOODSBeginning.png", Texture.class);
        aManager.load("Interfaces/GAMEPLAY/GobackCONSTPause.png", Texture.class);
        aManager.load("Interfaces/PAUSE/PAUSEBottomDisplay.png", Texture.class);
        aManager.load("Interfaces/PAUSE/PAUSEMapList.png", Texture.class);
        aManager.load("Interfaces/PAUSE/PAUSEStats.png", Texture.class);
        aManager.load("CLUES/Newspaper/CLUESNewspaper.png",Texture.class);
        aManager.load("CLUES/Newspaper/CLUESNewspaperDetail.png",Texture.class);
        aManager.load("CLUES/Photo/CLUESPhoto.png",Texture.class);
        aManager.load("CLUES/Photo/CLUESPhotoDetail.png",Texture.class);
        aManager.load("CLUES/Note/CLUESNote.png",Texture.class);
        aManager.load("CLUES/Note/CLUESNoteDetail.png",Texture.class);
        aManager.load("CLUES/Bone/CLUESBone.png",Texture.class);
        aManager.load("CLUES/Bone/CLUESBoneDetail.png",Texture.class);
        //Stats
        aManager.load("Interfaces/STATS/STATSback.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSBlueOrb.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSBlueOrbArrow.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSRedOrb.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSRedOrbArrow.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSYellowOrb.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSYellowOrbArrow.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSSophie.png", Texture.class);
        aManager.load("Interfaces/STATS/STATSSophieArrow.png", Texture.class);
        ///
        aManager.load("Interfaces/PAUSE/PAUSEQuit.png", Texture.class);
        aManager.load("Interfaces/PAUSE/PAUSETopDisplay.png", Texture.class);
        aManager.load("Interfaces/PAUSE/PAUSEback.png", Texture.class);
        aManager.load("Interfaces/SOUND/SOUNDMusicON.png", Texture.class);
        aManager.load("Interfaces/SOUND/SOUNDMusic.png", Texture.class);
        aManager.load("Interfaces/SOUND/SOUNDSoundON.png", Texture.class);
        aManager.load("Interfaces/SOUND/SOUNDSound.png", Texture.class);
        aManager.load("CONSTANT/CONSTDialogueBox.png", Texture.class);
        aManager.load("Interfaces/GAMEPLAY/ARCADE/ARCADESophie.png", Texture.class);

        //aManager.finishLoading();
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
    }

    private void textureInit() {
        textureLogo = new Texture("Interfaces/splash.png");
        spriteLogo = new Sprite(textureLogo);
        spriteLogo.setPosition(WIDTH/2-spriteLogo.getWidth()/2, HEIGHT/2-spriteLogo.getHeight()/2);
    }

    private void logoScale() {
        float cameraScale = WIDTH / HEIGHT;
        float screenScale = 1.0f*Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float scale = cameraScale / screenScale;
        spriteLogo.setScale(scale, 1);
    }


    @Override
    public void render(float delta) {
        cls();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        spriteLogo.draw(batch);
        batch.end();

        upTime -= delta;
        if (upTime<=0 && aManager.update()) {
            app.setScreen(new Fade(app, LoaderState.MAINMENU));
        }
    }

    @Override
    public void resize(int width, int height) {

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
        // Libera las texturas
        textureLogo.dispose();
    }
}
