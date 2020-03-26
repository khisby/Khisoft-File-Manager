package gui;

import component.KhisoftButton;
import data.Multiselect;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khisby
 */
public class Main extends javax.swing.JFrame {
    JPanel jFile;
    ArrayList<KhisoftButton> tombol = new ArrayList<>();
    Integer indexFile;
    String path;
    ArrayList<Multiselect> multiselected = new ArrayList<>();
    Boolean copy;
    Boolean move;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        this.jFile = new JPanel();
        this.jFile.setLayout(null);
        this.jspFile.setViewportView(this.jFile);
        this.path = "";
        this.dirRoot();
        
        setPath();
        
        btnCopy.setEnabled(false);
        btnMove.setEnabled(false);
        btnMove.setEnabled(false);
        copy = false;
        move = false;
        
        this.btnUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String newPath = (new File(path).getParent());
                if(newPath == null){
                    dirRoot();
                }else{
                    path = newPath + "/";
                    setPath();
                    getDir();
                }
                
                if(copy == false && move == false){
                    taFileTerpilih.setText("");
                    for(KhisoftButton b : tombol){
                        b.setMultiSelected(false);
                        b.setBackground(null);
                    }
                    multiselected.clear();
                    copy = false;
                    move = false;
                    btnPaste.setEnabled(false);
                    btnCopy.setEnabled(false);
                    btnMove.setEnabled(false);
                    btnCancel.setEnabled(false);
                }
            }
        });
        
        tfCari.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(tfCari.getText().length() > 0){
                    cari();
                }else{
                    getDir();
                } 
            }
        });
        
        btnNewFolder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newFolder();
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        
        btnNewFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newFile();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        btnEditFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(indexFile > -1){
                    try {
                        editFile(path + tombol.get(indexFile).getActionCommand().toString());
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Pilih file terlebih dahulu");
                }
            }
        });
        
        btnShowText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(indexFile > -1){
                    try {
                        showText(path + tombol.get(indexFile).getActionCommand().toString());
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Pilih file terlebih dahulu");
                }
            }
        });
        
        btnShowDateTime.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(indexFile > -1){
                    showDateTime(path + tombol.get(indexFile).getActionCommand().toString());
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Pilih file terlebih dahulu");
                }
            }
        });
        
        btnRename.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(indexFile > -1){
                    try {
                        rename(path + tombol.get(indexFile).getActionCommand().toString(),tombol.get(indexFile).getActionCommand().toString());
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Pilih file terlebih dahulu");
                }
            }
        });
        
        btnProperties.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                properties();
            }
        });
        
        btnCopy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                copy = true;
                move = false;
                btnPaste.setEnabled(true);
                btnCopy.setEnabled(false);
                btnMove.setEnabled(false);
                btnCancel.setEnabled(true);
                
            }
        });
        
        btnMove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                copy = false;
                move = true;
                btnPaste.setEnabled(true);
                btnCopy.setEnabled(false);
                btnMove.setEnabled(false);
                btnCancel.setEnabled(true);
            }
        });
        
        btnPaste.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paste();
                    btnCancel.setEnabled(false);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taFileTerpilih.setText("");
                for(KhisoftButton b : tombol){
                    b.setMultiSelected(false);
                    b.setBackground(null);
                }
                multiselected.clear();
                copy = false;
                move = false;
                btnPaste.setEnabled(false);
                btnCopy.setEnabled(false);
                btnMove.setEnabled(false);
                btnCancel.setEnabled(false);
            }
        });
        
        btnBantuan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPane, "Untuk copy dan move pilih file dulu (bisa beberapa file) dengan menekan tahan CTRL dan klik pada file. \nKemudian pilih Copy atau Move selanjutnya buka direktori yang ingin dituju dan tekan pada Paste in here.");
            }
        });
        
        btnTentang.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String tentang = "Pembuat : Khisby" + "\n"
                        + "By : Khisoft \n"
                        + "Email : Khisby@gmail.com\n"
                        + "Kode Sumber Terbuka Aplikasi Ini: https://github.com/khisby/Khisoft-File-Manager\n"
                        + "Linkedin : https://www.linkedin.com/in/khisby/";
                JOptionPane.showMessageDialog(rootPane, tentang);
            }
        });
    }
    
    public void setPath(){
        if(this.path.equals("")){
            tfPath.setText("This PC");
        }else{
            tfPath.setText(path);
        }
    }
    
    public void RedrawPanel(){
        jspFile.setViewportView(null);
        jspFile.setViewportView(this.jFile);
        setPath();
        indexFile = -1;
    }
    
    public void dirRoot(){
        this.tombol.clear();
        this.path = "";
        File[] fileList = File.listRoots();
        int i = 0;
        this.jFile = new JPanel();
        if(fileList.length == 0){
            this.jFile.setLayout(null);
            JLabel lbKosong = new JLabel("Tidak ada item dalam folder");
            lbKosong.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            this.jFile.add(lbKosong);
            this.jFile.setBounds(10, 10, 150, 50);
        }else{
            this.jFile.setLayout(new GridLayout(fileList.length / 3, 3, 15, 15));
            for(File f : fileList){
                    KhisoftButton btn = new KhisoftButton(f.getPath());
                    btn.addMouseListener(new MouseListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(e.getClickCount() == 1){
                                for(KhisoftButton b : tombol){
                                    b.setBackground(null);
                                }
                                indexFile = btn.getIndex();
                                btn.setBackground(Color.BLUE);

                            }else if(e.getClickCount() == 2){
                                indexFile = -1;
                                path = path + btn.getActionCommand();
                                getDir();
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                    btn.setIndex(i);
                    this.tombol.add(btn);
                    this.jFile.add(btn);
                    i++;
                }
        }
        this.RedrawPanel();
    }
    
    public void getDir(){
        this.tombol.clear();
        File[] fileList = (new File(this.path)).listFiles();
        this.jFile = new JPanel();
        if(fileList.length == 0){
            this.jFile.setLayout(null);
            JLabel lbKosong = new JLabel("Tidak ada item dalam folder");
            lbKosong.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            lbKosong.setBounds(10, 10, 300, 50);
            this.jFile.add(lbKosong);
        }else{
            this.jFile.setLayout(new GridLayout(fileList.length / 3, 3, 15, 15));
            int i = 0;
            for (File f : fileList) {
                    KhisoftButton btn = new KhisoftButton(f.getName());
                    btn.addMouseListener(new MouseListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if((ActionEvent.CTRL_MASK & e.getModifiers()) != 0){
                                indexFile = -1;
                                btn.setMultiSelected(true);
                                for(KhisoftButton b : tombol){
                                    if(b.isMultiSelected() == true){
                                        b.setBackground(Color.CYAN);
                                    }else{
                                        b.setBackground(null);
                                    }
                                }
                                Multiselect m = new Multiselect();
                                m.setIndex(btn.getIndex());
                                m.setFileName(btn.getActionCommand().toString());
                                m.setFilePath(path + btn.getActionCommand().toString());
                                boolean sudahAda = false;
                                for(Multiselect mls : multiselected){
                                    if(mls.getIndex() == m.getIndex()){
                                        sudahAda = true;
                                    }
                                }
                                if(sudahAda == false){
                                    multiselected.add(m);
                                }
                                
                                taFileTerpilih.setText("");
                                for (int j = 0; j < multiselected.size(); j++) {
                                     taFileTerpilih.append(multiselected.get(j).getFileName() + "\n");
                                }
                                
                                btnCopy.setEnabled(true);
                                btnMove.setEnabled(true);
                            }else if(e.getClickCount() == 1){
                                for(KhisoftButton b : tombol){
                                    if(b.isMultiSelected() == true){
                                        b.setBackground(Color.CYAN);
                                    }else{
                                        b.setBackground(null);
                                    }
                                }
                                indexFile = btn.getIndex();
                                btn.setBackground(Color.BLUE);
                            }else if(e.getClickCount() == 2){
                                indexFile = -1;
                                File file = new File(path + btn.getActionCommand() + "/");
                                if(file.isDirectory()){
                                    path = path + btn.getActionCommand() + "/";
                                    getDir();
                                }else{
                                    Desktop desktop = Desktop.getDesktop();
                                    try {
                                        desktop.open(file);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } 
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });;
                    btn.setIndex(i);
                    this.tombol.add(btn);
                    this.jFile.add(btn);
                    i++;
            }
        }
        this.RedrawPanel();
    }
    
    public void cari(){
        this.tombol.clear();
        File[] fileList = (new File(this.path)).listFiles();
        this.jFile = new JPanel();
        this.jFile.setLayout(new GridLayout(fileList.length / 3, 3, 15, 15));
        int i = 0;
        
        boolean ada = false; 
        for (File f : fileList) {
                if(f.getName().toLowerCase().contains(tfCari.getText().toLowerCase().toString())){
                    ada = true;
                    KhisoftButton btn = new KhisoftButton(f.getName());
                    btn.addMouseListener(new MouseListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(e.getClickCount() == 1){
                                for(KhisoftButton b : tombol){
                                    b.setBackground(null);
                                }
                                indexFile = btn.getIndex();
                                btn.setBackground(Color.BLUE);
                            }else if(e.getClickCount() == 2){
                                indexFile = -1;
                                File file = new File(path + btn.getActionCommand() + "/");
                                if(file.isDirectory()){
                                    path = path + btn.getActionCommand() + "/";
                                    getDir();
                                }else{
                                    Desktop desktop = Desktop.getDesktop();
                                    try {
                                        desktop.open(file);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });;
                    btn.setIndex(i);
                    this.tombol.add(btn);
                    this.jFile.add(btn);
                    i++;
                }
        }
        
        if(ada == false){
            this.jFile.setLayout(null);
            JLabel lbKosong = new JLabel("Tidak ada item dalam folder");
            lbKosong.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            lbKosong.setBounds(10, 10, 300, 50);
            this.jFile.add(lbKosong);
        }
        this.RedrawPanel();
    }
    
    public void newFolder(){
        String folder = JOptionPane.showInputDialog("Masukkan nama folder : ");
        if(folder != null){
            String folderBaru  = this.path + folder + "/";
            File file = new File(folderBaru);
            if(file.mkdir()){
                if(JOptionPane.showConfirmDialog(rootPane, "Berhasil membuat folder. Ingin masuk folder?", "Berhasil", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                    this.path = folderBaru;
                    getDir();
                } else {
                    getDir();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "Gagal membuat folder baru. Folder Sudah ada");
            }
        }
    }
    
    public void delete(){
        if(this.indexFile > -1){
            String hapus = this.path + this.tombol.get(this.indexFile).getActionCommand();
            File file = new File(hapus);
            if(JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin ingin menghapus \""+ this.tombol.get(this.indexFile).getActionCommand() + "\" ", "Hapus", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                if(file.delete()){
                    JOptionPane.showMessageDialog(rootPane, "Berhasil menghapus");
                    getDir();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Gagal menghapus");
                    getDir();
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilih file terlebih dahulu");
        }
    }
    
    public void newFile() throws IOException{
        String folder = JOptionPane.showInputDialog("Masukkan nama file : ");
        if(folder != null){
            String folderBaru  = this.path + folder + ".txt";
            File file = new File(folderBaru);
            if(file.createNewFile()){
                if(JOptionPane.showConfirmDialog(rootPane, "Berhasil membuat file. Ingin menulis filenya?", "Berhasil", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                    editFile(folderBaru);
                    getDir();
                } else {
                    getDir();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "Gagal membuat file. file Sudah ada");
            }
        }
    }
    
    public void editFile(String file) throws IOException{
        File f = new File(file);
        if(f.isFile()){
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String oldText = br.readLine();
            String folder = JOptionPane.showInputDialog("Masukkan teks isi file : ",oldText);
            if(folder != null){
                FileWriter writer = new FileWriter(file);
                writer.write(folder);
                writer.close();
                getDir();
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilihan bukan sebuah File");
        }
    }
    
    public void showText(String file) throws FileNotFoundException, IOException{
        File f = new File(file);
        if(f.isFile()){
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String oldText = br.readLine();
            JOptionPane.showMessageDialog(rootPane, oldText, "Lihat isi file", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilihan bukan sebuah File");
        }
    }
    
    public void showDateTime(String file){
        File f = new File(file);
        if(f.isFile()){
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
            JOptionPane.showMessageDialog(rootPane, "Last Modified Date: " + sdf.format(f.lastModified()), "Lihat isi file", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilihan bukan sebuah File");
        }
    }
    
    public void rename(String file, String nameFile) throws IOException{
        File f = new File(file);
        String folder = JOptionPane.showInputDialog("Ubah menjadi : ", nameFile);
        if(folder != null){
            f.renameTo(new File(path + folder + "/"));
            JOptionPane.showMessageDialog(rootPane, "Berhasil mengubah nama file menjadi " + folder);
            getDir();
        }
    }
    
    public void properties(){
        if(this.indexFile > -1){
            String properties = this.path + this.tombol.get(this.indexFile).getActionCommand();
            File file = new File(properties);
            String p = "Ukuran file : " + file.length() + " Bytes\n"
                    + "Sebuah Folder : " + file.isDirectory() + "\n"
                    + "Sebuah File : " + file.isFile() + "\n"
                    + "Tersembunyi : " + file.isHidden() + "\n"
                    + "Perizinan (Baca, Tulis, Dijalankan) : " + file.canRead() + "," + file.canWrite() + "," + file.canExecute();
            JOptionPane.showMessageDialog(rootPane, p);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilih file terlebih dahulu");
        }
    }
    
    public void paste() throws IOException{
        if(copy == false && move == true){
            for(Multiselect m : multiselected){
                File f = new File(m.getFilePath());
                f.renameTo(new File(path + m.getFileName()));
                f.delete();
            }
            getDir();
        }else if(copy == true && move == false){
            for(Multiselect m : multiselected){
                Files.copy((new File(m.getFilePath())).toPath(), (new File(this.path + m.getFileName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            getDir();
        }
        
        taFileTerpilih.setText("");
        multiselected.clear();
        copy = false;
        move = false;
        btnPaste.setEnabled(false);
        btnCopy.setEnabled(false);
        btnMove.setEnabled(false);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspFile = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        tfPath = new javax.swing.JTextField();
        tfCari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnRename = new javax.swing.JButton();
        btnEditFile = new javax.swing.JButton();
        btnShowDateTime = new javax.swing.JButton();
        btnProperties = new javax.swing.JButton();
        btnNewFile = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        btnNewFolder = new javax.swing.JButton();
        btnShowText = new javax.swing.JButton();
        btnMove = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        btnPaste = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taFileTerpilih = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnTentang = new javax.swing.JButton();
        btnBantuan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Khisoft File Manager");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Khisoft File Manager © Khisoft 2020");

        jLabel2.setText("Pencarian");

        btnRename.setText("Rename");

        btnEditFile.setText("Edit File");

        btnShowDateTime.setText("Show Date-Time");

        btnProperties.setText("Properties");

        btnNewFile.setText("New File");

        btnDelete.setText("Delete");

        btnUp.setText("Up");

        btnNewFolder.setText("New Folder");

        btnShowText.setText("Show Text");

        btnMove.setText("Move");

        btnCopy.setText("Copy");

        btnPaste.setText("Paste in here");
        btnPaste.setEnabled(false);

        taFileTerpilih.setColumns(20);
        taFileTerpilih.setRows(5);
        jScrollPane1.setViewportView(taFileTerpilih);

        jLabel3.setText("File terpilih : ");

        btnCancel.setText("Cancel Selected");

        btnTentang.setText("Tentang");

        btnBantuan.setText("Bantuan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfPath, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUp))
                    .addComponent(jspFile, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnDelete)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEditFile)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                        .addComponent(btnShowDateTime))
                                    .addComponent(tfCari)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnNewFolder)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnNewFile)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnProperties))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnCopy)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnMove)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnPaste))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnShowText)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnRename)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCancel))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(btnBantuan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTentang)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfPath, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUp))
                        .addGap(18, 18, 18)
                        .addComponent(jspFile))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewFolder)
                            .addComponent(btnNewFile)
                            .addComponent(btnProperties))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete)
                            .addComponent(btnEditFile)
                            .addComponent(btnShowDateTime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnShowText)
                            .addComponent(btnRename))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCopy)
                            .addComponent(btnMove)
                            .addComponent(btnPaste))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnCancel))
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTentang)
                            .addComponent(btnBantuan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBantuan;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditFile;
    private javax.swing.JButton btnMove;
    private javax.swing.JButton btnNewFile;
    private javax.swing.JButton btnNewFolder;
    private javax.swing.JButton btnPaste;
    private javax.swing.JButton btnProperties;
    private javax.swing.JButton btnRename;
    private javax.swing.JButton btnShowDateTime;
    private javax.swing.JButton btnShowText;
    private javax.swing.JButton btnTentang;
    private javax.swing.JButton btnUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jspFile;
    private javax.swing.JTextArea taFileTerpilih;
    private javax.swing.JTextField tfCari;
    private javax.swing.JTextField tfPath;
    // End of variables declaration//GEN-END:variables
}
