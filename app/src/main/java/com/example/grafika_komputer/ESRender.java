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
        this.triangle = new Create2DShapes();
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

        // gambar titik
        //points_object.draw_points(gl);

        //gambar garis menggunakan bresenham
        //bresenham_line_object.onDraw(gl);

        //gambar garis menggunakan dda
        //da_line_object.onDraw(gl);

        // gambar segitiga dan persegi
        shape_2d.Triangle(gl);
        shape_2d.Square(gl);

        // kembalikan transformasi ke kondisi semula
        gl.glPopMatrix();
    }
}
