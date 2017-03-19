package mx.rmr.App201711;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * SplashScreen
 */

class SplashScreen extends Screen
{

    private final App app; //Main app class

    //Screen sizes
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float HALFW = WIDTH/2;
    public static final float HALFH = HEIGHT/2;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private float upTime = 1.2f;

    // Logo del tec
    private Texture textureLogo;
    private Sprite spriteLogo;

    // Constructor, guarda la referencia al app
    public SplashScreen(App app) {
        this.app = App;
    }

    @Override
    public void show() {
        cameraInit();
        textureInit();
       
        logoScale();
    }

    private void cameraInit() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(HALFW, HALFH, 0);
        camera.update();
    }

    private void textureInit() {
        textureLogo = new Texture("Interfaces/splash.png");
        spriteLogo = new Sprite(textureLogo);
        spriteLogo.setPosition(WIDTH/2-spriteLogo.getWidth()/2, HEIGHT/2-spriteLogo.getHeight()/2);
    }

    private void logoScale() {
        float cameraScale = WIDTH / HEIGHT;
        float screenScale = 1.0f*Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float scale = cameraScale / screenScale;
        spriteLogo.setScale(scale, 1);
    }


    @Override
    public void render(float delta) {
        cls();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteLogo.draw(batch);
        batch.end();

        upTime -= delta;
        if (upTime<=0) {
            app.setScreen(new Fade(app, Pantallas.MAINMENU));
        }
    }
    private void cls() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void actualizarVista() {
        logoScale();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        // Libera las texturas
        textureLogo.dispose();
    }
}
