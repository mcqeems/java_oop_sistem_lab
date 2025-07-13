
package sistemlab.jadwal;

public class Jadwal {
    private String namaKegiatan;
    private String hari;
    private String waktu;
    private String kodeLab; // Ditambahkan

    public Jadwal(String namaKegiatan, String hari, String waktu, String kodeLab) {
        this.namaKegiatan = namaKegiatan;
        this.hari = hari;
        this.waktu = waktu;
        this.kodeLab = kodeLab;
    }

    public String getKodeLab() { return kodeLab; }
    public String getHari() { return hari; }
    public String getWaktu() { return waktu; }


    @Override
    public String toString() {
        return String.format("| %-30s | %-8s | %-15s | Lab: %-3s |",
                namaKegiatan, hari, waktu, kodeLab);
    }
}