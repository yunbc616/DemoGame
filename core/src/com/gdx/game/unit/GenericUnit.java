package com.gdx.game.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class GenericUnit extends Rectangle{

    private float DURATION_FRAME_FULL_STATES = 1f;
    private float DURATION_FRAME_STATE_ = 1f;
    private int NO_FRAME_COLS = 1;
    private int NO_FRAME_ROWS = 1;

    private float unitHeight = 0f;
    private float unitWidth = 0f;

    protected float state = 0f;
    protected Animation<TextureRegion> unitAnimation = null;

    public GenericUnit() {
        this.setX(0);
        this.setY(0);
        setupUnitAnimation();
    }

    public GenericUnit(float x, float y) {
        this.setX(x);
        this.setY(y);
        setupUnitAnimation();
    }

    public GenericUnit(float x, float y, float durationFrameFullStates, float durationFrameState, int noFrameCols, int noFrameRows){
        this.setX(x);
        this.setY(y);
        this.setDURATION_FRAME_FULL_STATES(durationFrameFullStates);
        this.setDURATION_FRAME_STATE_(durationFrameState);
        this.setNO_FRAME_COLS(noFrameCols);
        this.setNO_FRAME_ROWS(noFrameRows);
        this.setX(0);
        this.setY(0);
        setupUnitAnimation();
    }

    protected abstract Texture getTexture();

    protected void setupUnitAnimation(){
        Texture textureSheet = this.getTexture();
        unitHeight = textureSheet.getHeight()/NO_FRAME_ROWS;
        unitWidth = textureSheet.getWidth()/NO_FRAME_COLS;

        TextureRegion[][] textureRegion2D = TextureRegion.split(textureSheet,
                (int)this.getUnitWidth(),
                (int)this.getUnitHeight());

        TextureRegion[] textureRegion = new TextureRegion[NO_FRAME_COLS * NO_FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < NO_FRAME_ROWS; i++){
            for (int j = 0; j < NO_FRAME_COLS; j ++){
                textureRegion[index++] = textureRegion2D[i][j];
            }
        }
        this.unitAnimation = new Animation<TextureRegion>(DURATION_FRAME_FULL_STATES, textureRegion);

    }

    public float getUnitWidth(){
        return this.unitWidth;
    }

    public float getUnitHeight(){
        return this.unitHeight;
    }

    public float getState() {
        return state;
    }

    public void setState(float state) {
        this.state = state;
    }

    public float getDURATION_FRAME_FULL_STATES() {
        return DURATION_FRAME_FULL_STATES;
    }

    public void setDURATION_FRAME_FULL_STATES(float DURATION_FRAME_FULL_STATES) {
        this.DURATION_FRAME_FULL_STATES = DURATION_FRAME_FULL_STATES;
    }

    public float getDURATION_FRAME_STATE_() {
        return DURATION_FRAME_STATE_;
    }

    public void setDURATION_FRAME_STATE_(float DURATION_FRAME_STATE_) {
        this.DURATION_FRAME_STATE_ = DURATION_FRAME_STATE_;
    }

    public int getNO_FRAME_COLS() {
        return NO_FRAME_COLS;
    }

    public void setNO_FRAME_COLS(int NO_FRAME_COLS) {
        this.NO_FRAME_COLS = NO_FRAME_COLS;
    }

    public int getNO_FRAME_ROWS() {
        return NO_FRAME_ROWS;
    }

    public void setNO_FRAME_ROWS(int NO_FRAME_ROWS) {
        this.NO_FRAME_ROWS = NO_FRAME_ROWS;
    }

    public Animation<TextureRegion> getUnitAnimation() {
        return unitAnimation;
    }

    public void setUnitAnimation(Animation<TextureRegion> unitAnimation) {
        this.unitAnimation = unitAnimation;
    }

    public TextureRegion getFrames(){
        return this.unitAnimation.getKeyFrame(state, true);
    }

}
