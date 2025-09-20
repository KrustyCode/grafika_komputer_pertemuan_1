package com.example.grafika_komputer;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

// Activity utama aplikasi yang menampilkan GLSurfaceView.
public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onResume () {
        super.onResume();
        // melanjutkan kembali GLSurfaceView saat Activity kembali aktif
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause () {
        super.onPause();
        // menjeda GLSurfaceView saat Activity berhenti sementara
        glSurfaceView.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // membuat AppActivity dalam mode fullscreen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // membuat GLSurfaceView baru untuk menampilkan OpenGL
        glSurfaceView = new GLSurfaceView(this);

        // menghubungkan GLSurfaceView dengan renderer (ESRender)
        glSurfaceView.setRenderer(new ESRender());

        // menjadikan GLSurfaceView sebagai tampilan utama Activity
        setContentView(glSurfaceView);
    }
}