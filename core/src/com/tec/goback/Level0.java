package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by pablo on 18/03/17.
 */

public class Level0 implements Screen{

    private final App app;
    private AssetManager aManager;

    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private Viewport view;

    // camera
    private OrthographicCamera camera;

    // textures
    private Texture background;
    private Texture boat;
    private Texture oar;

    private SpriteBatch batch;

    private Stage level0Stage;

    // boat atributes
    private Sprite boatSprite;
    private float boatXPosition;
    boolean boatRotateLeft = true;

    //oar atribute
    private Sprite oarSprite;
    private float oarXPosition;
    boolean oarRotateLeft = false;

    //textboxes


    public Level0(App app){
        this.app = app;
        this.aManager = app.getAssetManager();
    }

    @Override
    public void show() {
        cameraInit();
        texturesInit();
        objectInit();
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        camera.position.set(HALFW,HALFH,0);
        camera.update();
        view = new StretchViewport(WIDTH,HEIGHT);

    }

    private void texturesInit() {
        background = aManager.get("INTRO/INTROBackground.png");
        boat = aManager.get("INTRO/INTROBoat.png");
        oar = aManager.get("INTRO/INTROOar.png");
    }

    private void objectInit() {
        batch = new SpriteBatch();

        level0Stage = new Stage(view,batch);

        // background
        Image backImg = new Image(background);
        backImg.setPosition(WIDTH/2-backImg.getWidth()/2, HEIGHT/2-backImg.getHeight()/2);
        level0Stage.addActor(backImg);

        //boat

        boatSprite = new Sprite(boat);
        boatXPosition = -boatSprite.getWidth();

        // oar
        oarSprite = new Sprite(oar);
        oarSprite.setRotation(-13);
        oarXPosition = -oarSprite.getWidth();

        Gdx.input.setInputProcessor(level0Stage);
        Gdx.input.setCatchBackKey(true);

    }



    @Override
    public void render(float delta) {
        moveObject(delta,boatSprite, boatXPosition, boatRotateLeft, "boat");
        moveObject(delta,oarSprite, oarXPosition, oarRotateLeft, "oar");
        batch.begin();
        batch.draw(background,0,0);
        boatSprite.draw(batch);
        boatSprite.setPosition(boatXPosition,HEIGHT/2-boatSprite.getHeight()/2);
        oarSprite.draw(batch);
        oarSprite.setPosition(oarXPosition,HEIGHT/2-oarSprite.getHeight()/2-80);

        batch.end();
    }

    private void moveObject(float delta, Sprite sprite, float xPosition, boolean rotateLeft, String type) {

        xPosition += delta*40;
        if(rotateLeft){
            sprite.rotate((float) 0.25);
        }else{
            sprite.rotate((float) -0.25);
        }
        if(sprite.getRotation() > 5 ){
            rotateLeft = false;
        }else if(sprite.getRotation() < -20){
            rotateLeft = true;
        }
        if(type.equals("boat")){
            boatXPosition = xPosition;
            boatRotateLeft = rotateLeft;
        }else{
            oarXPosition = xPosition;
            oarRotateLeft = rotateLeft;
        }


    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
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
        aManager.unload("INTRO/INTROBackground.png");
        aManager.unload("INTRO/INTROBoat.png");
        aManager.unload("INTRO/INTROOar.png");
    }

}
