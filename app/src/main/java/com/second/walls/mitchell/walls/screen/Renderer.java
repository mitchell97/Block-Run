package com.second.walls.mitchell.walls.screen;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Mitchell on 2017-07-25.
 */
public class Renderer implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Square square = new Square();
            square.draw(gl);

        square.setVerticesAndDraw(0.8f, gl, (byte) 255);

        square.setVerticesAndDraw(0.7f, gl, (byte) 150);
        square.setVerticesAndDraw(0.6f, gl, (byte) 100);
        square.setVerticesAndDraw(0.5f, gl, (byte) 80);
        square.setVerticesAndDraw(0.4f, gl, (byte) 50);
    }



    class Square {

        FloatBuffer vertexbuffer;

        ByteBuffer indicesBuffer;


        float vetices[] = {
//
//            -0.5f,0.5f,0.0f,
//            0.5f,0.5f,0.0f,
//            0.5f,-0.5f,0.0f,
//            -0.5f,0.5f,0.0f

                -1.0f, -1.0f,
                1.0f, -1.0f,
                -1.0f, 1.0f,
                1.0f, 1.0f

        };


        byte indices[] = {0, 1, 2, 2, 1, 3};

        public Square() {

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vetices.length * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertexbuffer = byteBuffer.asFloatBuffer();
            vertexbuffer.put(vetices);
            vertexbuffer.position(0);


            indicesBuffer = ByteBuffer.allocateDirect(indices.length);
//        indicesBuffer.order(ByteOrder.nativeOrder());
            indicesBuffer.put(indices);
            indicesBuffer.position(0);

        }

        public void setVerticesAndDraw(Float value, GL10 gl, byte color) {
            FloatBuffer vertexbuffer;
            ByteBuffer indicesBuffer;
            ByteBuffer mColorBuffer;

            byte indices[] = {0, 1, 2, 0, 2, 3};

            float vetices[] = {//
                    -value, value, 0.0f,
                    value, value, 0.0f,
                    value, -value, 0.0f,
                    -value, -value, 0.0f
            };

            byte colors[] = //3
                    {color, color, 0, color,
                            0, color, color, color,
                            0, 0, 0, color,
                            color, 0, color, 0
                    };


            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vetices.length * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertexbuffer = byteBuffer.asFloatBuffer();
            vertexbuffer.put(vetices);
            vertexbuffer.position(0);

            indicesBuffer = ByteBuffer.allocateDirect(indices.length);
            indicesBuffer.put(indices);
            indicesBuffer.position(0);

            mColorBuffer = ByteBuffer.allocateDirect(colors.length);
            mColorBuffer.put(colors);
            mColorBuffer.position(0);


            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexbuffer);
            gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mColorBuffer);

            gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indicesBuffer);
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        }

        public void draw(GL10 gl) {

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexbuffer);

            gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indicesBuffer);

            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);


        }

    }
}
