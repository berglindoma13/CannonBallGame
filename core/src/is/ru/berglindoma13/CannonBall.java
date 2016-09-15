package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.sun.javafx.sg.prism.NGShape;

/**
 * Created by Berglind on 9/15/2016.
 */
public class CannonBall {
    Point position;
    Vector direction;
    int i = 0;

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
        Gdx.gl.glUniform4f(colorLoc, 0.1f, 0.5f, 0, 1);
        CircleGraphic.drawSolidCircle();
        ModelMatrix.main.popMatrix();
}

    public void update(float hit){
        float deltaTime = Gdx.graphics.getDeltaTime();
        position.x += direction.x * deltaTime * 150.0f;
        position.y += direction.y * deltaTime * 150.0f;

        collision(hit);

    }

    public void setDirection(float x){
        direction.x = x;
        direction.y = 1;
    }

    public void collision(float hit){
        float deltaTime = Gdx.graphics.getDeltaTime();

        Point leftside = new Point();
        leftside.x = 0;
        leftside.y = 768;

        Vector v = new Vector();
        v.x = 0;
        v.y = -768;

        Vector normal = new Vector();
        normal.x = -v.y;
        normal.y = v.x;

        Point topwall = new Point();
        topwall.x = 0;
        topwall.y = 768;

        Vector v2 = new Vector();
        v2.x = -1024;
        v2.y = 0;

        Vector normal2 = new Vector();
        normal2.x = -v.y;
        normal2.y = v.x;

        float thit1 = thit(v,normal,leftside);
        float thit2 = thit(v2,normal2,topwall);


        if(thit1 < deltaTime && i == 0){

            /*if(position.x < hit + 50 && position.x > hit - 50){
                System.out.println("HIT THE GOAL");
            }
            else{
                getReflection(v);
            }*/
            i = 1;
            getReflection(v);

        }
        else if(thit2 < deltaTime && i == 0){
            i = 1;
            getReflection(v2);
        }
    }

    public void getReflection(Vector v){
        Vector reflection = new Vector();

        Vector normal = new Vector();
        normal.x = -v.y;
        normal.y = v.x;

        float calc = 2*(((direction.x * normal.x) + (direction.y * normal.y))/((normal.x*normal.x)+(normal.y*normal.y)));

        Vector temp = new Vector();
        temp.x = normal.x*calc;
        temp.y = normal.y*calc;

        reflection.x = direction.x - temp.x;
        reflection.y = direction.y - temp.y;

        direction.x = reflection.x;
        direction.y = reflection.y;
        //i = 0;
    }

    private float thit(Vector v, Vector normal, Point b){
        Vector b_a = new Vector();
        b_a.x = (b.x - position.x);
        b_a.y = (b.y - position.y);

        float thit = ((normal.x * b_a.x) + (normal.y * b_a.y)) / ((normal.x * direction.x) + (normal.y * direction.y));

        return thit;
    }


}
