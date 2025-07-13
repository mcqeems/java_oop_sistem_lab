package sistemlab.aset;

public class Aset {
    private String namaAset;
    private int jumlah;

    public Aset(String namaAset, int jumlah) {
        this.namaAset = namaAset;
        this.jumlah = jumlah;
    }

    public String getNamaAset() {
        return namaAset;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void pinjam(int jumlahPinjam){
        if(this.jumlah >= jumlahPinjam){
            this.jumlah -= jumlahPinjam;
        }
    }

    public void kembali(){
        this.jumlah++;
    }

    @Override
    public String toString() {
        return String.format("%-25s | Jumlah Tersedia: %d", namaAset, jumlah);
    }
}
