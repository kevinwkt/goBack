package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by pablo on 16/02/17.
 */

public class SoundSettings implements Screen{

    private final App app;

    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;


    private Viewport view;

    // textures
    private Texture background;
    private Texture backButton;
    private Texture muteMusicOffButton;
    private Texture muteMusicOnButton;
    private Texture muteFXOnButton;
    private Texture muteFXOffButton;
    private Texture soundDecoration;


    // camera
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Stage soundSettingsStage;

    public SoundSettings(App app) {
        this.app = app;
    }


    @Override
    public void show() {
        cameraInit();
        textureInit();
        objectInit();



    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        camera.position.set(HALFW,HALFH,0);
        camera.update();

        view = new StretchViewport(WIDTH,HEIGHT);
    }

    private void textureInit() {
        background = new Texture("HARBOR/GoBackHARBOR0.png");
        backButton = new Texture("Interfaces/SOUND/SOUNDBack.png");
        muteMusicOffButton = new Texture("Interfaces/SOUND/SOUNDMusic.png");
        muteMusicOnButton = new Texture("Interfaces/SOUND/SOUNDMusicON.png");
        muteFXOnButton = new Texture("Interfaces/SOUND/SOUNDSoundON.png");
        muteFXOffButton = new Texture("Interfaces/SOUND/SOUNDSound.png");
        soundDecoration = new Texture("Interfaces/SOUND/SOUNDDecoration.png");
    }

    private void objectInit() {
        batch = new SpriteBatch();
        soundSettingsStage = new Stage(view,batch);

        // background
        Image backImg = new Image(background);
        backImg.setPosition(WIDTH/2-backImg.getWidth()/2, HEIGHT/2-backImg.getHeight()/2);
        soundSettingsStage.addActor(backImg);

        // buttons

        TextureRegionDrawable backBtnPlay = new TextureRegionDrawable(new TextureRegion(backButton));
        ImageButton backBtnImg = new ImageButton(backBtnPlay);
        backBtnImg.setPosition(WIDTH/20, 11*HEIGHT/15);
        soundSettingsStage.addActor(backBtnImg);

        backBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MainMenu(app));
            }
        });

        TextureRegionDrawable muteMusicOffPlay = new TextureRegionDrawable(new TextureRegion(muteMusicOffButton));
        ImageButton muteMusicOffImg = new ImageButton(muteMusicOffPlay);
        muteMusicOffImg.setPosition(WIDTH/2, HEIGHT/2);

        TextureRegionDrawable muteMusicOnPlay = new TextureRegionDrawable(new TextureRegion(muteMusicOnButton));
        ImageButton muteMusicOnImg = new ImageButton(muteMusicOnPlay);
        muteMusicOnImg.setPosition(4*WIDTH/7, 8*HEIGHT/15);
        soundSettingsStage.addActor(muteMusicOnImg);

        muteMusicOnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //soundSettingsStage.addActor(muteMusicOffImg);
                Gdx.app.log("clicked","Cambiar Mute");

            }
        });

        /*TextureRegionDrawable muteFxOffPlay = new TextureRegionDrawable(new TextureRegion(muteFXOffButton));
        ImageButton muteFxOffImg = new ImageButton(muteFxOffPlay);
        muteFxOffImg.setPosition(WIDTH/2, HEIGHT/2);
        soundSettingsStage.addActor(muteFxOffImg);*/

        TextureRegionDrawable muteFxOnPlay = new TextureRegionDrawable(new TextureRegion(muteFXOnButton));
        ImageButton muteFxOnImg = new ImageButton(muteFxOnPlay);
        muteFxOnImg.setPosition(4*WIDTH/7, 4*HEIGHT/15);
        soundSettingsStage.addActor(muteFxOnImg);

        TextureRegionDrawable soundDecorationPlay = new TextureRegionDrawable(new TextureRegion(soundDecoration));
        ImageButton soundDecorationImg = new ImageButton(soundDecorationPlay);
        soundDecorationImg.setPosition(WIDTH/20, HEIGHT/2-soundDecorationImg.getHeight()/2);
        soundSettingsStage.addActor(soundDecorationImg);

        Gdx.input.setInputProcessor(soundSettingsStage);
        Gdx.input.setCatchBackKey(false);

    }

    @Override
    public void render(float delta) {
        cls();
        soundSettingsStage.draw();
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
}
