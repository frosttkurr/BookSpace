package id.syakurr.bookspace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.syakurr.bookspace.model.BukuHandler;
import id.syakurr.bookspace.model.PinjamHandler;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper (Context context) {
        super(context, "bookspace.db", null, 1);
//        context.deleteDatabase("bookspace.db"); // hapus database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_buku(id INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT, kategori TEXT)");
        db.execSQL("CREATE TABLE tb_pinjam(id INTEGER PRIMARY KEY AUTOINCREMENT, nik TEXT, nama TEXT, judul TEXT, alamat TEXT, tgl_pinjam TEXT, tgl_kembali TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_buku");
        db.execSQL("DROP TABLE IF EXISTS tb_pinjam");
    }

    public boolean tambahBuku(BukuHandler bukuHandler) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("judul", bukuHandler.getJudul());
        values.put("kategori", bukuHandler.getKategori());
        return db.insert("tb_buku", null, values) > 0;
    }

    public Cursor tampilkanBukuEdukasi() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'EDUKASI'", null);
    }

    public Cursor tampilkanBukuIlmiah() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'ILMIAH'", null);
    }

    public Cursor tampilkanBukuFiksi() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'FIKSI'", null);
    }

    public boolean tambahPinjam(PinjamHandler pinjamHandler) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nik", pinjamHandler.getNik());
        values.put("nama", pinjamHandler.getNama());
        values.put("judul", pinjamHandler.getJudul());
        values.put("alamat", pinjamHandler.getAlamat());
        values.put("tgl_pinjam", pinjamHandler.getTgl_pinjam());
        values.put("tgl_kembali", pinjamHandler.getTgl_kembali());
        values.put("status", pinjamHandler.getStatus());
        return db.insert("tb_pinjam", null, values) > 0;
    }

    public Cursor tampilkanPinjam() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_pinjam order by tgl_pinjam", null);
    }

    public Cursor detailPinjam(int id_pinjam) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_pinjam where id = " + id_pinjam, null);
    }

    public boolean suntingPinjam(PinjamHandler pinjamHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tgl_kembali", pinjamHandler.getTgl_kembali());
        return db.update("tb_pinjam", values, "id" + "=" + id_pinjam, null) > 0;
    }

    public boolean hapusPinjam (int id_pinjam) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete("tb_pinjam", "id" + "=" + id_pinjam, null) > 0;
    }

    public boolean kembaliPinjam(PinjamHandler pinjamHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", pinjamHandler.getStatus());
        return db.update("tb_pinjam", values, "id" + "=" + id_pinjam, null) > 0;
    }
}
