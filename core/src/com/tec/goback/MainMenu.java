package com.tec.goback;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by gerry on 2/16/17.
 */
public class MainMenu implements Screen {

    //Main app class
    private final App app;

    //Screen sizes
    public static final float WIDTH = 1200;
    public static final float HEIGHT = 800;
    public static final float HALFW = 600;
    public static final float HALFH = 400;

    //Camera
    private OrthographicCamera camera;
    
    
    //View
    private Viewport view;

    //Textures
    private Texture background; //Background that changes with progress
    private Texture buttons; //Buttons PNG on top of the background


    private SpriteBatch batch;

    //Stage
    private Stage mainMenuStage;



    public MainMenu(App app) {
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
        background = new Texture("/Interfaces/MENU.png");
        buttons = new Texture(".png");
    }

    private void objectInit() {
        batch = new SpriteBatch();
        mainMenuStage = new Stage(view, batch);
        Image bg = new Image(background);
        Image bt = new Image(buttons);

    }



    @Override
    public void render(float delta) {

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
