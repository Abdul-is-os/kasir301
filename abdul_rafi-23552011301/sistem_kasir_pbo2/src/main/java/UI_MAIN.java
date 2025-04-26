import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UI_MAIN extends JFrame {
    
    public UI_MAIN() {
        setTitle("Sistem Kasir");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        
        // Tombol
        JButton btnTransaksi = new JButton("Transaksi Penjualan");
        JButton btnLaporan = new JButton("Lihat Laporan");
        JButton btnManajemenProduk = new JButton("Manajemen Produk");
        JButton btnKeluar = new JButton("Keluar");
        
        // Action listener
        btnTransaksi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tambahkan aksi untuk transaksi penjualan
                JOptionPane.showMessageDialog(null, "Fitur Transaksi Penjualan belum tersedia.");
            }
        });
        
        btnLaporan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tambahkan aksi untuk melihat laporan
                JOptionPane.showMessageDialog(null, "Fitur Lihat Laporan belum tersedia.");
            }
        });
        
        btnManajemenProduk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tambahkan aksi untuk manajemen produk
                JOptionPane.showMessageDialog(null, "Fitur Manajemen Produk belum tersedia.");
            }
        });
        
        btnKeluar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Tambahkan tombol ke panel
        panel.add(btnTransaksi);
        panel.add(btnLaporan);
        panel.add(btnManajemenProduk);
        panel.add(btnKeluar);
        
        // Tambahkan panel ke frame
        add(panel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UI_MAIN ui = new UI_MAIN();
            ui.setVisible(true);
        });
    }
}