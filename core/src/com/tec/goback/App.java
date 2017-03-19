package com.tec.goback;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;


public class App extends Game {

	private final AssetManager aManager;

	public App(){
		this.aManager = new AssetManager();
	}

	@Override
	public void create () {
		setScreen(new SplashScreen(this));
		aManager.load("HARBOR/GoBackHARBOR0.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEBottomDisplay.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEMapList.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEQuit.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSETopDisplay.png", Texture.class);
		aManager.load("Interfaces/PAUSE/PAUSEback.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDMusicON.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDMusic.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDSoundON.png", Texture.class);
		aManager.load("Interfaces/SOUND/SOUNDSound.png", Texture.class);
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
