package com.tec.goback;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;


public class App extends Game {

	private final AssetManager aManager;

	public App(){
		this.aManager = new AssetManager();
	}

	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}

    public AssetManager getAssetManager() {
        return aManager;
    }

	@Override
    public void dispose() {
        super.dispose();
        aManager.clear();
    }
}
