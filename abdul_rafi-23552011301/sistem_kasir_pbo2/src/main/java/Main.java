import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public Main() {
        setTitle("Cashier System 301");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        // Judul
        JLabel titleLabel = new JLabel("CASHIER SYSTEM 301");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spasi

        // Tombol Menu
        JButton transaksiButton = createMenuButton("TRANSAKSI PENJUALAN");
        JButton laporanButton = createMenuButton("LIHAT LAPORAN");
        JButton manajemenButton = createMenuButton("MANAJEMEN PRODUK");
        JButton keluarButton = createMenuButton("KELUAR");

        // Menambahkan tombol ke panel
        panel.add(transaksiButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi
        panel.add(laporanButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi
        panel.add(manajemenButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi
        panel.add(keluarButton);

        // Action listener untuk tombol transaksi
        transaksiButton.addActionListener(e -> {
            Transaksi transaksi = new Transaksi();
            transaksi.setVisible(true);
            dispose(); // Tutup jendela utama
        });

        // Action listener untuk tombol keluar
        keluarButton.addActionListener(e -> {
            System.exit(0);
        });

        // Tambahkan panel ke frame
        add(panel);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(100, 100, 255));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainUI = new Main();
            mainUI.setVisible(true);
        });
    }
}