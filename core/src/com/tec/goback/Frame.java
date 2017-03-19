package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
 * Created by sergiohernandezjr on 16/02/17.
 */

public class Frame implements Screen {
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

    //Main app class
    public Viewport view;

    //Textures
    private Texture pauseButton;//Image that holds creators photos and back button

    public SpriteBatch batch;

    //Stage
    public Stage frameStage;

    public Frame(App app) {
        this.app = app;
    }

    @Override
    public void show() {
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
        pauseButton = new Texture("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTPause.png");
        backgroundCharactersInit();
    }

    private void backgroundCharactersInit() {
        //Override this method always
    }

    private void objectInit() {
        batch = new SpriteBatch();
        frameStage = new Stage(view, batch);

        //Pause button
        TextureRegionDrawable pauseBtnTrd = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton pauseImgBtn = new ImageButton(pauseBtnTrd);

        pauseImgBtn.setPosition(WIDTH-pauseImgBtn.getWidth()-10, HEIGHT-pauseImgBtn.getHeight()-10);
        frameStage.addActor(pauseImgBtn);


        pauseImgBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LoaderState toRet = LoaderState.LEVEL0;

                int d = pref.getInteger("level");
                switch (d){
                    case 1:
                        toRet = LoaderState.LEVEL1;
                        break;
                    case 2:
                        toRet = LoaderState.LEVEL2;
                        break;
                    case 3:
                        toRet = LoaderState.LEVEL3;
                        break;

                }
                app.setScreen(new Fade(app, LoaderState.PAUSE));
            }
        });

        //pass the Stage
        Gdx.input.setInputProcessor(frameStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
    }



    @Override
    public void render(float delta) {
        cls();
        frameStage.draw();
    }
    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        view.update(width,height);
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
