 
package sistemlab.peminjaman;

import sistemlab.aset.Aset;
import sistemlab.jadwal.Jadwal;
import sistemlab.pengguna.AsistenLab;
import sistemlab.pengguna.Dosen;
import sistemlab.pengguna.Pengguna;

public class Pengajuan {
    public enum Status { PENDING, PARTIALLY_APPROVED, APPROVED, REJECTED }
    public enum Tipe { ASET, LAB }

    private static int counter = 0;
    private int id;
    private Pengguna pemohon;
    private Object item;
    private int jumlah; // Untuk peminjaman aset
    private Status status;
    private Tipe tipe;
 
    private boolean disetujuiAslab = false;
    private boolean disetujuiDosen = false;
 
    public Pengajuan(Pengguna pemohon, Aset aset, int jumlah) {
        this.id = ++counter;
        this.pemohon = pemohon;
        this.item = aset;
        this.jumlah = jumlah;
        this.tipe = Tipe.ASET;
        this.status = Status.PENDING;
    }
 
    public Pengajuan(Pengguna pemohon, Jadwal jadwal) {
        this.id = ++counter;
        this.pemohon = pemohon;
        this.item = jadwal;
        this.jumlah = 1; // Lab hanya bisa 1
        this.tipe = Tipe.LAB;
        this.status = Status.PENDING;
    }
 
    public int getId() { return id; }
    public Status getStatus() { return status; }
    public Tipe getTipe() { return tipe; }
    public Object getItem() { return item; }
    public int getJumlah() { return jumlah; }

    public void setujui(Pengguna approver) {
        if (approver instanceof AsistenLab) {
            this.disetujuiAslab = true;
        } else if (approver instanceof Dosen) {
            this.disetujuiDosen = true;
        }
        updateStatus();
    }

    public void tolak() {
        this.status = Status.REJECTED;
    }

    private void updateStatus() {
        if (disetujuiAslab && disetujuiDosen) {
            this.status = Status.APPROVED;
        } else if (disetujuiAslab || disetujuiDosen) {
            this.status = Status.PARTIALLY_APPROVED;
        }
    }

    @Override
    public String toString() {
        String detailItem = "";
        if (tipe == Tipe.ASET) {
            detailItem = "Aset: " + ((Aset) item).getNamaAset() + " (Jumlah: " + jumlah + ")";
        } else {
            Jadwal j = (Jadwal) item;
            detailItem = "Lab: " + j.getKodeLab() + " | Hari: " + j.getHari() + " | Waktu: " + j.getWaktu();
        }
        return String.format("ID: %d | Pemohon: %-15s | Status: %-18s | Detail: %s",
                id, pemohon.getNama(), status, detailItem);
    }
}