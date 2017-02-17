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
 * Created by kevin on 2/16/2017.
 */

public class Pause implements Screen {

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
    private Texture background; //Background

    private Texture bottom; //Button
    private Texture map; //Button
    private Texture quitBton; //Button
    private Texture top; //Button
    private Texture backBton; //Button




    //SpriteBatch
    private SpriteBatch batch;

    //Stage
    private Stage mainMenuStage;


    //Constructor recieves main App class (implements Game)
    public Pause(App app) {
        this.app = app;
    }

    //Call other methods because readability
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

        background = new Texture("HARBOR/GoBackHARBOR0.png");
        bottom = new Texture("Interfaces/PAUSE/PAUSEBottomDisplay.png");
        map = new Texture("Interfaces/PAUSE/PAUSEMapList.png");
        quitBton = new Texture("Interfaces/PAUSE/PAUSEBQuit.png");
        top = new Texture("Interfaces/PAUSE/PAUSETopDisplay.png");
        backBton = new Texture("Interfaces/PAUSE/PAUSEback.png");
    }

    private void objectInit() { //MAYBE PROBABLY (NOT) WORKING

        batch = new SpriteBatch();
        mainMenuStage = new Stage(view, batch);

        //background hace Image
        Image backgroundImg = new Image(background);
        backgroundImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        mainMenuStage.addActor(backgroundImg);


        //bottom
        Image bottomImg=new Image(bottom);
        bottomImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        mainMenuStage.addActor(bottomImg);


        //map
        Image mapImg=new Image(map);
        mapImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        mainMenuStage.addActor(mapImg);


        //quitBton
        TextureRegionDrawable quitBtonTrd = new TextureRegionDrawable(new TextureRegion(quitBton));
        ImageButton quitBtonImg = new ImageButton(quitBtonTrd);

        quitBtonImg.setPosition(HALFW-quitBtonImg.getWidth()/2, 3*HEIGHT/4-quitBtonImg.getHeight()/2);
        mainMenuStage.addActor(quitBtonImg);

        quitBtonImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new SoundSettings(app));
            }
        });




        //top
        Image topImg=new Image(top);
        topImg.setPosition(HALFW-backgroundImg.getWidth()/2, HALFH-backgroundImg.getHeight()/2);
        mainMenuStage.addActor(topImg);


        //title
        TextureRegionDrawable backBtonTrd = new TextureRegionDrawable(new TextureRegion(backBton));
        ImageButton backBtonImg = new ImageButton(backBtonTrd);

        backBtonImg.setPosition(HALFW-backBtonImg.getWidth()/2, 3*HEIGHT/4-backBtonImg.getHeight()/2);
        mainMenuStage.addActor(backBtonImg);

        quitBtonImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new SoundSettings(app));
            }
        });

        //pass the Stage
        Gdx.input.setInputProcessor(mainMenuStage);



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