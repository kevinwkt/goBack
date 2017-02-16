package com.tec.goback;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    //Scenes
    private Stage menuScene;



    public MainMenu(App app) {
        this.app = app;
    }

    @Override
    public void show() {
        cameraInit();
        objectInit();
        spriteInit();
    }

    private void objectInit() {
    }

    private void cameraInit() {
        
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
