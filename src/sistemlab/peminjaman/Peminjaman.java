package sistemlab.peminjaman;

import sistemlab.aset.Aset;
import sistemlab.pengguna.Pengguna;
import java.time.LocalDate;

public class Peminjaman {
    private Pengguna peminjam;
    private Aset aset;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private boolean disetujui;

    public Peminjaman(Pengguna peminjam, Aset aset) {
        this.peminjam = peminjam;
        this.aset = aset;
        this.tanggalPinjam = LocalDate.now();
        this.disetujui = false; // Status awal belum disetujui
    }
}