package sistemlab;

import sistemlab.aset.Aset;
import sistemlab.jadwal.Jadwal;
import sistemlab.peminjaman.Pengajuan;
import sistemlab.pengguna.*;
import sistemlab.ruanglab.RuangLab;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SistemLab {
    private Pengguna penggunaAktif;
    private List<Aset> daftarAset;
    private List<Jadwal> daftarJadwal;
    private List<RuangLab> daftarRuang;
    private List<Pengajuan> daftarPengajuan;

    public SistemLab(Pengguna pengguna, List<Aset> aset, List<Jadwal> jadwal, List<RuangLab> ruang, List<Pengajuan> pengajuan) {
        this.penggunaAktif = pengguna;
        this.daftarAset = aset;
        this.daftarJadwal = jadwal;
        this.daftarRuang = ruang;
        this.daftarPengajuan = pengajuan;
    }

    public void tampilkanMenu(Scanner scanner) {
        if (penggunaAktif instanceof Mahasiswa) {
            tampilkanMenuMahasiswa(scanner);
        } else if (penggunaAktif instanceof Dosen) {
            tampilkanMenuDosen(scanner);
        } else if (penggunaAktif instanceof AsistenLab) {
            tampilkanMenuAsistenLab(scanner);
        }
    }

    public void tampilkanMenuMahasiswa(Scanner scanner){
        int pilihan;
        do {
            System.out.println("\n===== MENU MAHASISWA =====");
            System.out.println("1. Tampilkan Jadwal Kegiatan");
            System.out.println("2. Tampilkan Aset Tersedia");
            System.out.println("3. Ajukan Peminjaman Aset");
            System.out.println("4. Ajukan Peminjaman Lab");
            System.out.println("0. Logout");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: manajemenJadwal(); break;
                case 2: tampilkanAsetTersedia(); break;
                case 3: peminjamanAset(scanner); break;
                case 4: peminjamanLab(scanner); break;
                case 0: System.out.println("Logout berhasil."); break;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);
    }

    private void tampilkanMenuDosen(Scanner scanner) {
        int pilihan;
        do {
            System.out.println("\n===== MENU DOSEN =====");
            System.out.println("1. Setujui/Tolak Pengajuan");
            System.out.println("2. Tampilkan Jadwal");
            System.out.println("3. Lihat Ketersediaan Aset");
            System.out.println("4. Manajemen Data");
            System.out.println("0. Logout");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt(); scanner.nextLine();
            switch (pilihan) {
                case 1: manajemenPengajuan(scanner); break;
                case 2: manajemenJadwal(); break;
                case 3: tampilkanAsetTersedia(); break;
                case 4: tampilkanMenuManajemenData(scanner); break;
                case 0: System.out.println("Logout berhasil."); break;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);
    }

    private void tampilkanMenuAsistenLab(Scanner scanner) {
        int pilihan;
        do {
            System.out.println("\n===== MENU ASISTEN LAB =====");
            System.out.println("1. Setujui/Tolak Pengajuan");
            System.out.println("2. Tampilkan Semua Jadwal");
            System.out.println("3. Lihat Stok Semua Aset");
            System.out.println("4. Manajemen Data");
            System.out.println("0. Logout");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt(); scanner.nextLine();
            switch (pilihan) {
                case 1: manajemenPengajuan(scanner); break;
                case 2: manajemenJadwal(); break;
                case 3: tampilkanAsetTersedia(); break;
                case 4: tampilkanMenuManajemenData(scanner); break;
                case 0: System.out.println("Logout berhasil."); break;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);
    }

    // --- BUAT METODE UNTUK MENU MANAJEMEN DATA ---
    private void tampilkanMenuManajemenData(Scanner scanner) {
        System.out.println("\n--- MENU MANAJEMEN DATA ---");
        System.out.println("1. Kelola Aset (Tambah/Hapus)");
        System.out.println("2. Kelola Jadwal (Tambah/Hapus)");
        System.out.println("0. Kembali ke Menu Utama");
        System.out.print("Pilih menu: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        switch(pilihan) {
            case 1:
                manajemenAset(scanner);
                break;
            case 2:
                manajemenJadwalAdmin(scanner);
                break;
            case 0:
                return; // Kembali
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private void manajemenJadwal() {
        System.out.println("\n--- JADWAL KEGIATAN LABORATORIUM ---");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("| %-30s | %-8s | %-15s | %-8s |\n", "Mata Kuliah/Kegiatan", "Hari", "Waktu", "Lab");
        System.out.println("-----------------------------------------------------------------------------------");
        if (daftarJadwal.isEmpty()) {
            System.out.println("| Tidak ada jadwal yang tersedia.                                                 |");
        } else {
            for (Jadwal jadwal : daftarJadwal) {
                System.out.println(jadwal.toString());
            }
        }
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print("Tekan Enter untuk kembali ke menu...");
        new Scanner(System.in).nextLine();
    }

    private void tampilkanAsetTersedia() {
        System.out.println("\n--- DAFTAR ASET TERSEDIA ---");
        System.out.println("----------------------------------------------");
        daftarAset.stream()
                .filter(aset -> aset.getJumlah() > 0)
                .forEach(System.out::println);
        System.out.println("----------------------------------------------");
        System.out.print("\nTekan Enter untuk kembali...");
        new Scanner(System.in).nextLine();
    }

    private void manajemenPengajuan(Scanner scanner) {
        System.out.println("\n--- MANAJEMEN PENGAJUAN ---");
        List<Pengajuan> perluProses = daftarPengajuan.stream()
                .filter(p -> p.getStatus() != Pengajuan.Status.APPROVED && p.getStatus() != Pengajuan.Status.REJECTED)
                .collect(Collectors.toList());

        if (perluProses.isEmpty()) {
            System.out.println("Tidak ada pengajuan aktif.");
            return;
        }
        perluProses.forEach(System.out::println);

        System.out.print("\nMasukkan ID pengajuan yang akan diproses (0 untuk batal): ");
        int idProses = scanner.nextInt(); scanner.nextLine();
        if (idProses == 0) return;

        Pengajuan target = perluProses.stream().filter(p -> p.getId() == idProses).findFirst().orElse(null);
        if (target == null) {
            System.out.println("ID tidak ditemukan.");
            return;
        }

        System.out.print("Pilih tindakan [1. Setujui] [2. Tolak]: ");
        int tindakan = scanner.nextInt(); scanner.nextLine();

        if (tindakan == 1) {
            target.setujui(penggunaAktif);
            System.out.println("Anda telah menyetujui pengajuan ID " + target.getId());
            if(target.getStatus() == Pengajuan.Status.APPROVED) {
                System.out.println("Persetujuan LENGKAP. Pengajuan telah DISETUJUI.");
                if(target.getTipe() == Pengajuan.Tipe.ASET) {
                    ((Aset) target.getItem()).pinjam(target.getJumlah());
                } else {
                    daftarJadwal.add((Jadwal) target.getItem());
                }
            } else {
                System.out.println("Menunggu persetujuan dari role lain.");
            }
        } else if (tindakan == 2) {
            target.tolak();
            System.out.println("Pengajuan ID " + target.getId() + " telah DITOLAK.");
        }
    }

    private void peminjamanAset(Scanner scanner) {
        System.out.println("\n--- FORMULIR PEMINJAMAN ASET ---");
        List<Aset> asetTersedia = daftarAset.stream()
                .filter(aset -> aset.getJumlah() > 0)
                .collect(Collectors.toList());

        if (asetTersedia.isEmpty()){
            System.out.println("Maaf, tidak ada aset yang tersedia untuk dipinjam saat ini.");
            return;
        }

        System.out.println("Daftar Aset yang Bisa Dipinjam:");
        for (int i = 0; i < asetTersedia.size(); i++) {
            System.out.println((i + 1) + ". " + asetTersedia.get(i));
        }

        System.out.print("Pilih nomor aset yang ingin dipinjam: ");
        int pilihanAset = scanner.nextInt();
        scanner.nextLine();

        if (pilihanAset < 1 || pilihanAset > asetTersedia.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Aset asetDipilih = asetTersedia.get(pilihanAset - 1);
        System.out.print("Masukkan jumlah yang ingin dipinjam (Maks: " + asetDipilih.getJumlah() + "): ");
        int jumlahPinjam = scanner.nextInt();
        scanner.nextLine();

        if (jumlahPinjam <= 0 || jumlahPinjam > asetDipilih.getJumlah()) {
            System.out.println("Jumlah tidak valid.");
            return;
        }

        Pengajuan pengajuanBaru = new Pengajuan(penggunaAktif, asetDipilih, jumlahPinjam);
        daftarPengajuan.add(pengajuanBaru);
        System.out.println("Pengajuan peminjaman berhasil dibuat dan menunggu persetujuan.");
    }

    private void peminjamanLab(Scanner scanner) {
        System.out.println("\n--- FORMULIR PEMINJAMAN RUANG LAB ---");
        System.out.println("Daftar Ruang Lab:");
        daftarRuang.forEach(lab -> System.out.println(lab.getKodeRuang() + ". " + lab.getNamaRuang()));
        System.out.print("Pilih nomor ruang lab: ");
        String kodeLab = scanner.nextLine();

        System.out.print("Masukkan hari (Contoh: Senin): ");
        String hari = scanner.nextLine();
        System.out.print("Masukkan waktu (Contoh: 13:00-15:00): ");
        String waktu = scanner.nextLine();
        System.out.print("Masukkan nama kegiatan: ");
        String kegiatan = scanner.nextLine();

        String finalKodeLab = kodeLab;
        boolean bentrok = daftarJadwal.stream().anyMatch(j ->
                j.getKodeLab().equals(finalKodeLab) && j.getHari().equalsIgnoreCase(hari)
        );

        if (bentrok) {
            System.out.println("Jadwal Ditolak. Lab sudah terisi pada hari tersebut.");
        } else {
            Jadwal jadwalBaru = new Jadwal(kegiatan, hari, waktu, kodeLab);
            daftarPengajuan.add(new Pengajuan(penggunaAktif, jadwalBaru));
            System.out.println("Pengajuan peminjaman lab berhasil dibuat dan menunggu persetujuan.");
        }
    }

    private void manajemenAset(Scanner scanner){
        System.out.println("\n--- MANAJEMEN DATA ASET ---");
        System.out.println("1. Tambah Aset Baru");
        System.out.println("2. Hapus Aset");
        System.out.println("Pilih tindakan");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        if(pilihan == 1){
            System.out.println("Masukkan nama aset baru");
            String nama = scanner.nextLine();
            System.out.print("Masukkan Jumlah Awal:");
            int jumlah = scanner.nextInt();
            scanner.nextLine();

            daftarAset.add(new Aset(nama, jumlah));
            System.out.println("Aset '" + nama + "' Berhasil ditambahkan.");
        } else if (pilihan == 2){
            System.out.println("Pilih aset yang akan dihapus:");
            for(int i = 0; i < daftarAset.size(); i++){
                System.out.println((i + 1) + ". " + daftarAset.get(i));
            }
            System.out.print("Masukkan nomor aset untuk dihapus: ");
            int nomorHapus = scanner.nextInt();
            scanner.nextLine();

            if(nomorHapus > 0 && nomorHapus <= daftarAset.size()){
                Aset asetDihapus = daftarAset.remove(nomorHapus - 1);
                System.out.println("Aset '" + asetDihapus.getNamaAset() + "' Berhasil dihapus.");
            } else {
                System.out.println("Nomor tidak valid.");
            }
        }
    }

    private void manajemenJadwalAdmin(Scanner scanner) {
        System.out.println("\n--- MANAJEMEN DATA JADWAL ---");
        System.out.println("1. Tambah Jadwal Baru");
        System.out.println("2. Hapus Jadwal");
        System.out.print("Pilih tindakan: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine();

        if (pilihan == 1) {
            System.out.print("Masukkan nama kegiatan/mata kuliah: ");
            String kegiatan = scanner.nextLine();
            System.out.print("Masukkan hari (Contoh: Jumat): ");
            String hari = scanner.nextLine();
            System.out.print("Masukkan waktu (Contoh: 15:00-17:00): ");
            String waktu = scanner.nextLine();
            System.out.print("Masukkan kode lab (1=AI, 2=RPL, 3=Data Science): ");
            String kodeLab = scanner.nextLine();

            daftarJadwal.add(new Jadwal(kegiatan, hari, waktu, kodeLab));
            System.out.println("Jadwal baru berhasil ditambahkan.");

        } else if (pilihan == 2) {
            System.out.println("Pilih jadwal yang akan dihapus:");
            for (int i = 0; i < daftarJadwal.size(); i++) {
                System.out.println((i + 1) + ". " + daftarJadwal.get(i));
            }
            System.out.print("Masukkan nomor jadwal untuk dihapus: ");
            int nomorHapus = scanner.nextInt();
            scanner.nextLine();

            if (nomorHapus > 0 && nomorHapus <= daftarJadwal.size()) {
                Jadwal jadwalDihapus = daftarJadwal.remove(nomorHapus - 1);
                System.out.println("Jadwal berhasil dihapus.");
            } else {
                System.out.println("Nomor tidak valid.");
            }
        }
    }

}