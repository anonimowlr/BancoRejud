
package view;

import rotinasPortal.AcessoOutrosBancos;
import rotinasPortal.Coletas;
import rotinasPortal.CriarSolicitacaoLevPortal;
import rotinasPortal.EfetivarLevantamento;
import dao.DAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.Utils;
import entidade.SolicitacaoLevantamento;
import entidade.Usuario;

public class frmSolicitacaoLevantamento extends javax.swing.JFrame {

    Usuario user = new Usuario();

    public frmSolicitacaoLevantamento() {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblUsuarioAtual = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtEnderecoDocumento1 = new javax.swing.JPanel();
        txtDocumento1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNumeroOficio = new javax.swing.JTextField();
        txtNPJ = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtVariacao = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDocumento2 = new javax.swing.JTextField();
        btnIncluir1 = new javax.swing.JButton();
        btnIncluir2 = new javax.swing.JButton();
        txtTipoLevantamento = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtSaldoContabilPortal = new javax.swing.JTextField();
        txtLevantador = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtFinalidade = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtEspecificacao = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtFavorecido = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtIncluir = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtJustificativa = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        txtValorSolicitacao = new javax.swing.JTextField();
        txtDataOficio = new javax.swing.JFormattedTextField();
        btnSalvar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Solicitação de Levantamento");

        jLabel18.setText("Usuário Atual:");

        lblUsuarioAtual.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(lblUsuarioAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuarioAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        txtEnderecoDocumento1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Cliente"));

        txtDocumento1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel2.setText("Número NPJ:");

        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Levantador:");

        jLabel6.setText("Tipo de Levantamento:");

        jLabel4.setText("Valor da Solicitação");

        txtNumeroOficio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNumeroOficio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroOficioActionPerformed(evt);
            }
        });

        txtNPJ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNPJActionPerformed(evt);
            }
        });
        txtNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNPJKeyTyped(evt);
            }
        });

        jLabel5.setText("Data do Ofício:");

        jLabel3.setText("Número do ofício: ");

        jLabel8.setText("Variação do NPJ");

        txtVariacao.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtVariacao.setModel(new javax.swing.SpinnerNumberModel(0, 0, 16, 1));

        jLabel9.setText("Endereço Documento 1:");

        jLabel10.setText("Endereço Documento 2:");

        txtDocumento2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        btnIncluir1.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        btnIncluir1.setText("Incluir");
        btnIncluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluir1ActionPerformed(evt);
            }
        });

        btnIncluir2.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        btnIncluir2.setText("Incluir");
        btnIncluir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluir2ActionPerformed(evt);
            }
        });

        txtTipoLevantamento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTipoLevantamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Total", "Parcial" }));
        txtTipoLevantamento.setSelectedIndex(2);
        txtTipoLevantamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoLevantamentoActionPerformed(evt);
            }
        });

        jLabel11.setText("Saldo Contábil Portal:");

        txtSaldoContabilPortal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSaldoContabilPortal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoContabilPortalActionPerformed(evt);
            }
        });

        txtLevantador.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtLevantador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Banco", "Adverso ou Terceiro" }));
        txtLevantador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLevantadorActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("Finalidade:");

        txtFinalidade.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFinalidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Levantamento em Favor do Banco", "Condenacoes e Acordos", "Custas de Condução" }));
        txtFinalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFinalidadeActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(102, 153, 255));
        jLabel13.setText("Especificação:");

        txtEspecificacao.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtEspecificacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Ajuste contabil ou Novo Deposito", "Excesso de Execução ou Ganho de Causa BB", "Custas Processuais Sucumbenciais", "Dano Moral, Dano Material e Repeticao de Indebito", "Diferenca de Rendimentos - Planos - Poupanca", "Honorarios Advocaticios Sucumbenciais", "Lucros Cessantes e Juros", "Honorarios Periciais em Juizo", "Tributos devidos pelo Banco Multiplo" }));
        txtEspecificacao.setToolTipText("");
        txtEspecificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEspecificacaoActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Favorecido:");

        txtFavorecido.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel15.setForeground(new java.awt.Color(0, 153, 255));
        jLabel15.setText("Cpf/Cnpj do Favorecido:");

        txtCPF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("Incluir Beneficiário?");

        txtIncluir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtIncluir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Sim" }));
        txtIncluir.setSelectedIndex(1);

        txtJustificativa.setColumns(20);
        txtJustificativa.setRows(5);
        jScrollPane1.setViewportView(txtJustificativa);

        jLabel17.setForeground(new java.awt.Color(51, 153, 255));
        jLabel17.setText("Justificativa:");

        txtValorSolicitacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorSolicitacaoActionPerformed(evt);
            }
        });
        txtValorSolicitacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorSolicitacaoKeyTyped(evt);
            }
        });

        try {
            txtDataOficio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataOficio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataOficioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout txtEnderecoDocumento1Layout = new javax.swing.GroupLayout(txtEnderecoDocumento1);
        txtEnderecoDocumento1.setLayout(txtEnderecoDocumento1Layout);
        txtEnderecoDocumento1Layout.setHorizontalGroup(
            txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(65, 65, 65))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtEnderecoDocumento1Layout.createSequentialGroup()
                                    .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel12))
                                    .addGap(74, 74, 74)))
                            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, Short.MAX_VALUE))))
                    .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addGap(22, 22, 22)))
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                                .addComponent(txtNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(jLabel8)
                                .addGap(21, 21, 21)
                                .addComponent(txtVariacao, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNumeroOficio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataOficio, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDocumento2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoLevantamento, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSaldoContabilPortal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLevantador, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFinalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEspecificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnIncluir1)
                            .addComponent(btnIncluir2)))
                    .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                                .addComponent(txtFavorecido, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel15))
                            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txtEnderecoDocumento1Layout.setVerticalGroup(
            txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtVariacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNumeroOficio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtDataOficio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel10))
                    .addGroup(txtEnderecoDocumento1Layout.createSequentialGroup()
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIncluir1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDocumento2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIncluir2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtValorSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTipoLevantamento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSaldoContabilPortal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtLevantador, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtFinalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtEspecificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFavorecido, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addGap(17, 17, 17)
                .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtEnderecoDocumento1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98))
        );

        txtEnderecoDocumento1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel16, jLabel17, txtCPF, txtDataOficio, txtDocumento1, txtDocumento2, txtFavorecido, txtIncluir, txtNPJ, txtNumeroOficio, txtSaldoContabilPortal, txtValorSolicitacao});

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jButton2.setText("Limpar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setText("CEF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Banrisul");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Minhas Destinações");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Contabilizar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Instruções");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Incluir Portal");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtEnderecoDocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtEnderecoDocumento1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(13, 13, 13)
                        .addComponent(jButton1)
                        .addGap(11, 11, 11)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtEnderecoDocumento1.getAccessibleContext().setAccessibleName("");
        txtEnderecoDocumento1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void limpar() {
        
        txtNPJ.setText(null);
        txtVariacao.setValue(0);
        txtNumeroOficio.setText(null);
        txtDocumento1.setText(null);
        txtDocumento2.setText(null);
        txtTipoLevantamento.setSelectedIndex(0);
        txtSaldoContabilPortal.setText(null);
        txtLevantador.setSelectedIndex(0);
        txtEspecificacao.setSelectedIndex(0);
        txtFinalidade.setSelectedIndex(0);
        txtJustificativa.setText(null);
        txtFavorecido.setText(null);
        txtCPF.setText(null);
        txtIncluir.setSelectedIndex(0);

        txtValorSolicitacao.setText(null);
        txtDataOficio.setText(null);

        //jTextField6.setText(null);
    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
       
       Coletas coletas = new Coletas();
       
            if(user.getMatricula()==null){
                JOptionPane.showMessageDialog(null, "Sem usuário logado, nao será possível gravar as solicitações de levantamento");
                return;
            }
       
        
        if (txtNPJ.getText().equals("") ||  txtVariacao.getValue()==null || 
                txtDataOficio.getText().equals("")|| txtNumeroOficio.getText().equals("")|| 
                txtSaldoContabilPortal.getText().equals("")|| txtValorSolicitacao.getText().equals("")|| 
                txtDocumento1.getText().equals("")|| txtJustificativa.getText().equals("") || 
                txtTipoLevantamento.getSelectedItem().equals("Selecione")){
            
            JOptionPane.showMessageDialog(this, "Algum campo de preenchimento obrigatório está em branco");
            return;
            
        }
        
        DAO<SolicitacaoLevantamento> d = new DAO<>("rejud");        
        SolicitacaoLevantamento s;
        int i = JOptionPane.showConfirmDialog(null, "Deseja Salvar o pedido de solicitação de levantamento?",
                "Solicitação de levantamento", JOptionPane.YES_NO_OPTION);

        if (i == JOptionPane.NO_OPTION) {

            return;
        }

        try {
            String npj = txtNPJ.getText();
            int variacaoNpj = (int) txtVariacao.getValue();
            String historico = txtJustificativa.getText().trim();
            String oficio = txtNumeroOficio.getText().trim();
            String data = txtDataOficio.getText().trim();
            String valorSolicitacao = txtValorSolicitacao.getText().trim();
            String documento1 = txtDocumento1.getText().trim();
            String saldo = txtSaldoContabilPortal.getText().trim();
            String enderecoDocumento2 = txtDocumento2.getText().trim();
            String levantador = (String) txtLevantador.getSelectedItem();
            String tipoLevantamento = (String) txtTipoLevantamento.getSelectedItem();
            String finalidade = (String) txtFinalidade.getSelectedItem();
            String especificacao = (String) txtEspecificacao.getSelectedItem();
            String Beneficiario = txtFavorecido.getText().trim().toUpperCase();
            String cpfbeneficiario = txtCPF.getText().trim();
            String incluirParte = (String) txtIncluir.getSelectedItem();

            int tamanhoNpj = npj.length();

            if (tamanhoNpj < 11) {
                JOptionPane.showMessageDialog(this, "NPJ deve ter obrigatoriamente 11 posições numéricas");
                return;

            }

            BigDecimal saldoTratado = coletas.tratarNumero(saldo);
            BigDecimal valorSolicitacaoTratado = coletas.tratarNumero(valorSolicitacao);

            if (valorSolicitacaoTratado.compareTo(saldoTratado) > 0 || saldoTratado.equals(valorSolicitacaoTratado)) {
                tipoLevantamento = "Total";
            }

            BigDecimal div = valorSolicitacaoTratado.divide(saldoTratado, MathContext.DECIMAL32);

            if (div.compareTo(BigDecimal.valueOf(1.3)) > 0) {

                JOptionPane.showMessageDialog(null, "Valor do levantamento supera o valor do saldo contábil, necessário ajuste antes da inclusão!!");
                return;
            }

            s = new SolicitacaoLevantamento(npj, variacaoNpj, oficio, data, historico, valorSolicitacao, documento1, saldo, enderecoDocumento2, levantador, tipoLevantamento, finalidade, especificacao, Beneficiario, cpfbeneficiario, incluirParte);
            d.inserir(s);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this, ex);
            return;

        }

        i = JOptionPane.showConfirmDialog(null, "Dados gravados com sucesso!!..." + "\n" + "Deseja manter a  tela com os dados preenchidos para uma proxima inclusão?",
                "Manter dados preenchidos", JOptionPane.YES_NO_OPTION);

        if (i == JOptionPane.NO_OPTION) {

            limpar();
        }

  
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       limpar();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
              
                //String pasta = System.getProperty("user.dir");  
               
               // JOptionPane.showMessageDialog(null, usuarioRede + " ," + pasta);
                lblUsuarioAtual.setText(user.getMatricula());
               


    }//GEN-LAST:event_formWindowOpened
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void txtFinalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFinalidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFinalidadeActionPerformed

    private void txtLevantadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLevantadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLevantadorActionPerformed

    private void txtSaldoContabilPortalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoContabilPortalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoContabilPortalActionPerformed

    private void btnIncluir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluir2ActionPerformed

        JFileChooser arquivo = Utils.getFileChooser();
        int returnVal = arquivo.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Utils.setLastDir(arquivo.getSelectedFile());
            String arquivoSelecionado = arquivo.getSelectedFile().getAbsolutePath();
            File f = new File(arquivoSelecionado);
            long tamanho = (f.length() / 1024) / 1024;

            String discoArquivo = arquivoSelecionado.subSequence(0, 1).toString();
            int comprimento = arquivoSelecionado.length();

            if (comprimento > 99) {
                JOptionPane.showMessageDialog(null, "Nome do arquivo com mais de 99 caracteres");
                txtDocumento1.setText("");
                return;
            }

            if (!((discoArquivo.equals("G")) || (discoArquivo.equals("P")))) {
                JOptionPane.showMessageDialog(null, "Documento nao está na rede,não será possível prosseguir!!");
                txtDocumento2.setText("");
                return;
            }

            if (tamanho >= 5) {
                JOptionPane.showMessageDialog(this, "Tamanho máximo de documento para inclusão no Portal jurídico é de 5 MB, este  arquivo tem " + tamanho + " Mega Byte");
                return;
            }

            txtDocumento2.setText(arquivoSelecionado);

    }//GEN-LAST:event_btnIncluir2ActionPerformed
    }
    
    private void btnIncluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluir1ActionPerformed

        JFileChooser arquivo = Utils.getFileChooser();
        int returnVal = arquivo.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Utils.setLastDir(arquivo.getSelectedFile());
            String arquivoSelecionado = arquivo.getSelectedFile().getAbsolutePath();
            File f = new File(arquivoSelecionado);
            long tamanho = (f.length() / 1024) / 1024;

            String discoArquivo = arquivoSelecionado.subSequence(0, 1).toString();
            int comprimento = arquivoSelecionado.length();

            if (comprimento > 99) {
                JOptionPane.showMessageDialog(null, "Nome do arquivo com mais de 99 caracteres");
                txtDocumento1.setText("");
                return;
            }

            if (!((discoArquivo.equals("G")) || (discoArquivo.equals("P")))) {
                JOptionPane.showMessageDialog(null, "Documento nao está na rede,não será possível prosseguir!!");
                txtDocumento1.setText("");
                return;
            }

            if (tamanho >= 5) {
                JOptionPane.showMessageDialog(this, "Tamanho máximo de documento para inclusão no Portal jurídico é de 5 MB, este  arquivo tem " + tamanho + " Mega Byte");
                return;
            }

            txtDocumento1.setText(arquivoSelecionado);

            if (Usuario.getAtividade().equals("Rejud")) {
                txtJustificativa.setText("Inclusão de solicitação de levantamento conforme IN 869-2  item  2.4.4- funcionário responsável pela análise: " + user.getMatricula());

            }

            if (Usuario.getAtividade().equals("Forca tarefa")) {
                txtJustificativa.setText("Recomendação Auditoria 73.734.Contabilização efetuada com base no Painel URO - Intranet.Orientações Colab-URO de 26 De abril De 2017 - funcionário responsável pela análise: " + user.getMatricula());

            }

    }//GEN-LAST:event_btnIncluir1ActionPerformed
    }
    private void txtNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNPJActionPerformed

    private void txtNumeroOficioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroOficioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroOficioActionPerformed

    private void txtNPJKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNPJKeyTyped
    
        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }
        
    }//GEN-LAST:event_txtNPJKeyTyped

    private void txtValorSolicitacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorSolicitacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorSolicitacaoActionPerformed

    private void txtValorSolicitacaoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorSolicitacaoKeyTyped
         char c  = evt.getKeyChar();
          if(c=='R' || c=='$') evt.consume();
                
         /*   
            if(c==',' ) {
                evt.consume();
                JOptionPane.showMessageDialog(this, "Usar ponto para separação de casas decimais " );
          }
        */ 
    }//GEN-LAST:event_txtValorSolicitacaoKeyTyped

    private void txtEspecificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEspecificacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEspecificacaoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AcessoOutrosBancos acessoCef = new AcessoOutrosBancos();
        acessoCef.logarCef();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         AcessoOutrosBancos acessoBanrirul = new AcessoOutrosBancos();
         acessoBanrirul.logarBanrisul();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtTipoLevantamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoLevantamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoLevantamentoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        FormTabLevantamento formLevantamento = new FormTabLevantamento(null, rootPaneCheckingEnabled);
            formLevantamento.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        int i = JOptionPane.showConfirmDialog(null, "Deseja efetuar a destinação dos registros de solicitação de levantamento?",
                "Contabilização de resgate", JOptionPane.YES_NO_OPTION);

        if (i == JOptionPane.NO_OPTION) {

            return;
        }

        EfetivarLevantamento v = new EfetivarLevantamento();
        v.start();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        AcessoOutrosBancos acesso = new AcessoOutrosBancos();
        acesso.instrucaoRejud();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
         CriarSolicitacaoLevPortal criarSolicitacaoLevPortal = new CriarSolicitacaoLevPortal();
        criarSolicitacaoLevPortal.start();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtDataOficioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataOficioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataOficioActionPerformed
    
    
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
                new frmSolicitacaoLevantamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIncluir1;
    private javax.swing.JButton btnIncluir2;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsuarioAtual;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JFormattedTextField txtDataOficio;
    private javax.swing.JTextField txtDocumento1;
    private javax.swing.JTextField txtDocumento2;
    private javax.swing.JPanel txtEnderecoDocumento1;
    private javax.swing.JComboBox<String> txtEspecificacao;
    private javax.swing.JTextField txtFavorecido;
    private javax.swing.JComboBox<String> txtFinalidade;
    private javax.swing.JComboBox<String> txtIncluir;
    private javax.swing.JTextArea txtJustificativa;
    private javax.swing.JComboBox<String> txtLevantador;
    private javax.swing.JTextField txtNPJ;
    private javax.swing.JTextField txtNumeroOficio;
    private javax.swing.JTextField txtSaldoContabilPortal;
    private javax.swing.JComboBox<String> txtTipoLevantamento;
    private javax.swing.JTextField txtValorSolicitacao;
    private javax.swing.JSpinner txtVariacao;
    // End of variables declaration//GEN-END:variables
}
