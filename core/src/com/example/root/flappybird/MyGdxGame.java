package com.example.root.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	//ShapeRenderer shapeRenderer;

    int flap = 0;
    float birdy = 0;
    float velocity = 0;
    float tubevelocity = 4;
    int state = 0;
    float gravity = 2;
     Texture toptube;
    Texture bottomtube;
    float gap = 400;
    Random random;
    int number =4;
    Texture gameover;
    float[] maxmov = new float[number];
    float[] tubex = new float[number] ;
    float distance;
    float maxmove;
     Circle birdcircle ;
     Rectangle[] toprectangle;
     Rectangle[] bottomrectangle;
     BitmapFont font;


     int score = 0;
     int scoringtube = 0;





    @Override
	public void create () {
		batch = new SpriteBatch();
		 background = new Texture("bg.png");
		 birds = new Texture[2];
		 birds[0]= new Texture("bird.png");
		 birds[1] = new Texture("bird2.png");
		    distance =  2*Gdx.graphics.getWidth()/3;
		 toptube = new Texture("toptube.png");
		 bottomtube = new Texture("bottomtube.png");
		 maxmove = Gdx.graphics.getHeight()/2 -gap/2 -200;
        //shapeRenderer = new ShapeRenderer();
        birdcircle = new Circle();
        toprectangle = new Rectangle[number];
        bottomrectangle= new Rectangle[number];
            font = new BitmapFont();
            font.setColor(Color.FIREBRICK);
            font.getData().setScale(10);
        gameover = new Texture("gameover.png");
        random = new Random();
        birdy = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;

        for (int i = 0 ; i < number ;i++) {
            maxmov[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() - gap -400);
            tubex[i] = Gdx.graphics.getWidth() - toptube.getWidth()/2 + i*distance;

            toprectangle[i] = new Rectangle();
            bottomrectangle[i] = new Rectangle();
        }
    }

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



        if (state == 1) {

            if (tubex[scoringtube] < Gdx.graphics.getWidth()/2){
                score++;
                if (scoringtube < number -1 ){
                    scoringtube++;
                }else {
                    scoringtube = 0;
                }
            }

            if (Gdx.input.justTouched()){


                velocity = -30;


            }
            for (int i = 0 ; i < number ;i++) {

                if (tubex[i] < -toptube.getWidth()){

                    tubex[i] += number*distance;
                    maxmov[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() - gap -400);


                }else {

                    tubex[i] -= tubevelocity;


                }



                batch.draw(toptube, tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + maxmov[i]);
                batch.draw(bottomtube, tubex[i], Gdx.graphics.getHeight() / 2 - bottomtube.getHeight() - gap / 2 + maxmov[i]);

                toprectangle[i] = new Rectangle(tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + maxmov[i],toptube.getWidth(),toptube.getHeight());
                bottomrectangle[i] = new Rectangle(tubex[i], Gdx.graphics.getHeight() / 2 - bottomtube.getHeight() - gap / 2 + maxmov[i],bottomtube.getWidth(),bottomtube.getHeight());
            }


            if ( birdy > 0)

            {
                velocity += gravity;
                birdy -= velocity;
            }else {

                state = 2;

            }

        }else if (state == 0){



            if (Gdx.input.justTouched()){



                state = 1;

            }

        }else  {

            batch.draw(gameover,Gdx.graphics.getWidth()/2 - gameover.getWidth()/2,Gdx.graphics.getHeight()/2 - gameover.getHeight()/2);

            if (Gdx.input.justTouched()){


               state = 1;
                birdy = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;

                for (int i = 0 ; i < number ;i++) {
                    maxmov[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() - gap -400);
                    tubex[i] = Gdx.graphics.getWidth() - toptube.getWidth()/2 + i*distance;

                    toprectangle[i] = new Rectangle();
                    bottomrectangle[i] = new Rectangle();
                }
                score = 0;
                scoringtube = 0 ;
                velocity = 0;


            }
        }

        if (flap == 0) {
            flap = 1;
        } else {
            flap = 0;
        }

        batch.draw(birds[flap], Gdx.graphics.getWidth() / 2 - birds[flap].getWidth() / 2, birdy);

         font.draw(batch,String.valueOf(score),100,200);



        birdcircle.set(Gdx.graphics.getWidth()/2,birdy+birds[flap].getHeight()/2,birds[flap].getWidth()/2);




        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
        //shapeRenderer.circle(birdcircle.x,birdcircle.y,birdcircle.radius);

        for (int i = 0 ; i < number ;i++) {

            //shapeRenderer.rect(tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + maxmov[i],toptube.getWidth(),toptube.getHeight());
            //shapeRenderer.rect(tubex[i], Gdx.graphics.getHeight() / 2 - bottomtube.getHeight() - gap / 2 + maxmov[i],bottomtube.getWidth(),bottomtube.getHeight());

            if (Intersector.overlaps(birdcircle,toprectangle[i])||Intersector.overlaps(birdcircle,bottomrectangle[i])){

                state = 2;
            }

        }
       // shapeRenderer.end();
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
