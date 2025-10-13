package com.example.grafika_komputer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// bertugas untuk menyimpan koordinat titik (vertices) dan menyediakan method
// untuk menggambar titik-titik tersebut menggunakan OpenGL.
public class CreatePoints {

    // kumpulan posisi (x, y) untuk setiap verteks (24)
    private final float[] vertices = {
            -1.0f, 0.3f,  // V1
            -0.8f, 0.0f,  // V2
            -0.6f, 0.0f,  // V3
            -0.4f, 0.0f,  // V4
            -0.2f, 0.0f,  // V5
            0.0f, 0.0f,   // V6
            0.2f, 0.0f,   // V7
            0.4f, 0.0f,   // V8
            0.6f, 0.0f,   // V9
            0.8f, 0.3f,   // V10
            1.0f, 0.0f,   // V11
            1.2f, 0.0f,   // V12
            1.4f, 0.0f,   // V13
            1.6f, 0.0f,   // V14
            1.8f, 0.0f,   // V15
            2.0f, 0.0f,   // V16
            2.2f, 0.0f,   // V17
            2.4f, 0.0f,   // V18
            2.6f, 0.0f,   // V19
            2.8f, 0.0f,   // V20
            3.0f, 0.0f,   // V21
            3.2f, 0.3f,   // V22
            3.4f, 0.3f,   // V23
            3.6f, 0.3f    // V24
    };

    // --- Method untuk menyimpan float array ke dalam memori sehingga dapat diakses oleh OpenGL---
    public static FloatBuffer makeFloatBuffer(float[] arr) {
        // alokasikan buffer mentah (ByteBuffer) sebesar jumlah data * 4 byte (ukuran float)
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);

        // atur urutan byte sesuai native order (menyocokkan secara dinamis dengan arsitektur CPU perangkat)
        bb.order(ByteOrder.nativeOrder());

        // konversi ByteBuffer menjadi FloatBuffer (supaya bisa simpan data float)
        FloatBuffer fb = bb.asFloatBuffer();

        // masukkan data array float ke dalam FloatBuffer
        fb.put(arr);

        // reset posisi pointer buffer ke awal (siap dibaca oleh OpenGL)
        fb.position(0);

        // kembalikan FloatBuffer yang sudah berisi data
        return fb;
    }

    // --- Method untuk menggambar verteks ---
    public void draw_points(GL10 gl) {
        // aktifkan penggunaan array vertex
        // ---> memberitahu OpenGL untuk membaca data posisi titik dari buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // menyimpan jumlah verteks yang ada
        int number_of_vertices = vertices.length/2;

        // daftar verteks spesial: 0=V1, 9=V10, 21=V22, 22=V23, 23=V24
        Set<Integer> special = new HashSet<>(Arrays.asList(0, 9, 21, 22, 23));

        // Perulangan untuk menggambar titik
        for (int index = 0; index < number_of_vertices; index++){

            // warna biru untuk V1, V10, V22, V23, V24
            if(special.contains(index)){
                gl.glColor4f(0f, 0f, 1f, 1f);
            }

            // warna merah untuk verteks lainnya
            else{
                gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
            }

            // inisialisasi verteks baru
            gl.glVertexPointer(2, GL10.GL_FLOAT, 0,
                    makeFloatBuffer(new float[]{vertices[index * 2], vertices[index * 2 + 1]})
            );

            // gambar verteks ke layar
            gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
        }

        // Nonaktifkan penggunaan array vertex
        // ---> Reset state setelah menggambar
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

}
