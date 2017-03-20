package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
 * Created by gerry on 2/18/17.
 */
public class MainMenu implements Screen {

    //Main app class
    private final App app;

    Preferences pref=Gdx.app.getPreferences("getLevel");

    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    //Camera
    private OrthographicCamera camera;

    //Viewport
    private Viewport view;

    //Textures
    private Texture background; //Image
    private Texture title; //Image

    private Texture aboutBtn; //Button
    private Texture arcadeBtn; //Button
    private Texture soundBtn; //Button
    private Texture storyBtn; //Button

    //SpriteBatch
    private SpriteBatch batch;

    //Stage
    private Stage mainMenuStage;

    private AssetManager aManager;

    //Constructor recieves main App class (implements Game)
    public MainMenu(App app) {
        this.app = app;
        this.aManager = app.getAssetManager();
    }

    //Call other methods because FIS
    @Override
    public void show() {
        //    -----------------------  TO GET INTO LEVEL 0 TEMPORAL OMG
        pref.putInteger("level",0);
        pref.flush();


        cameraInit();
        textureInit();
        objectInit();
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT);
    }

    private void textureInit() {
        background = aManager.get("HARBOR/GoBackHARBOR0.png");
        aboutBtn = aManager.get("Interfaces/MENU/ABOUT.png");
        arcadeBtn = aManager.get("Interfaces/MENU/ARCADE.png");
        soundBtn = aManager.get("Interfaces/MENU/SOUND.png");
        storyBtn = aManager.get("Interfaces/MENU/STORY.png");
        title = aManager.get("Interfaces/MENU/TITLE.png");
    }

    private void objectInit() { //MAYBE PROBABLY WORKING

        //background hace Image
        Image backgroundImg = new Image(background);
        batch = new SpriteBatch();
        mainMenuStage = new Stage(view, batch);
        backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        mainMenuStage.addActor(backgroundImg);

        //title
        Image titleImg = new Image(title);
        titleImg.setPosition(2, (HALFH-titleImg.getHeight()/2)+80);
        mainMenuStage.addActor(titleImg);

        //aboutBtn hace ImageButton
        TextureRegionDrawable aboutBtnTrd = new TextureRegionDrawable(new TextureRegion(aboutBtn));
        ImageButton aboutBtnImg = new ImageButton(aboutBtnTrd);

        aboutBtnImg.setPosition(160, 10);
        mainMenuStage.addActor(aboutBtnImg);

        aboutBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new Fade(app, LoaderState.ABOUT));
            }
        });

        //soundBtn
        TextureRegionDrawable soundBtnTrd = new TextureRegionDrawable(new TextureRegion(soundBtn));
        ImageButton soundBtnImg = new ImageButton(soundBtnTrd);

        soundBtnImg.setPosition(10, 10);
        mainMenuStage.addActor(soundBtnImg);

        soundBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new Fade(app, LoaderState.SOUNDSETTINGS));
            }
        });

        //storyBtn
        TextureRegionDrawable storyBtnTrd = new TextureRegionDrawable(new TextureRegion(storyBtn));
        ImageButton storyBtnImg = new ImageButton(storyBtnTrd);

        storyBtnImg.setPosition(HALFW-storyBtnImg.getWidth()/2+190+280, HALFH-storyBtnImg.getHeight()/2-75);
        mainMenuStage.addActor(storyBtnImg);

        storyBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LoaderState next;

                int d= pref.getInteger("level");
                Gdx.app.log("concha",""+d);
                switch (d){
                    case 1:
                        next = LoaderState.LEVEL1;
                       break;
                    case 2:
                        next = LoaderState.LEVEL2;
                        break;
                    case 3:
                        next = LoaderState.LEVEL3;
                       break;
                    case 4:
                        next = LoaderState.LEVEL3;
                        break;
                    default:
                        next = LoaderState.LEVEL0;
                }

                app.setScreen(new Fade(app, next));

            }
        });

        //arcadeBtn
        TextureRegionDrawable arcadeBtnTrd = new TextureRegionDrawable(new TextureRegion(arcadeBtn));
        ImageButton arcadeBtnImg = new ImageButton(arcadeBtnTrd);

        arcadeBtnImg.setPosition((HALFW-arcadeBtnImg.getWidth()/2)+190, HALFH-arcadeBtnImg.getHeight()/2-80);
        mainMenuStage.addActor(arcadeBtnImg);

        arcadeBtnImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new Arcade(app));
            }
        });

        //pass the Stage
        Gdx.input.setInputProcessor(mainMenuStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
    }



    @Override
    public void render(float delta) {
        cls();
        mainMenuStage.draw();
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
