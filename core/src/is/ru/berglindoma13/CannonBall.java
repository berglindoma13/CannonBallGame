package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;

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
        direction.y = 0.0f;
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

    public void update(Point hit){
        float deltaTime = Gdx.graphics.getDeltaTime();
        position.x += direction.x * deltaTime;
        position.y += direction.y * deltaTime ;

        if (goal(hit)) {
            System.out.println("GOAL");
            //position.x = cannon.get_cannonPoint().x;
            //position.y = cannon.get_cannonPoint().y;
            CannonGame.stopball();
        }
        else if(outofbounds()){
            CannonGame.stopball();
        }

        collision();

    }

    public void setDirection(float x, float y){
        direction.x = x;
        direction.y = y;
    }

    public void collision(){
        float deltaTime = Gdx.graphics.getDeltaTime();

        for(int i = 0 ; i < CannonGame.getNumberOfObstacles(); i++){
            Point middle = new Point();
            middle.x = CannonGame.getObstaclesX()[i];
            middle.y = CannonGame.getObstacleY()[i];

            //bottom left corner
            Point p1 = new Point();
            p1.x = middle.x - 50;
            p1.y = middle.y - 20;

            //top left corner
            Point p2 = new Point();
            p2.x = middle.x - 50;
            p2.y = middle.y + 20;

            //bottom right corner
            Point p3 = new Point();
            p3.x = middle.x + 50;
            p3.y = middle.y - 20;

            //top right corner
            Point p4 = new Point();
            p4.x = middle.x + 50;
            p4.y = middle.y + 20;

            Vector v = new Vector();

            if(direction.x >= 0 && direction.y >= 0){

                //bottom
                float thit2 = thit(p1,p3);
                Point phit = getPhit(thit2);
                boolean itsahit = (phit.x >= p1.x) && (phit.x <= p3.x);
                if ((thit2 < deltaTime && thit2 > 0) && itsahit){
                    v.x = p1.x - p3.x;
                    v.y = p1.y - p3.y;
                    getReflection(v);
                }

                //left
                float thit3 = thit(p1,p2);
                Point phit2 = getPhit(thit3);
                boolean itsahit2 = (phit2.y >= p1.y) && (phit2.y <= p2.y);
                if((thit3 < deltaTime && thit3 > 0) && itsahit2){
                    v.x = p1.x - p2.x;
                    v.y = p1.y - p2.y;
                    getReflection(v);
                }
            }

            else if(direction.x <= 0 && direction.y >= 0){
                //bottom
                float thit2 = thit(p1,p3);
                Point phit = getPhit(thit2);
                boolean itsahit = (phit.x >= p1.x) && (phit.x <= p3.x);
                if ((thit2 < deltaTime && thit2 > 0)&&itsahit){
                    v.x = p1.x - p3.x;
                    v.y = p1.y - p3.y;
                    getReflection(v);
                }

                //right
                float thit4 = thit(p3,p4);
                Point phit2 = getPhit(thit4);
                boolean itsahit2 = (phit2.y >= p3.y) && (phit2.y <= p4.y);
                if((thit4 < deltaTime && thit4 > 0)&&itsahit2){
                    v.x = p3.x - p4.x;
                    v.y = p3.y - p4.y;
                    getReflection(v);
                }
            }

            else if(direction.x >= 0 && direction.y <= 0){
                //left
                float thit3 = thit(p1,p2);
                Point phit = getPhit(thit3);
                boolean itsahit = (phit.y >= p1.y) && (phit.y <= p2.y);
                if((thit3 < deltaTime && thit3 > 0)&&itsahit){
                    v.x = p1.x - p2.x;
                    v.y = p1.y - p2.y;
                    getReflection(v);
                }

                //top
                float thit1 = thit(p2,p4);
                Point phit2 = getPhit(thit1);
                boolean itsahit2 = (phit2.x >= p2.y) && (phit2.x <= p4.y);
                if((thit1 < deltaTime && thit1 > 0)&&itsahit2){
                    v.x = p2.x - p4.x;
                    v.y = p2.y - p4.y;
                    getReflection(v);
                }
            }

            else if(direction.x <= 0 && direction.y <= 0){
                //top
                float thit1 = thit(p2,p4);
                Point phit = getPhit(thit1);
                boolean itsahit = (phit.x >= p2.x) && (phit.x <= p4.x);
                if((thit1 < deltaTime && thit1 > 0)&&itsahit){
                    v.x = p2.x - p4.x;
                    v.y = p2.y - p4.y;
                    getReflection(v);
                }

                //right
                float thit4 = thit(p3,p4);
                Point phit2 = getPhit(thit4);
                boolean itsahit2 = (phit2.y >= p3.y) && (phit2.y <= p4.y);
                if((thit4 < deltaTime && thit4 > 0) && itsahit2){
                    v.x = p3.x - p4.x;
                    v.y = p3.y - p4.y;
                    getReflection(v);
                }
            }
        }

        //Checking wall hits
        Point lefttop = new Point();
        lefttop.x = 0;
        lefttop.y = 768;

        Point righttop = new Point();
        righttop.x = 1024;
        righttop.y = 768;

        Point leftbottom = new Point();
        leftbottom.x = 0;
        leftbottom.y = 0;

        Point rightbottom = new Point();
        rightbottom.x = 1024;
        rightbottom.y = 0;


        Vector v = new Vector();

        //right side wall
        if(direction.x >= 0) {
            float thitright = thit(righttop, rightbottom);
            if (thitright < deltaTime) {

                v.x = rightbottom.x - righttop.x;
                v.y = rightbottom.y - righttop.y;
                getReflection(v);
            }
        }
        //left side wall
        else if(direction.x < 0) {
            float thitleft = thit(lefttop, leftbottom);

            if (thitleft < deltaTime && thitleft > 0) {
                v.x = leftbottom.x - lefttop.x;
                v.y = leftbottom.y - lefttop.y;

                getReflection(v);
            }
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


    }

    private float thit(Point a, Point b){
        Vector v = new Vector();
        v.x = a.x - b.x;
        v.y = a.y - b.y;

        Vector normal;
        normal = v.getnormal();

        Vector b_a = new Vector();
        b_a.x = (a.x - position.x);
        b_a.y = (a.y - position.y);

        float thit = ((normal.x * b_a.x) + (normal.y * b_a.y)) / ((normal.x * direction.x) + (normal.y * direction.y));

        return thit;
    }

    public Point getPhit(float thit){
        //A - particle point
        //c - particle vector
        //Phit = a + thit * c
        Point phit = new Point();

        phit.x = position.x + (thit*direction.x);
        phit.y = position.y + (thit*direction.y);

        return phit;
    }


    public boolean goal(Point hit){
        if(position.x <= (hit.x + 50) && position.x >= (hit.x - 50)){
            if(position.y <= (hit.y+20) && position.y >= (hit.y-20)){
                return true;
            }
        }
        return false;
    }

    public boolean outofbounds(){

        if(position.y > Gdx.graphics.getHeight() || position.y < 0){
            return true;
        }
        return false;
    }
}
