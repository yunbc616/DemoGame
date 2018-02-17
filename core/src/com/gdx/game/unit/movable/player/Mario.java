package com.gdx.game.unit.movable.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.unit.GenericUnit;
import com.gdx.game.unit.movable.MovableUnit;

public class Mario extends MovableUnit {

    public Mario (float x, float y){
        super(x,y);
    }

    @Override
    protected Texture getTexture(){
        return new Texture("mario.png");
    }

}
