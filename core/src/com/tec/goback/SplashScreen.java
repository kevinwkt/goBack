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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Time;

/**
 * Created by gerry on 2/17/17.
 */

public class SplashScreen implements Screen {
    //Main app class
    private final App app;

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
    private Texture splash;

    //SpriteBatch
    private SpriteBatch batch;

    //Stage
    private Stage splashStage;

    //Time 
    private long time;

    //Constructor recieves main App class (implements Game)
    public SplashScreen(App app) {
        this.app = app;
    }

    //Call other methods because FIS
    @Override
    public void show() {
        time = TimeUtils.millis();
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
        splash = new Texture("Interfaces/splash.png");
    }

    private void objectInit() { //MAYBE PROBABLY WORKING
        batch = new SpriteBatch();
        splashStage = new Stage(view, batch);

        //splash hace Image
        Image splashImg = new Image(splash);
        splashImg.setPosition(HALFW-splashImg.getWidth()/2, HALFH-splashImg.getHeight()/2);
        splashStage.addActor(splashImg);


        //pass the Stage
        Gdx.input.setInputProcessor(splashStage);

        //let go of android device back key
        Gdx.input.setCatchBackKey(false);
    }



    @Override
    public void render(float delta) {
        splashStage.draw();
        if((time - TimeUtils.millis() > 2000)){
            app.setScreen(new MainMenu(app));
        }
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
