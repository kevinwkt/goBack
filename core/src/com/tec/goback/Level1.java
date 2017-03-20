package com.tec.goback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by sergiohernandezjr on 18/03/17.
 */
public class Level1 extends Frame {
    // Mario
    private Sophie sophie;
    private Texture sophieTexture;

    // Música
    private Music bgMusic;
    private Sound fx;

    //BG
    Texture background;


    public Level1(App app) {
        super(app);
    }

    private GameState state = GameState.PLAYING;

    @Override
    public void show() {
        super.show();
        textureInit();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(super.camera.combined);
        cls();
        batch.begin();
        batch.draw(background,0,0);
        if (state==GameState.PAUSED) {
            Gdx.input.setInputProcessor(pauseStage);
            pauseStage.draw();
        }else if(state==GameState.PLAYING){
            Gdx.input.setInputProcessor(frameStage);
            //super.frameStage.draw();

            sophie.draw(batch);
            sophie.sprite.setPosition(HALFW-sophie.sprite.getWidth()/2, HALFH-sophie.sprite.getHeight()/2);
        }
        batch.end();
    }
    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void textureInit() {
        sophieTexture = new Texture("Squirts/Sophie/SOPHIEWalk.png");
        sophie = new Sophie(sophieTexture, 200,200);



        //Background
        background = new Texture("HARBOR/GoBackHARBORPanoramic.png");
        /*Image bgImg = new Image(background);
        bgImg.setPosition(HALFW-bgImg.getWidth()/2, HALFH-bgImg.getHeight()/2);
        aboutScreenStage.addActor(bgImg);

        //Cast  overlay image
        Image castImg = new Image(castOverlay);
        castImg.setPosition(HALFW-castImg.getWidth()/2, HALFH-castImg.getHeight()/2);
        aboutScreenStage.addActor(castImg);*/
    }
}
