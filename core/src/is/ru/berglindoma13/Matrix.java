package is.ru.berglindoma13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Stack;

/**
 * Created by Berglind on 9/14/2016.
 */
public class Matrix {

    FloatBuffer matrix;
    Stack<FloatBuffer> matrixStack;


    public Matrix(){
        matrix = BufferUtils.newFloatBuffer(16);
        matrixStack = new Stack<FloatBuffer>();
    }

    public void loadIdentityMatrix(){

        matrix.put(0, 1.0f);
        matrix.put(1, 0.0f);
        matrix.put(2, 0.0f);
        matrix.put(3, 0.0f);
        matrix.put(4, 0.0f);
        matrix.put(5, 1.0f);
        matrix.put(6, 0.0f);
        matrix.put(7, 0.0f);
        matrix.put(8, 0.0f);
        matrix.put(9, 0.0f);
        matrix.put(10, 1.0f);
        matrix.put(11, 0.0f);
        matrix.put(12, 0.0f);
        matrix.put(13, 0.0f);
        matrix.put(14, 0.0f);
        matrix.put(15, 1.0f);
    }

    public void addTransformation(float T[]){
        float m0 = matrix.get(0)*T[0] +  matrix.get(4)*T[1] + matrix.get(8)*T[2] + matrix.get(12)*T[3];
        float m1 = matrix.get(1)*T[0] +  matrix.get(5)*T[1] + matrix.get(9)*T[2] + matrix.get(13)*T[3];
        float m2 = matrix.get(2)*T[0] +  matrix.get(6)*T[1] + matrix.get(10)*T[2] + matrix.get(14)*T[3];
        float m3 = matrix.get(3)*T[0] +  matrix.get(7)*T[1] + matrix.get(11)*T[2] + matrix.get(15)*T[3];
        float m4 = matrix.get(0)*T[4] +  matrix.get(4)*T[5] + matrix.get(8)*T[6] + matrix.get(12)*T[7];
        float m5 = matrix.get(1)*T[4] +  matrix.get(5)*T[5] + matrix.get(9)*T[6] + matrix.get(13)*T[7];
        float m6 = matrix.get(2)*T[4] +  matrix.get(6)*T[5] + matrix.get(10)*T[6] + matrix.get(14)*T[7];
        float m7 = matrix.get(3)*T[4] +  matrix.get(7)*T[5] + matrix.get(11)*T[6] + matrix.get(15)*T[7];
        float m8 = matrix.get(0)*T[8] +  matrix.get(4)*T[9] + matrix.get(8)*T[10] + matrix.get(12)*T[11];
        float m9 = matrix.get(1)*T[8] +  matrix.get(5)*T[9] + matrix.get(9)*T[10] + matrix.get(13)*T[11];
        float m10 = matrix.get(2)*T[8] +  matrix.get(6)*T[9] + matrix.get(10)*T[10] + matrix.get(14)*T[11];
        float m11 = matrix.get(3)*T[8] +  matrix.get(7)*T[9] + matrix.get(11)*T[10] + matrix.get(15)*T[11];
        float m12 = matrix.get(0)*T[12] +  matrix.get(4)*T[13] + matrix.get(8)*T[14] + matrix.get(12)*T[15];
        float m13 = matrix.get(1)*T[12] +  matrix.get(5)*T[13] + matrix.get(9)*T[14] + matrix.get(13)*T[15];
        float m14 = matrix.get(2)*T[12] +  matrix.get(6)*T[13] + matrix.get(10)*T[14] + matrix.get(14)*T[15];
        float m15 = matrix.get(3)*T[12] +  matrix.get(7)*T[13] + matrix.get(11)*T[14] + matrix.get(15)*T[15];

        matrix.put(0,m0);
        matrix.put(1,m1);
        matrix.put(2,m2);
        matrix.put(3,m3);
        matrix.put(4,m4);
        matrix.put(5,m5);
        matrix.put(6,m6);
        matrix.put(7,m7);
        matrix.put(8,m8);
        matrix.put(9,m9);
        matrix.put(10,m10);
        matrix.put(11,m11);
        matrix.put(12,m12);
        matrix.put(13,m13);
        matrix.put(14,m14);
        matrix.put(15,m15);
    }

    public void setShaderMatrix(int shaderMatrixPointer){
        Gdx.gl.glUniformMatrix4fv(shaderMatrixPointer, 1, false, matrix);
    }
    public void setShaderMatrix(){

    }
}
