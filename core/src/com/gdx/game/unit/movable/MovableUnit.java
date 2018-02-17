package com.gdx.game.unit.movable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.unit.GenericUnit;
import sun.net.www.content.text.Generic;

public abstract class MovableUnit extends GenericUnit {
    private float DURATION_FRAME_FULL_STATES = 0.75f;
    private float DURATION_FRAME_STATE = 0.125f;
    private int NO_FRAME_COLS = 6;
    private int NO_FRAME_ROWS = 1;

    public static final int[] INDEX_STANDING_FRAME = {0};
    public static final int[] INDEX_WALKING_FRAME = {1,2,3,4};
    public float DURATION_WALKING_FRAME = DURATION_FRAME_FULL_STATES / NO_FRAME_ROWS * INDEX_WALKING_FRAME.length;

    public static final int NO_FRAME_WALK_STATES = 6;

    public MovableUnit(float x, float y){

        super(x, y, 0.75f, 0.125f, 6, 1);
    }


    public MovableUnit(){
        super(0, 0, 0.75f, 0.125f, 6, 1);
    }

    public TextureRegion getStandingFrame(){
        return this.unitAnimation.getKeyFrame(INDEX_STANDING_FRAME[0], false);
    }

    public void updateStandingState(){
        this.state = INDEX_STANDING_FRAME[0] * DURATION_FRAME_STATE;
    }

    public void updateWalkingState(float escapedTime){
        // the state should be within the walk frame state duration
        float walkingState = (this.state + escapedTime) % DURATION_FRAME_FULL_STATES + INDEX_WALKING_FRAME[0] * DURATION_FRAME_STATE;
        if (walkingState > INDEX_WALKING_FRAME[INDEX_WALKING_FRAME.length-1]){
            walkingState = INDEX_WALKING_FRAME[0] * DURATION_FRAME_STATE;
        }
        this.state = walkingState;
    }
}
