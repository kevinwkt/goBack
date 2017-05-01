package com.tec.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

/*
Created by gerry on 5/1/17.
 */

public class LevelEND extends Frame {

    private Input input;
    private Dialogue dialogue;
    private GameState state;

    //Textures
    private Texture bg;

    LevelEND(App app, float WIDTH_MAP, float HEIGHT_MAP) {
        super(app, WIDTH_MAP, HEIGHT_MAP);
    }

    @Override
    public void show() {
        input = new Input();
        dialogue = new Dialogue(aManager);
        state = GameState.PLAYING;

        Gdx.input.setInputProcessor(input);
        Gdx.input.setCatchBackKey(true);

        textureInit();
    }

    private void textureInit(){
        //bg =
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    @Override
    public void render(float delta) {
        cls();
        batch.begin();
        if(state == GameState.PLAYING){

        }

    }

    @Override
    public void resize(int width, int height) {
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

    private class Input implements InputProcessor {
        private Vector3 v = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button){
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
