package com.ahmedmk.mariobros.Screens;

import com.ahmedmk.mariobros.MarioBros;
import com.ahmedmk.mariobros.Scenes.Hud;
import com.ahmedmk.mariobros.Sprites.Mario;
import com.ahmedmk.mariobros.Tools.Box2dWorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.*;

public class PlayScreen implements Screen {
    //the actual game class itself
    private MarioBros game;
    //game cam
    private OrthographicCamera gameCam;
    //view port for porting purposes
    private Viewport gamePort;
    //the game Hud
    private Hud hud;
    //map loader to load the level
    private TmxMapLoader mapLoader;
    //reference for the map itself
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2D Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    //Texture Atlas
    private TextureAtlas Atlas;

    //create object from mario
    Mario mario;

    public PlayScreen(MarioBros game){

        //Atlas Texture
        Atlas = new TextureAtlas("/home/ahmed/AndroidStudioProjects/MarioBros/android/assets/Mario_and_Enemies.pack");

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM,MarioBros.V_HEIGHT / MarioBros.PPM,gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("/home/ahmed/AndroidStudioProjects/MarioBros/android/assets/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        //world init
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        new Box2dWorldCreator(world, map);
        mario = new Mario(world,this);

    }

    public TextureAtlas getAtlas(){
        return Atlas;
    }

    //handle user interaction controls
    public void handleInput(float deltaTime){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            mario.b2body.applyLinearImpulse(new Vector2(0,4f), mario.b2body.getWorldCenter(),true);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2body.getLinearVelocity().x <= 2)
            mario.b2body.applyLinearImpulse(new Vector2(0.1f,0), mario.b2body.getWorldCenter(),true);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2body.getLinearVelocity().x >= -2)
            mario.b2body.applyLinearImpulse(new Vector2(-0.1f,0), mario.b2body.getWorldCenter(),true);

        if (Gdx.input.isTouched())
            mario.b2body.applyLinearImpulse(new Vector2(0.1f,0), mario.b2body.getWorldCenter(),true);

//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//            gameCam.position.x += 150*deltaTime;
//        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            gameCam.position.x -= 150*deltaTime;
    }

    //update the game state
    public void update(float deltaTime){
        handleInput(deltaTime);

        world.step(1/60f, 6,2);

        gameCam.position.x = mario.b2body.getPosition().x;


        gameCam.update();
        //tells our renderer to draw only what camera can see in our game world
        renderer.setView(gameCam);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        mario.draw(game.batch);
        game.batch.end();

        //set our batch to draw hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
