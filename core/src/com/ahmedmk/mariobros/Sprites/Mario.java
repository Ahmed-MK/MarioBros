package com.ahmedmk.mariobros.Sprites;

import com.ahmedmk.mariobros.MarioBros;
import com.ahmedmk.mariobros.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Mario extends Sprite {

    public World world;
    public Body b2body;
    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen){
        screen.getAtlas().findRegion("/home/ahmed/AndroidStudioProjects/MarioBros/android/assets/Mario GFX/little_mario.png");
        this.world = world;
        defineMario();

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setRegion(marioStand);
        setBounds(0,0,16,16);

    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioBros.PPM,32 / MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
