package com.example.grafika_komputer;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


// class ESRender bertugas sebagai Renderer untuk GLSurfaceView
// renderer ini yang akan mengatur bagaimana objek ditampilkan di layar
public class ESRender implements GLSurfaceView.Renderer {

    // objek untuk menggambar titik/vertex yang dibuat dari class CreatePoints
    private CreatePoints points_object;
    private CreateLines bresenham_line_object;
    private CreateLines dda_line_object;
    private Create2DShapes shape_2d;

    public ESRender() {
        // membuat objek ketika renderer diinisialisasi
        this.points_object = new CreatePoints();
        this.bresenham_line_object =  new CreateLines(0.0f, 0.0f, 10.0f, 5.0f, CreateLines.Algorithm.BRESENHAM);
        this.dda_line_object =  new CreateLines(0.5f, 0.5f, 10.0f, 5.0f, CreateLines.Algorithm.DDA);
        this.shape_2d = new Create2DShapes();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // dipanggil sekali ketika surface OpenGL pertama kali dibuat
        // set warna background menjadi putih (R=1, G=1, B=1, A=1)
        gl.glClearColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // dipanggil ketika ukuran layar/surface berubah (misal rotasi)
        // set area gambar sesuai dengan ukuran layar
        gl.glViewport(0, 0, width, height);

        // atur proyeksi kamera (perspektif 3D)
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / height, 1f, 100f);

        // kembali ke mode model-view (untuk menggambar objek)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // dipanggil setiap frame (refresh layar)
        // bersihkan layar dengan warna background & buffer depth
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // simpan posisi transformasi (supaya bisa dikembalikan nanti)
        gl.glPushMatrix();

        // geser kamera ke belakang (supaya titik bisa terlihat di layar)
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        // atur ukuran titik yang akan digambar
        gl.glPointSize(20f);

        // aktifkan smoothing supaya titik terlihat lebih halus (bulat)
        gl.glEnable(GL10.GL_POINT_SMOOTH);

        // Gambar segitiga untuk batang pohon
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.7f,0.0f);
        gl.glScalef(0.3f, 1.0f, 1.0f);
        shape_2d.Triangle(gl);
        gl.glPopMatrix();

        // Gambar lingkaran untuk daun pohon
        gl.glPushMatrix();
        gl.glTranslatef(-0.4f, -0.15f, 0.0f);
        shape_2d.Circle(gl, 0.25f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(-0.3f, 0.3f, 0.0f);
        shape_2d.Circle(gl, 0.3f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.4f, 0.0f);
        shape_2d.Circle(gl, 0.2f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.3f, 0.25f, 0.0f);
        shape_2d.Circle(gl, 0.25f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.55f, 0.0f, 0.0f);
        shape_2d.Circle(gl, 0.2f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.1f, -0.1f, 0.0f);
        shape_2d.Circle(gl, 0.4f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.2f, 0.5f, 0.0f);
        gl.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
        float shx = 0.5f; // shear in X
        float shy = 0.0f; // shear in Y

        float[] shearMatrix = {
                1,  shy, 0, 0,
                shx, 1,  0, 0,
                0,   0,  1, 0,
                0,   0,  0, 1
        };


        gl.glMultMatrixf(shearMatrix, 0);

        shape_2d.Circle(gl, 0.2f);
        gl.glPopMatrix();


        gl.glPopMatrix();
    }
}
