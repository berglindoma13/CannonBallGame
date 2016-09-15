package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.sun.javafx.sg.prism.NGShape;

/**
 * Created by Berglind on 9/15/2016.
 */
public class CannonBall {
    Point position;
    Vector direction;

    public CannonBall(Cannon cannon){
        direction = new Vector();
        position = new Point();
        position.x = cannon.get_cannonPoint().x;
        position.y = cannon.get_cannonPoint().y;
        position.z = cannon.get_cannonPoint().z;
        direction.x = 0.0f;
        direction.y = 1.0f;
    }

    public void display(int colorLoc){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.setModelMatrixTranslation(position.x,position.y,position.z);
        ModelMatrix.main.setModelMatrixScale(5,5,0);
        ModelMatrix.main.setShaderMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.5f, 0, 1);
        CircleGraphic.drawSolidCircle();
        ModelMatrix.main.popMatrix();
}

    public void update(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        position.x += direction.x * deltaTime * 250.0f;
        position.y += direction.y * deltaTime * 250.0f;

        collision();

    }

    public void setDirection(float x){
        direction.x = x;
        direction.y = 1;
    }

    public void collision(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        Point topwall = new Point();
        topwall.x = 0;
        topwall.y = 768;

        Vector v = new Vector();
        v.x = -1024;
        v.y = 0;

        Vector normal = new Vector();
        normal.x = -v.y;
        normal.y = v.x;

        Vector b_a = new Vector();
        b_a.x = (topwall.x - position.x);
        b_a.y = (topwall.y - position.y);

        float thit = ((normal.x * b_a.x) + (normal.y * b_a.y)) / ((normal.x * direction.x) + (normal.y * direction.y));

        //putting thit and delta into same units
        float newthit = thit * direction.x + thit*direction.y;
        float newdelta = deltaTime * direction.x + deltaTime * direction.y;

        if(newthit < newdelta){

            getReflection();
        }
    }

    public void getReflection(){
        Vector reflection = new Vector();
        Vector v = new Vector();
        v.x = -1024;
        v.y = 0;

        Vector normal = new Vector();
        normal.x = -v.y;
        normal.y = v.x;
        float calc = (2*((direction.x * normal.x) + (direction.y * normal.y)))/((normal.x*normal.x)+(normal.y*normal.y));

        Vector newn = new Vector();
        newn.x = normal.x*calc;
        newn.y = normal.y*calc;

        reflection.x = direction.x - newn.x;
        reflection.y = direction.y - newn.y;

        direction.x = reflection.x;
        direction.y = reflection.y;
    }
}
