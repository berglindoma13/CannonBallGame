package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;

/**
 * Created by Berglind on 9/15/2016.
 */
public class CannonBall {
    Point position;
    //CircleGraphic c;

    public CannonBall(){
        position = new Point();
        position.x = 0;
        position.y = 0;
        position.z = 0;
    }

    public void display(int colorLoc){
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.setModelMatrixTranslation(position.x,position.y,position.z);
        ModelMatrix.main.setModelMatrixScale(5,5,0);
        ModelMatrix.main.setShaderMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.5f, 0, 1);
        CircleGraphic.drawSolidCircle();
}

    public void update(){
        position.x += 5;
        position.y += 5;
    }
}
