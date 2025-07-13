package sistemlab.ruanglab;

public class RuangLab {
    private String kodeRuang;
    private String namaRuang;
    private int kapasitas;

    public RuangLab(String kodeRuang, String namaRuang, int kapasitas) {
        this.kodeRuang = kodeRuang;
        this.namaRuang = namaRuang;
        this.kapasitas = kapasitas;
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public String getKodeRuang() {
        return kodeRuang;
    }
}
