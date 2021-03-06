package mx.itesm.goback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 *
 * Created by gerry on 3/18/17.
 */
class Fade implements Screen {
    private final App app; //Main app class

    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private AssetManager manager;  // AssetManager
    private LoaderState loaderState; //game state

    private OrthographicCamera camera;
    private Viewport view;
    private SpriteBatch batch;
    private MainMenu menu;

    private Sprite loadIcon;
    private Sprite loadIconSmall;
    private float time = 0;

    BitmapFont font = new BitmapFont(Gdx.files.internal("fira.fnt"));
    GlyphLayout glyph = new GlyphLayout();

    Fade(App app, LoaderState loaderState) {
        this.app = app;
        this.loaderState = loaderState;
    }

    Fade(App app, LoaderState loaderState, MainMenu menu) {
        this.app = app;
        this.loaderState = loaderState;
        this.menu = menu;
    }

    @Override
    public void show() {
        manager = app.getAssetManager();
        cameraInit();
        batch = new SpriteBatch();
        superLoad();

        loadIcon = new Sprite(new Texture("Interfaces/GAMEPLAY/LOADING/LOADINGBigIcon.png"));
        loadIcon.setOrigin(150.5f, 150.5f);
        loadIcon.setPosition(ArcadeValues.pelletOriginX-loadIcon.getWidth()/2, ArcadeValues.pelletOriginY);

        loadIconSmall = new Sprite(new Texture("Interfaces/GAMEPLAY/LOADING/LOADINGSmallIcon.png"));
        loadIcon.setOrigin(38f, 36f);
        loadIconSmall.setPosition(120, -1);
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        time += delta;
        cls();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        glyph.setText(font, "Loading...", Color.WHITE, 710F, 30, true);
        font.draw(batch, glyph, 0, 20);
        loadIconSmall.setRotation(time*500);
        loadIcon.setPosition(ArcadeValues.pelletOriginX-loadIcon.getWidth()/2, ArcadeValues.pelletOriginY + 100*MathUtils.sin(time*5));

        loadIconSmall.draw(batch);
        //loadIcon.draw(batch);
        batch.end();
        goNextScreen();
    }

    @Override
    public void resize(int width, int height) {

    }

