package is.ru.berglindoma13;

/**
 * Created by Berglind on 9/9/2016.
 */
public class Vector {
    public float x;
    public float y;

    public void normalize(){
        float length = (float)Math.sqrt(x*x + y*y);
        x = x / length;
        y = y / length;

    }

    public void normalize(float setLength){
        float length = (float)Math.sqrt(x*x + y*y);
        x = setLength * x / length;
        y = setLength * y / length;
    }

    public Vector getnormal(){
        Vector normal = new Vector();
        normal.x = -y;
        normal.y = x;
        return normal;
    }
}
