 
package sistemlab;

import sistemlab.aset.Aset;
import sistemlab.jadwal.Jadwal;
import sistemlab.peminjaman.Pengajuan;
import sistemlab.pengguna.*;
import sistemlab.ruanglab.RuangLab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
 
 
    private static List<Aset> daftarAset = new ArrayList<>();
    private static List<Jadwal> daftarJadwal = new ArrayList<>();
    private static List<RuangLab> daftarRuang = new ArrayList<>();
    private static List<Pengajuan> daftarPengajuan = new ArrayList<>();

    public static void main(String[] args) {
 
        inisialisasiData();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            Pengguna penggunaSaatIni = login(scanner);
            if (penggunaSaatIni == null) {
 
                System.out.println("Terima kasih telah menggunakan sistem. Sampai jumpa!");
                break;
            }

            System.out.println("\nLogin berhasil! Selamat datang, " + penggunaSaatIni.getNama() + ".");
            System.out.println("---------------------------------------------");
 
            SistemLab sistem = new SistemLab(penggunaSaatIni, daftarAset, daftarJadwal, daftarRuang, daftarPengajuan);
            sistem.tampilkanMenu(scanner); // Menu berjalan sampai pengguna logout
        }

        scanner.close();
    }

    public static Pengguna login(Scanner scanner) {
        System.out.println("\n=============================================");
        System.out.println("SELAMAT DATANG DI SISTEM MANAJEMEN LABORATORIUM");
        System.out.println("=============================================");
        System.out.println("Login sebagai:");
        System.out.println("1. Mahasiswa");
        System.out.println("2. Dosen");
        System.out.println("3. Asisten Lab");
        System.out.println("0. Keluar dari Aplikasi");
        System.out.print("Pilih role Anda: ");

        int pilihanRole = scanner.nextInt();
        scanner.nextLine();

        if (pilihanRole == 0) {
            return null; // Sinyal untuk keluar dari program
        }

        System.out.print("Masukkan ID Anda: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Anda: ");
        String nama = scanner.nextLine();

        switch (pilihanRole) {
            case 1: return new Mahasiswa(id, nama);
            case 2: return new Dosen(id, nama);
            case 3: return new AsistenLab(id, nama);
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                return login(scanner); // Rekursif jika pilihan salah
        }
    }

    public static void inisialisasiData() {
 
        try (BufferedReader br = new BufferedReader(new FileReader("list_aset.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 2) {
                    daftarAset.add(new Aset(data[0].trim(), Integer.parseInt(data[1].trim())));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error membaca list_aset.txt");
        }
 
        try (BufferedReader br = new BufferedReader(new FileReader("jadwal_siakad.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) { // Mata Kuliah, Hari, Waktu, KodeLab
                    daftarJadwal.add(new Jadwal(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error membaca jadwal_siakad.txt");
        }
 
        daftarRuang.add(new RuangLab("1", "Lab AI", 40));
        daftarRuang.add(new RuangLab("2", "Lab RPL", 35));
        daftarRuang.add(new RuangLab("3", "Lab Data Science", 30));
    }
}