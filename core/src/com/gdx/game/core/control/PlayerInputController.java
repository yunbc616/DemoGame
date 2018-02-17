package com.gdx.game.core.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.gdx.game.unit.movable.MovableUnit;

public class PlayerInputController implements InputProcessor{

    public final static float HORIZONTAL_MOVE_UNIT = 200;
    private MovableUnit playerUnit = null;
    private int keyCodePressed = -1;

    public PlayerInputController(MovableUnit playerUnit){
        this.setPlayerUnit(playerUnit);
    }

    public void moveUnit(){
        if (keyCodePressed != -1){
            float deltaTime = Gdx.graphics.getDeltaTime();
            float moveUnit = 0f;

            if (Input.Keys.LEFT == keyCodePressed){
                moveUnit -= HORIZONTAL_MOVE_UNIT;
            } else if (Input.Keys.RIGHT == keyCodePressed) {
                moveUnit += HORIZONTAL_MOVE_UNIT;
            }

            if (moveUnit != 0){
                playerUnit.setX(playerUnit.getX() + moveUnit * deltaTime);
                if (playerUnit.getX() < 0){
                    playerUnit.setX(0);
                } else if (playerUnit.getX() > playerUnit.UNIT_HORIZONTAL_BOUNDARY - playerUnit.getUnitWidth()){
                    playerUnit.setX(playerUnit.UNIT_HORIZONTAL_BOUNDARY - playerUnit.getUnitWidth());
                }
                playerUnit.updateWalkingState(deltaTime);


            }
        }
    }

    public void stopUnit(){
        playerUnit.updateStandingState();
    }

    @Override
    public boolean keyDown(int keycode) {
        keyCodePressed = keycode;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (Input.Keys.LEFT == keycode || Input.Keys.RIGHT == keycode){
            keyCodePressed = -1;
            playerUnit.updateStandingState();
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public MovableUnit getPlayerUnit() {
        return playerUnit;
    }

    public void setPlayerUnit(MovableUnit playerUnit) {
        this.playerUnit = playerUnit;
    }
}
