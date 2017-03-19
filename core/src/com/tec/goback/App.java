package com.tec.goback;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class App extends Game {

	private final AssetManager aManager;

	public App(){
		this.aManager = new AssetManager();
	}

	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}

	// Para que las otras pantallas usen el assetManager
    public AssetManager getAssetManager() {
        return assetManager;
    }

	@Override
    public void dispose() {
        super.dispose();
        assetManager.clear();
    }
}
