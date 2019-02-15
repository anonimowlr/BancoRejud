
package view;

import dao.DesconciliacaoDAO;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import entidade.Desconciliacao;
import rotinasWeb.LeitorXlsxBaseWeb;
import util.Autenticar;
import util.Utils;

/**
 *
 * @author suporte
 */
public class FormProgresso extends javax.swing.JFrame {

   
    
    /**
     * Creates new form FormSolicitacaoTarifa
     * 
     */
    
    
    public FormProgresso() {
        setPropriedades();
        initComponents();

        URL caminhoIcone = getClass().getResource("/imagens/banco do brasil.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

    }

    private void setPropriedades() {

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
        txtAreaRetab = new javax.swing.JLabel();

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(220, 220, 220))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                            .addComponent(txtArea, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                            .addComponent(txtAreaRetab, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblTitulo, txtArea});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAreaRetab, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnIniciar)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblTitulo, txtArea});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        
        DesconciliacaoDAO desconciliacaoDAO = new DesconciliacaoDAO("rejud");
        Autenticar a = new Autenticar();
        
        a.autenticaUser();
                
        this.setTitle("Lista Base Web DJO");

        Thread thread = new Thread() {
            @Override
            public void run() {//double a, String b, int c, Date k

                FormProgresso bar = new FormProgresso();
                
                String bancoDados = "rejud";
                String arqSelecRejud = null;
                int qtdRegRejud = 0;

                JOptionPane.showMessageDialog(null, "Selecione o arquivo .xlsx referente ao REJUD");
                JFileChooser arquivoRejud = Utils.getFileChooser();
                int returnValRejud = arquivoRejud.showOpenDialog(arquivoRejud);

                if (returnValRejud == JFileChooser.APPROVE_OPTION) { // || returnValRetab == JFileChooser.APPROVE_OPTION) {

                    try {

                        lblTitulo.setText("Efetuando carga do arquivo - Início: " + Utils.getDataHoraAtualMysql());
                        
                        txtArea.setText("Aguarde...");
                        btnIniciar.setEnabled(false);

                        LeitorXlsxBaseWeb leitor = new LeitorXlsxBaseWeb(bancoDados);

                        arqSelecRejud = arquivoRejud.getSelectedFile().getAbsolutePath();
                        qtdRegRejud = leitor.contarRegistros(arqSelecRejud); //qtd linhas do arquivo xlsx
                        txtArea.setText(qtdRegRejud + " registros REJUD a ser carregado, aguarde...");

                        int k = JOptionPane.showConfirmDialog(null, "Registros do REJUD: " + qtdRegRejud + "\n\n"
                                + "Lembre-se de que este número deve ser idêntico ao da lista de desconciliação do Portal Jurídico\n\n"
                                + "Confima carga dos resgistros?",
                                "Leitura dos arquivos desconciliação Portal Jurídico", JOptionPane.YES_NO_OPTION);
   
                        if (k == JOptionPane.NO_OPTION) {
                            bar.dispose();
                        }
                              
                        
                        desconciliacaoDAO.apagaRegistrosTabela("tb_temporaria_desconciliacao_djo_paj");


                        if (returnValRejud == JFileChooser.APPROVE_OPTION) {                        
                            procedimentoRejud(leitor, arqSelecRejud, qtdRegRejud); 
                        }                 

                        btnIniciar.setEnabled(true);
                        bar.setVisible(false);

                        JOptionPane.showMessageDialog(null, "Fim da leitura dos arquivos: " + Utils.getDataHoraAtualMysql());
                        System.exit(0);

                    } catch (HeadlessException ex) {
                        JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo XLSX- reinicie o procedimento");
                        System.exit(0);
                    }


                }
                
            }

            private void procedimentoRejud(LeitorXlsxBaseWeb leitor, String arqSelecRejud, int qtdRegRejud) {
                
                txtArea.setText("Efetuando carga de " + qtdRegRejud + " registros do REJUD. Aguarde...");

                leitor.lerXlsx(arqSelecRejud, txtArea);

                List<Desconciliacao> descREJUD = desconciliacaoDAO.consultaTbTemp("tb_temporaria_desconciliacao_djo_paj");
                int qtdRejud = descREJUD.size(); // qtd registros gravados no BD

                if (qtdRegRejud == qtdRejud) {
                    txtArea.setText(qtdRejud + " registros gravados com sucesso de " + qtdRegRejud);
                } else {
                    int cr = JOptionPane.showConfirmDialog(null, "A quantidade de registro do arquivo REJUD (" + qtdRegRejud + ") não confere com a gravada no Bando de Dados (" + qtdRejud + ").\n\n"
                            + "Continuar com a carga do RETAB?");

                    if (cr == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            }

        };

        thread.start();
    
    }//GEN-LAST:event_btnIniciarActionPerformed


//    public void lerXlsx(String arq, int prefixo) throws SQLException, Exception {
//
//        DesconciliacaoDAO desconciliacaoDAO = new DesconciliacaoDAO();
//        
//        desconciliacaoDAO.apagaRegistrosTabela("tb_temporaria_desconciliacao_djo_paj");
//
//        File file = new File(arq);
//
//        try {
//            FileInputStream fisPlanilha = new FileInputStream(file);
//            //  cria um workbook , planilha com todas as abas
//            XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
//            // recuperar apenas a primeira aba
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            //retorna todas as linhas da planilha 0
//            Iterator<Row> rowIterator = sheet.iterator();
//            int primeiroRegistro = sheet.getFirstRowNum();
//            int ultimoRegistro = sheet.getLastRowNum();
//            int qtdReg = ultimoRegistro - primeiroRegistro;
//
//            int k = JOptionPane.showConfirmDialog(null, +qtdReg + " registros para efetuar a  carga, é isso mesmo? \n lembre-se de que este número deve ser idêntico ao da lista de desconciliação do Portal Jurídico",
//                    "Atualizar base web DJO", JOptionPane.YES_NO_OPTION);
//
//            if (k == JOptionPane.NO_OPTION) {
//
//                return;
//            }
//
//            //varre todas as linhas da planila 
//            int i = 0;
//            while (rowIterator.hasNext()) {
//
//                int numeroLinha = i;
//                System.out.println("numero da linha : " + numeroLinha);
//                txtArea.setText("numero da linha sendo carregada : " + numeroLinha + " de " + qtdReg);
//                // recebe cada linha da planilha
//                Row row = rowIterator.next();
//                //pega todas as celulas da linha   
//                Iterator<Cell> cellIterator = row.iterator();
//                Desconciliacao desconciliacao = new Desconciliacao();
//                
//                //varremos todas as celulas da linha atual
//                int j = 1;
//                while (cellIterator.hasNext()) {
//
//                //criamos uma celula
//                Cell cell = cellIterator.next();
//
//                    if (numeroLinha > 0) {
//                        
//                        
//                        new LeitorXlsxBaseWeb().switchRejud(desconciliacao, cell, j);                        
//
//                    
//                        
//                    }
//                    j++;
//                }
//                i++;
//                
//                desconciliacao.setPrefixoOrigem(prefixo); //desconciliações do REJUD
//                
//                if (numeroLinha > 0) {
//                    desconciliacaoDAO.inserirRegistroNovoTbTemporaria(desconciliacao, "tb_temporaria_desconciliacao_djo_paj");
//                }
//                
//            }            
//            
//            txtArea.setText("Efetuando atualização diaria  em Banco de Dados..aguarde!!");
//            desconciliacaoDAO.atualizacaoDiaria();
//            txtArea.setText("Efetuando atualização do histórico de tratamento e gerando volumetria  dos tratamentos automáticos..aguarde!!");
//            desconciliacaoDAO.buscarRegistroTratatoAutomatico();
//
//        } catch (FileNotFoundException ex) {
//         
//            JOptionPane.showMessageDialog(null, "Erro - reinicie o procedimento" + ex);
//            System.exit(0);
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Erro - reinicie o procedimento" + ex);
//            System.exit(0);
//        }
//        
//    }

    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    
        System.exit(0);

    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("Convert2Lambda")
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormProgresso().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel txtArea;
    private javax.swing.JLabel txtAreaRetab;
    // End of variables declaration//GEN-END:variables
 
   
}
