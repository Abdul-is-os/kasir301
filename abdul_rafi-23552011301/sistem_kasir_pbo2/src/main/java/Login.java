import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    
    public Login() {
        setTitle("Cashier System 301");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        // Judul
        JLabel titleLabel = new JLabel("CASHIER SYSTEM 301");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spasi

        // Username
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());
        JLabel userLabel = new JLabel("USERNAME:");
        JTextField userField = new JTextField(20);
        userPanel.add(userLabel);
        userPanel.add(userField);
        panel.add(userPanel);
        
        // Password
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new FlowLayout());
        JLabel passLabel = new JLabel("PASSWORD:");
        JPasswordField passField = new JPasswordField(20);
        passPanel.add(passLabel);
        passPanel.add(passField);
        panel.add(passPanel);
        
        // Login
        JButton loginButton = new JButton("ENTER THE SYSTEM");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(100, 100, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        
        // Kode di bagian ActionListener tombol login di kelas Login
        loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            // Arahkan ke halaman utama
            Main main = new Main();
            main.setVisible(true);
            dispose(); // Tutup jendela login
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }
});
        
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spasi
        panel.add(loginButton);
        
        // Tambahkan panel ke frame
        add(panel);
    }
    
    private boolean authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password)); // Pastikan password di-hash saat memverifikasi
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Jika ada hasil, login berhasil
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private String hashPassword(String password) {
        // Implementasi hashing password (misalnya, SHA-256)
        return password; // Ganti dengan implementasi hashing yang sesuai
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login ui = new Login();
            ui.setVisible(true);
        });
    }
}