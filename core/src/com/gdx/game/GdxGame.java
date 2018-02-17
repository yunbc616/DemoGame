package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.unit.movable.player.Mario;

import java.util.Iterator;

public class GdxGame extends ApplicationAdapter {
	public static final int FRAME_COLS = 6;
	public static final int FRAME_ROWS = 1;
	public static final float FRAME_ACTION_DURATION = 0.125f;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture img;
	private Texture dropImage;
	private Texture bucketImage;
	private Texture marioSheet;

	private Animation<TextureRegion> marioAnimation;

	private Rectangle bucket;
	private Array<Rectangle> raindrops; // libgdx Array
	private long score = 0;
	private long lastDropTime; // nanoseconds
	private BitmapFont font;
	private float frameRate;

	private float stateTime = 0f;

	private Mario mario = null;

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.setX(MathUtils.random(0, 800-64));
		raindrop.setY(480);
		raindrop.setWidth(64);
		raindrop.setHeight(64);
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private TextureRegion getUpdatedFrame(Animation<TextureRegion> animation, float updatedStateTime){
		return animation.getKeyFrame(updatedStateTime, false);
	}

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		dropImage = new Texture("droplet.png");
		bucketImage = new Texture("bucket.png");

		bucket = new Rectangle();
		bucket.setX(800/2 - 64/2);
		bucket.setY(20);
		bucket.setWidth(64);
		bucket.setHeight(64);

		raindrops = new Array<Rectangle>();
		spawnRaindrop();

		font = new BitmapFont();

		mario = new Mario(25, 25);
		/*
		// Load the sprite sheet
		marioSheet =  new Texture("mario.png");

		TextureRegion[][] tmp = TextureRegion.split(marioSheet,
				marioSheet.getWidth()/FRAME_COLS,
				marioSheet.getHeight()/FRAME_ROWS);

		TextureRegion[] marioFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++){
			for (int j = 0; j < FRAME_COLS; j ++){
				marioFrames[index++] = tmp[i][j];
			}
		}
		marioAnimation = new Animation<TextureRegion>(FRAME_ACTION_DURATION, marioFrames);
		*/
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.getX(), bucket.getY());
		for (Rectangle raindrop: raindrops){
			batch.draw(dropImage, raindrop.getX(), raindrop.getY());
		}
		// batch.draw(getUpdatedFrame(marioAnimation, stateTime), 25, 25);
		batch.draw(mario.getFrames(), mario.getX(), mario.getY());

		font.draw(batch, Long.toString(score), 800 - 100, 400 - 50);
		font.draw(batch, Float.toString(frameRate), 800 - 100, 400 - 10);
		batch.end();

		if (Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); // this function translates the touchPos xy coordination from screen system to camera system
			bucket.setX(touchPos.x - 64/2);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			//bucket.setX(bucket.getX() - 200 * Gdx.graphics.getDeltaTime());
			mario.setX(mario.getX() - 100 * Gdx.graphics.getDeltaTime());
			mario.updateWalkingState(Gdx.graphics.getDeltaTime());
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			//bucket.setX(bucket.getX() + 200 * Gdx.graphics.getDeltaTime());
			mario.setX(mario.getX() + 100 * Gdx.graphics.getDeltaTime());
			mario.updateWalkingState(Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			//bucket.setX(bucket.getX() + 200 * Gdx.graphics.getDeltaTime());
			mario.updateStandingState();
		}

		if (bucket.getX() < 0){
			bucket.setX(0);
		} else if (bucket.getX() > 800 - 64){
			bucket.setX(800 - 64);
		}

		if (mario.getX() < 0){
			mario.setX(0);
		} else if (mario.getX() > 800 - 64){
			mario.setX(800 - 64);
		}

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000){
			spawnRaindrop();
		}

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.setY(raindrop.getY() - 200 * Gdx.graphics.getDeltaTime());
			if (raindrop.y + 64 < 0){
				iter.remove();
			}

			if (raindrop.overlaps(bucket)){
				score ++;
				iter.remove();
			}
		}


		frameRate = Gdx.graphics.getFramesPerSecond();

	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		batch.dispose();
		img.dispose();
	}
}
