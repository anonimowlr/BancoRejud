package dao;

import conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import entidade.Desconciliacao;
import entidade.Usuario;

/**
 *
 * @author f4281065
 * @param <E>
 */
public class AtualizacaoWebDAO<E> {

    private final String bancoDados;
    
    Usuario user = new Usuario();

    /**
     * Inicia o construtor informando a qual Banco de Dados a ser conectado
     * 
     * @param bancoDados 
     */
    public AtualizacaoWebDAO(String bancoDados) {
        this.bancoDados = bancoDados;
    }
    

    /**
     * ATENÇÃO! Execução DROP na tabela
     *
     * @param tabela tabela a ser apagada
     */
    public void apagaTabelaTemp(String tabela) {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "drop table if exists " + tabela;
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Cria tabela temporária incremento a partir dos registros não repetidos,
     * novo npj, nova conta registros não repetidos, novo npj, nova conta.
     */
    public void criarIncremento() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "create table if not exists tb_tmp_incremento as "
                + "select * from tb_temporaria_desconciliacao_djo_paj as t1 "
                + "where not exists (select * from tb_desconciliacao_djo_paj as t2 "
                + "where(t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA))";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Insere novos registros na tabela desconciliacao paj a partir da tabela
     * temporária incremento
     */
    public void inserirIncremento() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "insert into tb_desconciliacao_djo_paj "
                + "(NPJ,VARIACAO_NPJ,CONTA_DEPOSITARIA,AUTOR,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO,DATA_ENTRADA_BD)"
                + "(select NPJ,VARIACAO_NPJ,CONTA_DEPOSITARIA,AUTOR,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO,curdate() FROM tb_tmp_incremento)";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Cria tabela temporária concluídos
     */
    public void criarConcluidos() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "create table if not exists tb_tmp_concluidos as "
                + "select * from tb_desconciliacao_djo_paj as t1 "
                + "where not exists (select * from tb_temporaria_desconciliacao_djo_paj as t2 "
                + "where (t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA))";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Cria nova tabela temporária falso regularizado com registros novos são
     * NÃO REGULARIZADOS, verifica se tem o mesmo registro antigo com situação
     * REGULARIZADO e transforma em NÃO REGULARIZADO
     */
    public void criarFalsoRegularizado() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "create table tb_falso_regularizado as "
                + "(select t1.NPJ,t1.SITUACAO,t1.CONTA_DEPOSITARIA,t1.VALOR_DESCONCILIACAO,t1.DATA_SITUACAO,t1.DATA_ENTRADA_BD FROM tb_desconciliacao_djo_paj as t1 "
                + " inner join tb_temporaria_desconciliacao_djo_paj as t2 on t1.NPJ = t2.NPJ AND t1.CONTA_DEPOSITARIA =t2.CONTA_DEPOSITARIA AND t1.SITUACAO = 'REGULARIZADO' AND t1.DATA_SITUACAO < subdate(curdate(),interval 1 day) )";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Compara as tabelas desconciliacao com a temporaria para verificar se há
     * algum registro regularizado, mas que continua na lista nova
     *
     * @return Lista com os registros falso regularizados
     */
    public List<Desconciliacao> pesquisaFalsoRegularizado() {

        Desconciliacao desconciliacao;
        List<Desconciliacao> desconciliacoes = new ArrayList<>();

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "select t1.CODIGO_DESCONCILIACAO from tb_falso_regularizado t2 "
                + "inner join tb_desconciliacao_djo_paj t1 on t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA "
                + "where t1.SITUACAO ='REGULARIZADO'";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));

                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return desconciliacoes;
    }

    /**
     * Atualiza as desconciliacoes que eram falso regularizados
     * 
     * @param id Id da lista da consulta
     */
    public void upFalsoRegularizado(int id) {

        try {

            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "UPDATE tb_desconciliacao_djo_paj "
                    + "set SITUACAO = NULL, NOME_TRATAMENTO = NULL, DATA_SITUACAO = NULL, OBS_LIVRE = 'Este registro estava marcado como regularizado, foi desmarcado automaticamente devido à persistencia da desconciliação' "
                    + "where CODIGO_DESCONCILIACAO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Lista todos os registros com situação nula, mas já estão regularizados
     * 
     * @return Lista dos regularizados 
     */
    public List<Desconciliacao> pesquisaRegularizados() {

        Desconciliacao desconciliacao;
        List<Desconciliacao> desconciliacoes = new ArrayList<>();

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "select t1.CODIGO_DESCONCILIACAO from tb_tmp_concluidos t2 "
                + "inner join tb_desconciliacao_djo_paj t1 on t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA "
                + "where t1.SITUACAO is null";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));

                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return desconciliacoes;
    }

    /**
     * Atualiza os registros finalizados com situação nulo para regularizado
     * 
     * @param id ID do registro regularizado 
     */
    public void upRegularizado(int id) {

        try {

            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "UPDATE tb_desconciliacao_djo_paj "
                    + "set SITUACAO = 'REGULARIZADO', NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA', DATA_SITUACAO = curdate(), OBS_LIVRE = 'Este registro foi anotado como regularizado automaticamente devido nao estar mais na lista de desconciliação do Portal Jurídico', FUNCIONARIO_RESPONSAVEL_SITUACAO = '" + user.getMatricula() + "'  "
                    + "where CODIGO_DESCONCILIACAO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Insere registro diário da séria histórica das desconciliações
     */
    public void inserirHistorico() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "INSERT INTO tb_serie_historica_desconciliacao_dj_paj (QUANTIDADE,VALOR,DATA,VALOR_ABSOLUTO) (select count(CODIGO) AS QUANTIDADE, sum((VALOR_DESCONCILIACAO)) AS VALOR, curdate(),SUM(ABS(VALOR_DESCONCILIACAO)) AS VLR_ABSOLUTO from tb_temporaria_desconciliacao_djo_paj)";
        PreparedStatement stmt;
        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upAvocado() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "update tb_desconciliacao_djo_paj set AVOCADO = NULL where AVOCADO = 'SIM' AND DATA_AVOCACAO < curdate()";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upInedito() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "update tb_desconciliacao_djo_paj set SITUACAO = 'INEDITO TRATADO' where (SITUACAO = 'INEDITO SOLICITADO' AND DATA_SITUACAO < curdate())";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upDiasConci() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "update tb_desconciliacao_djo_paj set DIAS_DESCONCILIADO = datediff(curdate(),DATA_ENTRADA_BD) where DATA_PRIMEIRO_TRATAMENTO IS NULL AND DATA_ENTRADA_BD IS NOT NULL";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upPrimeiroTrat() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE tb_desconciliacao_djo_paj set DATA_PRIMEIRO_TRATAMENTO = (DATA_SITUACAO) where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA' AND DATA_PRIMEIRO_TRATAMENTO IS NULL";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upDiasConciRegular() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE tb_desconciliacao_djo_paj set DIAS_DESCONCILIADO = datediff(DATA_PRIMEIRO_TRATAMENTO,DATA_ENTRADA_BD) where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA'";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upPrazo() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE tb_desconciliacao_djo_paj set TRATADO_PRAZO = 'SIM' "
                + "where (DIAS_DESCONCILIADO <=10 and DIAS_DESCONCILIADO >=0 OR DIAS_DESCONCILIADO IS NULL) "
                + "AND (TRATADO_PRAZO IS NULL OR TRATADO_PRAZO = 'NAO') "
                + "AND DATA_PRIMEIRO_TRATAMENTO IS NOT NULL AND SITUACAO = 'REGULARIZADO' "
                + "AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA'";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upContabIguais(int id) {

        try {

            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "update tb_desconciliacao_djo_paj "
                    + "set SITUACAO = 'REGULARIZADO', NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA', DATA_SITUACAO = curdate(), OBS_LIVRE = 'Este registro foi anotado como regularizado automaticamente devido contabiliação de resgate igual a desconciliação atual', FUNCIONARIO_RESPONSAVEL_SITUACAO = '" + user.getMatricula() + "' "
                    + "where CODIGO_DESCONCILIACAO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public List<Desconciliacao> pesquisaContabDevIguais(String campo) {

        Desconciliacao desconciliacao;
        List<Desconciliacao> desconciliacoes = new ArrayList<>();

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "select t1.codigo_desconciliacao from tb_desconciliacao_djo_paj t1 "
                + "inner join tb_cpj_cadastro t2 on t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA and t1.VALOR_DESCONCILIACAO = t2.ID_Valor "
                + "where t1.SITUACAO is null and t2." + campo + " like '%' 'sucesso' '%'";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));

                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return desconciliacoes;
    }

    public void upDevolucaoIgual(int id) {

        try {

            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "UPDATE tb_desconciliacao_djo_paj "
                    + "set SITUACAO = 'EM TRATAMENTO', NOME_TRATAMENTO = 'EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA', DATA_SITUACAO = curdate(), OBS_LIVRE = 'ANOTACAO AUTOMATICA', FUNCIONARIO_RESPONSAVEL_SITUACAO = '" + user.getMatricula() + "' "
                    + "where CODIGO_DESCONCILIACAO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upPrimeiroTratPend() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE tb_desconciliacao_djo_paj AS t1 set DATA_PRIMEIRO_TRATAMENTO = (t1.DATA_SITUACAO)  where NOME_TRATAMENTO = 'EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA' AND OBS_LIVRE = 'ANOTACAO AUTOMATICA' AND DATA_PRIMEIRO_TRATAMENTO IS NULL";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void upPrazoRetorno() {

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE tb_desconciliacao_djo_paj  as t1 set TRATADO_PRAZO = 'SIM' where (DIAS_DESCONCILIADO  <=10 and DIAS_DESCONCILIADO>=0 OR DIAS_DESCONCILIADO IS NULL)  AND (TRATADO_PRAZO IS NULL OR TRATADO_PRAZO = 'NAO') AND DATA_PRIMEIRO_TRATAMENTO IS NOT NULL AND NOME_TRATAMENTO = 'EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA' AND OBS_LIVRE = 'ANOTACAO AUTOMATICA'";
        PreparedStatement stmt;

        try {

            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AtualizacaoWebDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

}
