import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Transaksi extends JFrame {
    private JTable productTable;
    private JTextArea selectedProductArea;
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JTextField customerPhoneField;
    private JTextField totalPriceField;
    private JTextField discountField;

    public Transaksi() {
        setTitle("Transaksi Penjualan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Panel input data pelanggan
        JPanel customerPanel = new JPanel(new GridLayout(3, 2));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Data Pelanggan"));

        customerPanel.add(new JLabel("Nama:"));
        customerNameField = new JTextField();
        customerPanel.add(customerNameField);

        customerPanel.add(new JLabel("Email:"));
        customerEmailField = new JTextField();
        customerPanel.add(customerEmailField);

        customerPanel.add(new JLabel("Telepon:"));
        customerPhoneField = new JTextField();
        customerPanel.add(customerPhoneField);

        // Panel tabel produk
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Daftar Produk"));

        // Model tabel
        String[] columnNames = {"ID Produk", "Nama Produk", "Harga", "Stok"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productTable);
        productPanel.add(scrollPane, BorderLayout.CENTER);

        // Mengambil data produk dari database
        loadProducts(model);

        // JTextArea untuk produk yang dipilih
        selectedProductArea = new JTextArea(10, 20);
        selectedProductArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(selectedProductArea);
        areaScrollPane.setBorder(BorderFactory.createTitledBorder("Produk Dipilih"));

        // Panel untuk tombol dan metode pembayaran
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // Tombol panel
        JPanel buttonPanel = new JPanel();
        JButton selectButton = new JButton("Pilih Produk");
        JButton completeButton = new JButton("Selesaikan Pesanan");
        JButton cancelButton = new JButton("Batal");

        buttonPanel.add(selectButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(cancelButton);

        // Opsi metode pembayaran
        JPanel paymentPanel = new JPanel(new FlowLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Metode Pembayaran"));
        String[] paymentMethods = {"Cash", "Debit/Credit Card", "QRIS"};
        JComboBox<String> paymentComboBox = new JComboBox<>(paymentMethods);
        paymentPanel.add(paymentComboBox);

        // Panel untuk total harga dan diskon
        JPanel pricePanel = new JPanel(new FlowLayout());
        pricePanel.add(new JLabel("Total Harga:"));
        totalPriceField = new JTextField(10);
        totalPriceField.setEditable(false);
        pricePanel.add(totalPriceField);

        pricePanel.add(new JLabel("Diskon (%):"));
        discountField = new JTextField(5);
        discountField.addActionListener(e -> updateTotalPrice());
        pricePanel.add(discountField);

        // Menambahkan semua panel ke main panel
        mainPanel.add(customerPanel, BorderLayout.NORTH);
        mainPanel.add(productPanel, BorderLayout.CENTER);
        mainPanel.add(areaScrollPane, BorderLayout.EAST);
        
        // Menambahkan tombol dan metode pembayaran di panel bawah
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(paymentPanel, BorderLayout.EAST);
        bottomPanel.add(pricePanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Tambahkan main panel ke frame
        add(mainPanel);

        // Action listener untuk tombol "Pilih Produk"
        selectButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) model.getValueAt(selectedRow, 0);
                String name = (String) model.getValueAt(selectedRow, 1);
                int price = (int) model.getValueAt(selectedRow, 2);
                selectedProductArea.append("ID: " + id + ", Nama: " + name + ", Harga: " + price + "\n");

                // Update total harga
                updateTotalPrice();
            } else {
                JOptionPane.showMessageDialog(this, "Silakan pilih produk dari tabel.");
            }
        });

        // Action listener untuk tombol "Selesaikan Pesanan"
        completeButton.addActionListener(e -> {
            String paymentMethod = (String) paymentComboBox.getSelectedItem();
            if ("Cash".equals(paymentMethod)) {
                showCashPaymentPanel();
            } else if ("Debit/Credit Card".equals(paymentMethod)) {
                showCardPaymentPanel();
            } else if ("QRIS".equals(paymentMethod)) {
                showQRCodePanel();
            }
        });

        // Action listener untuk tombol "Batal"
        cancelButton.addActionListener(e -> {
            dispose(); // Kembali ke menu utama
        });
    }

    private void showCashPaymentPanel() {
        JPanel cashPanel = new JPanel();
        cashPanel.setLayout(new GridLayout(2, 2));
        
        JTextField cashInputField = new JTextField();
        JTextField changeField = new JTextField();
        changeField.setEditable(false);

        cashPanel.add(new JLabel("Jumlah Cash:"));
        cashPanel.add(cashInputField);
        cashPanel.add(new JLabel("Kembalian:"));
        cashPanel.add(changeField);

        int totalPrice = Integer.parseInt(totalPriceField.getText());
        
        cashInputField.addActionListener(e -> {
            try {
                int cashGiven = Integer.parseInt(cashInputField.getText());
                if (cashGiven == totalPrice) {
                    changeField.setText("Uang Pas");
                } else if (cashGiven < totalPrice) {
                    changeField.setText("Kurang bang uangnya");
                } else {
                    int change = cashGiven - totalPrice;
                    changeField.setText("Kembalian: " + change);
                }
            } catch (NumberFormatException ex) {
                changeField.setText("Input tidak valid");
            }
        });

        JOptionPane.showMessageDialog(this, cashPanel, "Pembayaran Cash", JOptionPane.PLAIN_MESSAGE);
    }

    private void showCardPaymentPanel() {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(2, 2));
        
        JTextField cardNumberField = new JTextField();
        cardPanel.add(new JLabel("Nomor Kartu:"));
        cardPanel.add(cardNumberField);

        JOptionPane.showMessageDialog(this, cardPanel, "Pembayaran Debit/Credit Card", JOptionPane.PLAIN_MESSAGE);
    }

    private void showQRCodePanel() {
        JPanel qrPanel = new JPanel();
        qrPanel.setLayout(new GridLayout(2, 2));
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            JPanel box = new JPanel();
            box.setBackground(random.nextBoolean() ? Color.BLACK : Color.WHITE);
            qrPanel.add(box);
        }

        JOptionPane.showMessageDialog(this, qrPanel, "Pembayaran QRIS", JOptionPane.PLAIN_MESSAGE);
    }

    private void updateTotalPrice() {
        int currentTotal = 0;
        String[] lines = selectedProductArea.getText().split("\n");
        for (String line : lines) {
            if (!line.isEmpty()) {
                String[] parts = line.split(", ");
                int productPrice = Integer.parseInt(parts[2].split(": ")[1]);
                currentTotal += productPrice;
            }
        }

        // Apply discount if any
        String discountText = discountField.getText();
        if (!discountText.isEmpty()) {
            int discount = Integer.parseInt(discountText);
            currentTotal -= (currentTotal * discount / 100);
        }

        totalPriceField.setText(String.valueOf(currentTotal));
    }

    private void loadProducts(DefaultTableModel model) {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, price, stock FROM Products")) {
            
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");
                model.addRow(new Object[]{id, name, price, stock});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products from database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Transaksi transaksiUI = new Transaksi();
            transaksiUI.setVisible(true);
        });
    }
}