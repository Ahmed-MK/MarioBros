package com.ahmedmk.mariobros.Screens;

import com.ahmedmk.mariobros.MarioBros;
import com.ahmedmk.mariobros.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    //handle user interaction controls
    public void handleInput(float deltaTime){
        if (Gdx.input.isTouched())
            gameCam.position.x += 150*deltaTime;

//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//            gameCam.position.x += 150*deltaTime;
//        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            gameCam.position.x -= 150*deltaTime;
    }

    //update the game state
    public void update(float deltaTime){
        handleInput(deltaTime);
        gameCam.update();
        renderer.setView(gameCam);
    }

    public PlayScreen(MarioBros game){
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_Width,MarioBros.V_Height,gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

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

    }
}
