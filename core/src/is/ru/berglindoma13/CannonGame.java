package is.ru.berglindoma13;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.BufferUtils;

public class CannonGame extends ApplicationAdapter {

	private FloatBuffer vertexBuffer;

	private FloatBuffer modelMatrixBuffer;
	private FloatBuffer projectionMatrix;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;

	//private ModelMatrix modelMatrix;

	private int modelMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;

    //Position of the cannon
    private float cannonX;
    private float cannonY;
	private float cannonZ;

    //rotation of the cannon
    private float angle;

    //position of goal
    private float goalX;
    private float goalY;
    private float goalZ;
    private boolean directionRight;
    private boolean directionLeft;

    //position of CannonBall
    private boolean ball_moving;
    private CannonBall cannonball;

    //CannonBall vector
    private Vector ball_speed;


	@Override
	public void create () {

	    //Cannon initialization
	    cannonX = 512;
        cannonY = 50;
		cannonZ = 0;
        angle = 0.0f;

        //Goal initialization
        goalX = 50;
        goalY = Gdx.graphics.getHeight() - 20;
        goalZ = 0;
        directionLeft = false;
        directionRight = true;

        //CannonnBall initalization
        ball_moving = false;
        ball_speed = new Vector();
        ball_speed.x = 0.0f;
        ball_speed.y = 0.0f;
        cannonball = new CannonBall();

        ModelMatrix.main = new ModelMatrix();

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("core/assets/shaders/simple2D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("core/assets/shaders/simple2D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();

		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);

		float[] pm = new float[16];

		pm[0] = 2.0f / Gdx.graphics.getWidth(); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -1.0f;
		pm[1] = 0.0f; pm[5] = 2.0f / Gdx.graphics.getHeight(); pm[9] = 0.0f; pm[13] = -1.0f;
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		projectionMatrix = BufferUtils.newFloatBuffer(16);
		projectionMatrix.put(pm);
		projectionMatrix.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);


		float[] mm = new float[16];

		mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
		mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
		mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
		mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

		modelMatrixBuffer = BufferUtils.newFloatBuffer(16);
		modelMatrixBuffer.put(mm);
		modelMatrixBuffer.rewind();

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrixBuffer);

		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(colorLoc, 0.7f, 0.2f, 0, 1);
		Gdx.gl.glClearColor(0.4f, 0.6f, 1.0f, 1.0f);


		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
		RectangleGraphic.create(positionLoc);
        CircleGraphic.create(positionLoc);

	}

	public void update(){
        float deltaTime = Gdx.graphics.getDeltaTime();

	    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (angle <= 45.0f) {
                angle += 5.0f;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(angle >= -45.0f){
                angle -= 5.0f;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            ball_speed.x = angle;
            ball_speed.y = angle;
            ball_moving = true;
        }
        if (ball_moving){
            cannonball.update();
        }

        if ((goalX <= Gdx.graphics.getWidth() - 50) && directionRight){
            goalX += 150.0f * deltaTime;
        }
        else if(goalX >= 50 && directionLeft){
            goalX -= 150.0f * deltaTime;
        }
        else if(goalX > Gdx.graphics.getWidth()-50){
            directionRight = false;
            directionLeft = true;
        }
        else{
            directionLeft = false;
            directionRight = true;
        }
    }

    public void display(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //drawing the goal
        //ModelMatrix.main.pushMatrix();
        ModelMatrix.main.setModelMatrixTranslation(goalX,goalY,goalZ);
        ModelMatrix.main.setModelMatrixRotation(90);
        ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
        Gdx.gl.glUniform4f(colorLoc, 0.3f, 0.2f, 0, 1);
        RectangleGraphic.drawSquareLines();

        //drawing the cannon
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.setModelMatrixTranslation(cannonX,cannonY,cannonZ);
        ModelMatrix.main.setModelMatrixRotation(angle);
        ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
        Gdx.gl.glUniform4f(colorLoc, 0.3f, 0.2f, 0, 1);
        RectangleGraphic.drawSolidSquare();

        if(ball_moving){
            ModelMatrix.main.loadIdentityMatrix();
            ModelMatrix.main.setModelMatrixTranslation(cannonX,cannonY,cannonZ);
            ModelMatrix.main.setModelMatrixScale(5,5,0);
            ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
            Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.5f, 0, 1);
            CircleGraphic.drawSolidCircle();
            //cannonball.display(colorLoc);
        }
    }

	@Override
	public void render () {
		update();
        display();
        //ModelMatrix.main.loadIdentityMatrix();
	}



}