package com.ahmedmk.mariobros.Scenes;

import com.ahmedmk.mariobros.MarioBros;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


import java.awt.*;

public class Hud {
    //Special camera that stays locked to the hud while the world is moving around it
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;
    private int score;

    private Label countDownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    //to create a new Label with a text and color
    private Label setLabel(String labelText){
        return new Label(labelText, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    public Hud(SpriteBatch spriteBatch){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FillViewport(MarioBros.V_Width, MarioBros.V_Height,new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = setLabel(String.format("%03d",worldTimer));
        scoreLabel = setLabel(String.format("%06d",score));
        timeLabel = setLabel("Time");
        levelLabel = setLabel("1-1");
        worldLabel = setLabel("World");
        marioLabel = setLabel("Mario");

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);
    }
}
