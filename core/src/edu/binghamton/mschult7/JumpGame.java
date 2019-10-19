package edu.binghamton.mschult7;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.logging.Logger;

import sun.rmi.runtime.Log;

public class JumpGame extends ApplicationAdapter {

	OrthographicCamera camera;
	ShapeRenderer render;
	Vector3 coord;
	Vector3 boxWidth;
	Boolean Jump;
	Boolean left;
	float jumpTop;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		render = new ShapeRenderer();
		coord = new Vector3((Gdx.graphics.getWidth() /2), Gdx.graphics.getHeight() / 2, 0);
		boxWidth = new Vector3(0,100,300);
		Jump = false;
		left = false;
		jumpTop = 0;
	}

	@Override
	public void render () {
		float jumpHeight = 400;
		camera.update();
		if(Gdx.input.isTouched()){
			Jump = true;
			jumpTop = coord.x - jumpHeight;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		render.begin(ShapeRenderer.ShapeType.Filled);
		render.setColor(Color.BLUE);
		render.rect((Gdx.graphics.getWidth() /2)+500,boxWidth.y,25,boxWidth.z);

		float boxspeed = 10;
		if(!left && boxWidth.y < Gdx.graphics.getHeight() - boxWidth.z){
			boxWidth.set(boxWidth.x, boxWidth.y + boxspeed,boxWidth.z);
		} else if(boxWidth.y <= 0){
			left = false;
		}else if(left){
			boxWidth.set(boxWidth.x,boxWidth.y -boxspeed, boxWidth.z);
		} else if(boxWidth.y == Gdx.graphics.getHeight() - boxWidth.z){
			left = true;
		}


		render.end();


		render.begin(ShapeRenderer.ShapeType.Filled);
		render.setColor(Color.GREEN);
		float jumpSpeed = 15;

		float base = (Gdx.graphics.getWidth());
		if((int)coord.x > jumpTop && Jump){
			coord.set(coord.x -jumpSpeed, coord.y,0);
		} else if(Jump && (int)coord.x <= jumpTop){
			Jump = false;
		} else if(!Jump && (int)coord.x >= jumpTop-jumpSpeed && (int)coord.x < base){
			coord.set(coord.x + jumpSpeed, coord.y, 0);
		} else if(!Jump && (int)coord.x == (int)boxWidth.x && (int)coord.y == (int)boxWidth.y){
			Jump = true;
			boxWidth.set(boxWidth.x,boxWidth.y,boxWidth.z-10);
		}
		else {
			coord.set(coord.x, coord.y, 0);
		}
		camera.unproject(coord);
		render.circle(coord.x, coord.y, 64);

		render.end();
	}

	@Override
	public void dispose(){
		render.dispose();
	}

	public static void hz(long i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}