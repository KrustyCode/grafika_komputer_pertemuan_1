package com.example.grafika_komputer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

// kelas untuk membuat garis secara manual
// menggunakan algoritma Bresenham dan DDA
public class CreateLines {
    // floatbuffer untuk menyimpan kumpulan koordinat titik
    private FloatBuffer vertexBuffer;
    //menyimpan jumlah titik yang akan digambar untuk membentuk garis
    private int pointCount;
    //list enum algoritma yang ada
    public enum Algorithm {
        BRESENHAM,
        DDA
    }

    // melacak algoritma yang dipilih
    private Algorithm current_algorithm;

    // fungsi untuk menginisialisasi garis yang ingin dibuat
    public CreateLines(float x0, float y0, float x1, float y1, Algorithm algorithm) {

        // array untuk mennyimpan kumpulan koordinat titik dari garis
        ArrayList<Float> vertices = new ArrayList<>();

        // menjalankan algoritma yang dipilih untuk menentukan koordinat titik yang perlu digambar
        if (algorithm == Algorithm.BRESENHAM) {
            bresenham(x0, y0, x1, y1, vertices);
            current_algorithm = Algorithm.BRESENHAM;
        } else {
            dda(x0, y0, x1, y1, vertices);
            current_algorithm = Algorithm.DDA;
        }

        // mendapatkan jumlah titik yang ada
        pointCount = vertices.size() / 3;

        // Convert ArrayList menjadi float array
        float[] vertexArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexArray[i] = vertices.get(i);
        }

        // Convert array ke FloatBuffer agar bisa dibaca oleh OpenGL
        ByteBuffer bb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }

    // Algoritma Garis Bresenham
    private void bresenham(float x0, float y0, float x1, float y1, ArrayList<Float> vertices) {
        //scale up koordinat awal dan akhir
        int ix0 = Math.round(x0 * 100);
        int iy0 = Math.round(y0 * 100);
        int ix1 = Math.round(x1 * 100);
        int iy1 = Math.round(y1 * 100);

        // menghitung derivatif atau perubahan yang dari titik awal ke titik akhir
        int dx = Math.abs(ix1 - ix0);
        int dy = Math.abs(iy1 - iy0);

        // cek arah garis (naik, turun, kanan, kiri)
        int sx = ix0 < ix1 ? 1 : -1;
        int sy = iy0 < iy1 ? 1 : -1;

        // selisih derivatif x dan y
        int D = dx - dy;

        // insialiasi titik awal
        int x = ix0;
        int y = iy0;

        // bentuk titik selama
        while (true) {
            // menambah titik ke daftar (x, y, z) --> Scale down kembali untuk mendapatkan koordinat asli
            vertices.add(x / 100.0f);
            vertices.add(y / 100.0f);
            vertices.add(0.0f); // koordinat z default

            // apabila titik saat ini sudah mencapai titik target maka proses berhenti
            if (x == ix1 && y == iy1) break;

            // mengecek dan melakukan pembaharuan titik
            int D2 = 2 * D;
            if (D2 > -dy) {
                D -= dy;
                x += sx;
            }

            if (D2 < dx) {
                D += dx;
                y += sy;
            }
        }
    }

    // Algoritma Garis DDA (Digital Differential Analyzer)
    private void dda(float x0, float y0, float x1, float y1, ArrayList<Float> vertices) {
        // menghitung derivatif x dan y --> scale up
        float dx = (x1 - x0) * 100;
        float dy = (y1 - y0) * 100;

        // step yang didapatkan dari derivation yang paling besar (dx atau dy)
        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) steps = 1;  // Mencegah 0 step, minimal 1 step

        // menghitung nilai yang akan ditambahkan ke koordinat tiap iterasi
        float xInc = dx / steps;
        float yInc = dy / steps;

        // inisialiasi titik awal
        float x = x0 * 100;
        float y = y0 * 100;

        //perulangan untuk membentuk titik sejumlah step
        for (int i = 0; i <= steps; i++) {
            vertices.add(x / 100.0f);  // Scale down untuk mendapatkan koordinat asli
            vertices.add(y / 100.0f);
            vertices.add(0.0f); //koordinat z default

            // memperbarui nilai koordinat saat ini
            x += xInc;
            y += yInc;
        }
    }

    // fungsi untuk yang dipanggil untuk mulai menggambar garis
    public void onDraw(GL10 gl) {
        // menyalakan client state untuk vertex array / sesi gambar
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Set warna RBGA untuk garis yang di gambar
        if (this.current_algorithm == Algorithm.BRESENHAM) {
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f); // biru untuk bresenham
        }
        else if (this.current_algorithm == Algorithm.DDA){
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // merah untuk dda
        };

        // Point to vertex buffer (3 components: x, y, z)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // set ukuran tiap titik yang akan digambar
        gl.glPointSize(5.0f);

        // Gambar titik-titik yang menjadi bagian dari garis
        gl.glDrawArrays(GL10.GL_POINTS, 0, pointCount);

        // tutup sesi gambar
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}