package com.tec.goback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

/*
Created by gerry on 5/1/17.
 */

class LevelEND extends Frame {

    private Dialogue dialogue;
    private GameState state;

    private Stage stage;

    private Image bg;

    private ArrayList<Image> arr = new ArrayList<Image>();
    private Image newsPaper;
    private Image photo;
    private Image note;
//    private Image bone;







    LevelEND(App app) {
        super(app, 1280, 720);
    }

    @Override
    public void show() {
        super.show();
        dialogue = new Dialogue(aManager);
        state = GameState.PLAYING;
        stage = new Stage(view, batch);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        actorInit();
    }

    private void actorInit(){
        Texture bgTx = new Texture("CLUES/CLUESBoneDisplay.png");
        Texture newsPaperTx = new Texture("CLUES/Newspaper/CLUESNewspaper.png");
        Texture photoTx = new Texture("CLUES/Photo/CLUESPhoto.png");
        Texture noteTx = new Texture("CLUES/Note/CLUESNote.png");
//        Texture boneTx = new Texture("CLUES/Bone/CLUESBone.png");

        bg = new Image(bgTx);

        newsPaper = new Image(newsPaperTx);
        photo = new Image(photoTx);
        note = new Image(noteTx);
//        bone = new Image(boneTx);

        arr.add(newsPaper); arr.add(photo); arr.add(note);

        newsPaper.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                newsPaper.moveBy(x - newsPaper.getWidth() / 2, y - newsPaper.getHeight() / 2);
            }
        });
        photo.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                photo.moveBy(x - photo.getWidth() / 2, y - photo.getHeight() / 2);
            }
        });
        note.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                note.moveBy(x - note.getWidth() / 2, y - note.getHeight() / 2);
            }
        });
//        bone.addListener(new DragListener() {
//            public void drag(InputEvent event, float x, float y, int pointer) {
//                bone.moveBy(x - bone.getWidth() / 2, y - bone.getHeight() / 2);
//            }
//        });

        newsPaper.setPosition(198,10);
        photo.setPosition(327,117);
        note.setPosition(410,10);

        stage.addActor(bg);
        stage.addActor(newsPaper);
        stage.addActor(photo);
        stage.addActor(note);
//        stage.addActor(bone);
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    @Override
    public void render(float delta) {
        cls();

        if(squaresFilled()) state = GameState.WON;

        if(state == GameState.PLAYING) {
            stage.draw();
        }else if(state == GameState.WON){
            stage.draw();
        }
    }

    private boolean squaresFilled(){
        boolean first = false, second = false, third = false;
        float x, y;
        for(Image i: arr){
            x = i.getX()+i.getWidth()/2; y = i.getY()+i.getHeight()/2;
            if( (x > 680 && x < 815) && (y > 25 && y <162) ) first = true;
            if( (x > 902 && x < 1030) && (y > 25 && y <162) ) second = true;
            if( (x > 1115 && x < 1247) && (y > 25 && y <162) ) third = true;
        }
        return (first && second) && third;
    }

    @Override
    public void resize(int width, int height){
            view.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}