    private void superLoad(){
            switch (loaderState) {
                case ABOUT:
                    manager.load("INTRO/INTROBackground.png", Texture.class); //Level 0
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);//Level 1
                    manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    manager.load("WOODS/WOODSBeginning.png",Texture.class); //Level3
                    manager.load("WOODS/WOODSEnding.png",Texture.class);//Level 4
                    manager.load("Interfaces/ABOUT/ABOUTCast.png", Texture.class);
                    manager.load("Interfaces/ABOUT/ABOUTBack.png", Texture.class);
                    manager.load("MUSIC/GoBackMusicMainMenu.mp3", Music.class);
                    break;
                case MAINMENU:
                    manager.load("INTRO/INTROBackground.png", Texture.class); //Level 0
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);//Level 1
                    manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    manager.load("WOODS/WOODSBeginning.png",Texture.class); //Level3
                    manager.load("WOODS/WOODSEnding.png",Texture.class);//Level 4
                    manager.load("Interfaces/MENU/ABOUT.png", Texture.class); 
                    manager.load("Interfaces/MENU/ARCADEActive.png", Texture.class);
                    manager.load("Interfaces/MENU/ARCADEInactive.png", Texture.class);
                    manager.load("Interfaces/MENU/SOUND.png", Texture.class);
                    manager.load("Interfaces/MENU/STORY.png", Texture.class);
                    manager.load("Interfaces/MENU/TITLE.png", Texture.class);
                    manager.load("MUSIC/GoBackMusicMainMenu.mp3", Music.class);
                    break;
                case SOUNDSETTINGS:
                    manager.load("INTRO/INTROBackground.png", Texture.class); //Level 0
                    manager.load("HARBOR/GoBackHARBOR0.png", Texture.class);//Level 1
                    manager.load("MOUNTAINS/GoBackMOUNTAINS0.png", Texture.class); //Level2
                    manager.load("WOODS/WOODSBeginning.png",Texture.class); //Level3
                    manager.load("WOODS/WOODSEnding.png",Texture.class);//Level 4
                    manager.load("Interfaces/SOUND/SOUNDMusicON.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDMusic.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDSoundON.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDSound.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDBack.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDDecoration.png", Texture.class);
                    manager.load("Interfaces/SOUND/SOUNDResetButton.png", Texture.class);
                    break;
                case LEVEL0:
                    manager.load("MUSIC/GoBackMusicMainMenu.mp3", Music.class);
                    manager.load("INTRO/INTROBackground.png", Texture.class);
                    manager.load("INTRO/INTROBoat.png", Texture.class);
                    manager.load("INTRO/INTROOar.png", Texture.class);
                    manager.load("INTRO/INTROAbundioDialogue.png", Texture.class);
                    manager.load("INTRO/INTROAbundioDialogueBlink.png", Texture.class);
                    break;
                case LEVEL1:
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png",Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill00.png", Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill01.png", Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill02.png", Texture.class);
                    manager.load("OLDMAN/STILL/OLDMANStill03.png", Texture.class);

                    manager.load("OLDMAN/DIALOGUEOldManBlink.png", Texture.class);
                    manager.load("OLDMAN/DIALOGUEOldManNormal.png", Texture.class);

                    manager.load("SOPHIE/DIALOGUESophieBlink.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieConcern.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieNormal.png", Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieSurprise.png", Texture.class);
                    manager.load("CLUES/Newspaper/CLUESNewspaper.png",Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaBody.png",Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaBackLeg.png",Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaFrontLeg.png",Texture.class);
                    manager.load("MUSIC/GoBackMusicLevel1.mp3", Music.class);
                    break;
                case LEVEL2:
                    manager.load("MOUNTAINS/GoBackMOUNTAINSPanoramic.png",Texture.class);
                    manager.load("MINIONS/METEOR/MINIONMeteor00.png",Texture.class);
                    manager.load("Squirts/Sophie/SOPHIEComplete.png",Texture.class);
                    //manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png",Texture.class);
                    manager.load("CLUES/Photo/CLUESPhoto.png",Texture.class);
                    manager.load("BOSS/JAGUAR/BOSSJaguarBackLeg.png",Texture.class);
                    manager.load("BOSS/JAGUAR/BOSSJaguarBody.png",Texture.class);
                    manager.load("BOSS/JAGUAR/BOSSJaguarFrontLeg.png",Texture.class);

                    break;
                case LEVEL3:
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png",Texture.class);
                    manager.load("WOODS/WOODSPanoramic2of2.png",Texture.class);
                    manager.load("MINIONS/ARROW/MINIONBlueArrow00.png",Texture.class);
                    manager.load("MINIONS/ARROW/MINIONRedArrow00.png",Texture.class);
                    manager.load("MINIONS/ARROW/MINIONYellowArrow00.png",Texture.class);
                    manager.load("Squirts/Sophie/SOPHIEWalk.png",Texture.class);
                    manager.load("WOODS/WOODSPanoramic1of2.png",Texture.class);
                    manager.load("WOODS/WOODSBeginning.png",Texture.class);
                    manager.load("WOODS/WOODSEnding.png",Texture.class);
                    manager.load("WOODS/WOODSMiddle00.png",Texture.class);
                    manager.load("WOODS/WOODSMiddle01.png",Texture.class);
                    break;
                case LEVEL4:
                    manager.load("Squirts/Sophie/SOPHIEWalk.png",Texture.class);

                    manager.load("OLDMAN/CRACK/OLDMANCrackFULL.png", Texture.class);
                    manager.load("OLDMAN/CRACK/OLDMANCrackFINAL.png", Texture.class);

                    manager.load("HARBOR/GoBackHARBORPanoramic.png",Texture.class);
                    manager.load("MOUNTAINS/GoBackMOUNTAINSPanoramic.png",Texture.class);
                    manager.load("WOODS/WOODSPanoramic1of2.png",Texture.class);

                    manager.load("WOODS/WOODSEnding.png",Texture.class);
                    manager.load("MINIONS/HAND/MINIONSHand00.png", Texture.class);
                    manager.load("BOSS/DEATH/BOSSDeathFull.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png",Texture.class);
                    manager.load("MUSIC/GoBackMusicArcade.mp3", Music.class);
                    break;
                case BOSS1:

                    break;
                case BOSS2:

                    break;
                case BOSS3:

                    break;
                case LEVELFINAL:
                    manager.load("HARBOR/GoBackHARBORPanoramic.png",Texture.class);
                    manager.load("Squirts/Sophie/SOPHIEComplete.png",Texture.class);
                    manager.load("IAN/IanStill.png",Texture.class);
                    manager.load("IAN/DIALOGUEIanConcern.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTBlueOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTYellowOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/GobackCONSTRedOrb.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/ARCADEBlueOrbBUNNY.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/ARCADERedOrbBIRD.png",Texture.class);
                    manager.load("Interfaces/GAMEPLAY/CONSTANT/ARCADEYellowOrbDOG.png",Texture.class);
                    manager.load("SOPHIE/DIALOGUESophieNormal.png",Texture.class);
                    manager.load("INTRO/concha.png", Texture.class);

                    break;
                case LEVELEND:
                    manager.load("CLUES/CLUESBoneDisplay.png", Texture.class);
                    break;
                case ARCADE:
                    //PELLETS
                    manager.load("PELLET/ATAQUEYellowPellet.png", Texture.class);
                    manager.load("PELLET/ATAQUERedPellet.png", Texture.class);
                    manager.load("PELLET/ATAQUEBluePellet.png", Texture.class);

                    //MUSIC
                    manager.load("MUSIC/shoot.mp3", Sound.class);
                    manager.load("MUSIC/GoBackMusicArcade.mp3", Music.class);

                    //BACKGROUND
                    manager.load("HARBOR/GoBackHARBORPanoramic.png", Texture.class);//Level1
                    manager.load("MOUNTAINS/GoBackMOUNTAINSPanoramic.png", Texture.class);//Level2
                    manager.load("UNDERGROUND/UNDERGROUNDArcade.png", Texture.class);//Level3

                    //ORBS
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEYellowOrb.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADEBlueOrb.png", Texture.class);
                    manager.load("Interfaces/GAMEPLAY/ARCADE/ARCADERedOrb.png", Texture.class); 

                    //BAD GUYS TODO KEVIN PUT UR ASS(ETS) HERE!
                    manager.load("MINIONS/METEOR/MINIONMeteor00.png", Texture.class);

                    //IGUANA BOSS
                    manager.load("BOSS/IGUANA/BOSSIguanaBody.png", Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaSymbol.png", Texture.class);
                    manager.load("BOSS/IGUANA/BOSSIguanaName.png", Texture.class);
                    //JAGUAR BOSS
                    manager.load("BOSS/JAGUAR/BOSSJaguarBody.png", Texture.class);
                    manager.load("BOSS/JAGUAR/BOSSJaguarSymbol.png", Texture.class);
                    manager.load("BOSS/JAGUAR/BOSSJaguarName.png", Texture.class);
                    //EYES BOSS
                    manager.load("BOSS/BATS/BOSSBatYellow.png", Texture.class);
                    manager.load("BOSS/BATS/BOSSBatBlue.png", Texture.class);
                    manager.load("BOSS/BATS/BOSSBatRed.png", Texture.class);
                    manager.load("BOSS/BATS/BOSSBatName.png", Texture.class);
                    manager.load("BOSS/BATS/BOSSBatBlueSymbol.png", Texture.class);
                    manager.load("BOSS/BATS/BOSSBatRedSymbol.png", Texture.class);
                    manager.load("BOSS/BATS/BOSSBatYellowSymbol.png", Texture.class);
                    //lost screen
//                    manager.load("Interfaces/LOST/LOSTMenu.png", Texture.class);
//                    manager.load("Interfaces/LOST/LOSTDisplay.png", Texture.class);
//                    manager.load("Interfaces/LOST/LOSTContinue.png", Texture.class);
//                    manager.load("Interfaces/LOST/LOSTBorders.png", Texture.class);

                    break;
            }
    }

    private void goNextScreen() {
        if (manager.update()) { // Done loading?
            switch (loaderState) {
                case ABOUT:
                    app.setScreen(new About(app, menu));
                    break;
                case MAINMENU:
                    if(menu!=null) {
                        app.setScreen(menu);
                    }else {
                        app.setScreen(new MainMenu(app));
                    }
                    break;
                case SOUNDSETTINGS:
                    app.setScreen(new SoundSettings(app, menu));
                    break;
                case LEVEL0:
                    app.setScreen(new Level0(app));
                    break;
                case LEVEL1:
                    app.setScreen(new Level1(app));
                    break;
                case LEVEL2:
                    app.setScreen(new Level2(app));
                    break;
                case LEVEL3:
                    app.setScreen(new Level3(app));
                    break;
                case LEVEL4:
                    app.setScreen(new Level4(app));
                    break;
                case LEVELEND:
                    app.setScreen(new LevelEND(app));
                    break;
                case BOSS1:

                    break;
                case BOSS2:

                    break;
                case BOSS3:

                    break;
                case LEVELFINAL:
                    app.setScreen(new LevelFINAL(app));
                    break;
                case ARCADE:
                    app.setScreen(new Arcade(app));
                    break;
            }
        }
    }

    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        // texturaCargando.dispose();
    }
}
