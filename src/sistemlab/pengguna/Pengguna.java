package sistemlab.pengguna;

public abstract class Pengguna {
    protected String id;
    protected String nama;
    protected String role;

    public Pengguna (String id, String nama, String role) {
        this.id = id;
        this.nama = nama;
        this.role = role;
    }

    public void tampilkanInfo(){
        System.out.println("ID      :" + id);
        System.out.println("Nama    :" + nama);
        System.out.println("Role    :" + role);
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getRole() {
        return role;
    }
}
