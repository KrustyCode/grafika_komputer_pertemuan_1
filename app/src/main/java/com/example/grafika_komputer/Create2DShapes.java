package com.example.grafika_komputer;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.opengles.GL10;
public class Create2DShapes {

    /**
     * Membuat FloatBuffer dari array float biasa agar bisa dibaca oleh OpenGL
     */

    public static FloatBuffer makeFloatBuffer(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }


    // ============================
    // ==== Menggambar Bentuk =====
    // ============================

    /**
     * Menggambar dua buah segitiga (GL_TRIANGLES)
     */
    public void Triangle(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Warna biru
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

        // Titik-titik segitiga (masing-masing 3 nilai: x, y, z)
        float[] vertices = {
                -1.0f, -0.1f, -5.0f,
                -0.5f, -0.25f, -5.0f,
                -0.75f, 0.25f, -5.0f,

                0.5f, -0.25f, -5.0f,
                1.0f, -0.25f, -5.0f,
                0.75f, 0.25f, -5.0f,
        };

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }


    /**
     * Menggambar segitiga menggunakan GL_TRIANGLE_STRIP (saling terhubung)
     */
    public void StripTriangle(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Warna hijau
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);

        float[] vertices = {
                -0.5f, -0.8f, -5.0f,
                -0.5f, -0.5f, -5.0f,
                -0.8f, -0.2f, -5.0f,
                -0.8f, -0.8f, -5.0f,
                -0.3f, -0.3f, -5.0f,
        };

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 5);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }


    /**
     * Menggambar segitiga dengan pola kipas (GL_TRIANGLE_FAN)
     */
    public void FanTriangle(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Warna hijau
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);

        float[] vertices = {
                0.5f, -0.8f, -5.0f,
                0.5f, -0.3f, -5.0f,
                0.8f, -0.8f, -5.0f,
                0.8f, -1.0f, -5.0f,
                0.3f, -1.2f, -5.0f,
        };

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 5);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }


    /**
     * (Eksperimen) Segitiga dengan shader, namun masih memakai GL10 (belum GLES20)
     * Shader code hanya disimpan sebagai string, tidak digunakan.
     */
    public void ShadeTriangle(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Warna biru transparan
        gl.glColor4f(0.0f, 0.0f, 1.0f, 0.5f);

        float[] vertices = {
                1.0f, -0.1f, -5.0f,
                0.5f, -0.25f, -5.0f,
                0.75f, 0.25f, -5.0f,
                0.5f, -0.25f, -5.0f,
                1.0f, -0.25f, -5.0f,
                0.75f, 0.25f, -5.0f,
        };

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }


    /**
     * Menggambar segi empat menggunakan garis (GL_LINE_LOOP)
     */
    public void Square(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Warna merah
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

        float[] vertices = {
                -0.2f, -0.8f, 0.0f,
                0.4f, -0.8f, 0.0f,
                0.4f, -1.8f, 0.0f,
                -0.2f, -1.8f, 0.0f,
        };

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
        gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
