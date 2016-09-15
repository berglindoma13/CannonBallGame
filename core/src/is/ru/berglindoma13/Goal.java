package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;

/**
 * Created by Berglind on 9/15/2016.
 */
public class Goal {
    Point position;
    private boolean directionRight;
    private boolean directionLeft;

    public Goal(){
        position = new Point();
        position.x = 50;
        position.y = Gdx.graphics.getHeight() - 20;
        position.z = 0;
        directionLeft = false;
        directionRight = true;
    }

    public void update(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        if ((position.x <= Gdx.graphics.getWidth() - 50) && directionRight){
            position.x += 150.0f * deltaTime;
        }
        else if(position.x >= 50 && directionLeft){
            position.x-= 150.0f * deltaTime;
        }
        else if(position.x > Gdx.graphics.getWidth()-50){
            directionRight = false;
            directionLeft = true;
        }
        else{
            directionLeft = false;
            directionRight = true;
        }
    }

    public void display(int colorLoc){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.setModelMatrixTranslation(position.x,position.y,position.z);
        ModelMatrix.main.setModelMatrixRotation(90);
        ModelMatrix.main.setShaderMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.3f, 0.2f, 0, 1);
        RectangleGraphic.drawSquareLines();
        ModelMatrix.main.popMatrix();
    }
}
