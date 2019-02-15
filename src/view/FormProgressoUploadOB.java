/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DesconciliacaoOBDAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import entidade.DesconciliacaoOB;
import entidade.Usuario;
import rotinasWeb.LeitorXlsxBaseWeb;
import util.Utils;

/**
 *
 * @author suporte
 */
public class FormProgressoUploadOB extends javax.swing.JFrame {

   
    
    /**
     * Creates new form FormSolicitacaoTarifa
     * 
     */
    
    
    public FormProgressoUploadOB() {
        setPropriedades();
        initComponents();

        URL caminhoIcone = getClass().getResource("/imagens/banco do brasil.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

    }

    public void setPropriedades() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        txtArea = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblTitulo, txtArea});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnIniciar)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblTitulo, txtArea});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        Usuario user = new Usuario();
        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário autenticado!!");
            System.exit(0);
        }

        if (!user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            JOptionPane.showMessageDialog(null, "Usuário sem Perfil de acesso para esta rotina!!");
            return;
        }

        this.setTitle("ATUALIZAÇÃO BASE WEB OUTROS BANCOS EM EXECUÇÃO");

        Thread thread = new Thread() {

            public void run() {//double a, String b, int c, Date k
                FormProgressoUploadOB bar = new FormProgressoUploadOB();
//                LeitorXlsxBaseWeb leitor = new LeitorXlsxBaseWeb();
                JFileChooser arquivo = Utils.getFileChooser();
                int returnVal = arquivo.showOpenDialog(arquivo);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Utils.setLastDir(arquivo.getSelectedFile());
                    String arquivoSelecionado = arquivo.getSelectedFile().getAbsolutePath();

                    try {

                        lblTitulo.setText("Efetuando atualização na base da ferramenta Web OUTROS BANCOS   \n  Início: "  + Utils.getDataHoraAtualMysql());
                        btnIniciar.setEnabled(false);
                       // leitor.lerXlsx(arquivoSelecionado);
                        lerXlsx(arquivoSelecionado);
                        
                        btnIniciar.setEnabled(true);
                        bar.setVisible(false);
                       
                        JOptionPane.showMessageDialog(null, "Fim da atualizaçao:" + Utils.getDataHoraAtualMysql());
                        System.exit(0);
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo XLSX- reinicie o procedimento");
                         System.exit(0);
                    }

                }
            }

            
        };

        thread.start();
    

    }//GEN-LAST:event_btnIniciarActionPerformed

     public void lerXlsx(String arq) throws SQLException, Exception {

        DesconciliacaoOBDAO desconciliacaoOBDAO = new DesconciliacaoOBDAO();
        desconciliacaoOBDAO.apagaRegistrosTabela();

        //String nomeArquivo = "C:\\Users\\suporte\\Desktop\\Pasta1.xlsx";
        File file = new File(arq);

        try {
            FileInputStream fisPlanilha = new FileInputStream(file);
            //  cria um workbook , planilha com todas as abas
            XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
            // recuperar apenas a primeira aba
            XSSFSheet sheet = workbook.getSheetAt(0);
            //retorna todas as linhas da planilha 0
            Iterator<Row> rowIterator = sheet.iterator();
            int primeiroRegistro = sheet.getFirstRowNum();
            int ultimoRegistro = sheet.getLastRowNum();
            int qtdReg = ultimoRegistro - primeiroRegistro;

            
                int k = JOptionPane.showConfirmDialog(null, + qtdReg + " registros para efetuar a  carga, é isso mesmo? \n lembre-se de que este número deve ser idêntico ao da lista de desconciliação recebida" ,
                        "Atualizar base web Outros bancos", JOptionPane.YES_NO_OPTION);

                if (k == JOptionPane.NO_OPTION) {

                    return;
                }

            

            //varre todas as linhas da planila 
            int i = 0;
            while (rowIterator.hasNext()) {
               
                

                int numeroLinha = i;
                System.out.println("numero da linha : " + numeroLinha);
                txtArea.setText("numero da linha sendo carregada : " + numeroLinha + " de " + qtdReg);
                // recebe cada linha da planilha
                Row row = rowIterator.next();
                //pega todas as celulas da linha   
                Iterator<Cell> cellIterator = row.iterator();
                DesconciliacaoOB desconciliacaoOB = new DesconciliacaoOB();
                //varremos todas as celulas da linha atual
                int j = 1;
                while (cellIterator.hasNext()) {

                    //criamos uma celula
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {

                        case Cell.CELL_TYPE_STRING:
                            System.out.println("tipo String " + cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:

                            System.out.println("tipo numerico " + cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            System.out.println("Tippo formula" + cell.getCellFormula());
                            
                    }
                    if (numeroLinha > 0) {
                        switch (j) {
                            case 1:
                                desconciliacaoOB.setNpj(Utils.tratarNpj(cell.getNumericCellValue()));
                                break;

                            case 2:
                                desconciliacaoOB.setVariacaoNpj((int) cell.getNumericCellValue());
                                break;

                            case 3:
                                desconciliacaoOB.setBancoDepositario(cell.getStringCellValue());
                                break;
                            case 4:
                                desconciliacaoOB.setContaDepositaria(cell.getStringCellValue());
                                break;
                            case 5:
                                desconciliacaoOB.setValorDesconciliacao(cell.getNumericCellValue());
                                break;

                            case 6:

                                desconciliacaoOB.setDataDesconciliacao((Utils.getDataDesconciliacaoFormatoMysql(cell.getDateCellValue())));
                                break;

                        }
                    }

                    j++;

                }
                i++;
                if (numeroLinha > 0) {
                    desconciliacaoOBDAO.inserirRegistroNovoTbTemporaria(desconciliacaoOB);
                }
            }
            
            
            txtArea.setText("Efetuando atualização diaria  em Banco de Dados..aguarde!!");
            desconciliacaoOBDAO.atualizacaoDiaria();
//            txtArea.setText("Efetuando atualização do histórico de tratamento e gerando volumetria  dos tratamentos automáticos..aguarde!!");
//            desconciliacaoOBDAO.buscarRegistroTratatoAutomatico();

        } catch (FileNotFoundException ex) {
         
            JOptionPane.showMessageDialog(null, "Erro - reinicie o procedimento" + ex);
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro - reinicie o procedimento" + ex);
            System.exit(0);
        }
    }

    
    
    
    
    
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    
        System.exit(0);

    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormProgressoUploadOB().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel txtArea;
    // End of variables declaration//GEN-END:variables
}
