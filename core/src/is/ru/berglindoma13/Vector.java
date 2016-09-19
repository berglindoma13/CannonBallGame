package is.ru.berglindoma13;

/**
 * Created by Berglind on 9/9/2016.
 */
public class Vector {
    public float x;
    public float y;

    public Vector getnormal(){
        Vector normal = new Vector();
        normal.x = -y;
        normal.y = x;
        return normal;
    }
}
