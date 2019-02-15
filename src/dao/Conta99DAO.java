/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import entidade.Conta99;
import entidade.Usuario;

/**
 *
 * @author f4281065
 * @param <E>
 */
public class Conta99DAO<E> {

    private final String bancoDados;
    private final String tabelaBanco;

    Usuario user = new Usuario();

    /**
     * Inicia o construtor informando o nome do Bando de Dados a ser conectado e
     * em qual tabela da conta judicial a ser utilizada
     *
     * @param bancoDados nome do BD
     * @param tabelaBanco nome da tabela do BD (conta 691 ou 700)
     */
    public Conta99DAO(String bancoDados, String tabelaBanco) {
        this.bancoDados = bancoDados;
        this.tabelaBanco = tabelaBanco;
    }

    /**
     * Insere registros do objeto Conta99 para BD
     *
     * @param conta99 objeto Conta99
     */
    public void inserirMovimento(Conta99 conta99) {

        Connection con;
        PreparedStatement stmt;

        try {

            con = ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO " + tabelaBanco
                    + " (DATA_MOV, COD_HISTORICO, DESCRICAO_HISTORICO, BANCO, AGENCIA, CPF_CNPJ, "
                    + " DESCRICAO_BANCOS, DOCUMENTO, ORIGEM, LOTE, VALOR, LANCAMENTO, DATA_ROTINA) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setDate(1, (Date) conta99.getDataMovimentacao());
            stmt.setInt(2, conta99.getCodigoHistorico());
            stmt.setString(3, conta99.getDescricaoHistorico());
            stmt.setInt(4, conta99.getCodigoBanco());
            stmt.setInt(5, conta99.getCodigoAgencia());
            stmt.setDouble(6, conta99.getCpfCnpj());
            stmt.setString(7, conta99.getDescricaoBancos());
            stmt.setLong(8, conta99.getNumeroDoc());
            stmt.setInt(9, conta99.getCodigoOrigem());
            stmt.setInt(10, conta99.getNumeroLote());
            stmt.setDouble(11, conta99.getValor());
            stmt.setString(12, conta99.getTipoLancamento());
            stmt.setDate(13, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    /**
     * Realiza uma consulta no BD retornando o registro com a data mais recente
     *
     * @param coluna Coluna do Bando de Dados tipo DATE
     * @return Date mais recente (yyyy-MM-dd)
     */
    public java.util.Date buscaDataRecente(String coluna) {

        Conta99 conta99 = new Conta99();
        java.util.Date dataRecente = new java.util.Date();

        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT " + coluna + " FROM " + tabelaBanco
                    + " GROUP BY " + coluna + " ORDER BY " + coluna + " DESC LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            conta99.setDataMovimentacao(rs.getDate(coluna));

            dataRecente = conta99.getDataMovimentacao();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return dataRecente;

    }

    /**
     * Realiza uma consulta no BD retornando o registro com a data mais recente
     * caso não foi complementado via COMPE
     *
     * @param coluna Coluna do Bando de Dados tipo DATE
     * @return Date mais recente (yyyy-MM-dd)
     */
    public java.util.Date confereComplementacao(String coluna) {

        Conta99 conta99 = new Conta99();
        java.util.Date dataRecente = new java.util.Date();

        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT " + coluna + " FROM " + tabelaBanco + " WHERE historico_ted is not null"
                    + " GROUP BY " + coluna + " ORDER BY " + coluna + " DESC LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            conta99.setDataMovimentacao(rs.getDate(coluna));

            dataRecente = conta99.getDataMovimentacao();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return dataRecente;

    }

    /**
     * Soma dos creditos na data requerida, utilizado para conferencia das
     * entradas e saidas
     *
     * @param dataCapturada data da movimentacao (yyyy-MM-dd)
     * @return soma dos creditos
     */
    public Double saldoCredito99(String dataCapturada) {

        Conta99 conta99 = new Conta99();
        Double credito = null;

        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT sum(valor) AS credito FROM " + tabelaBanco
                    + " WHERE lancamento = 'C' and DATA_MOV = '" + dataCapturada + "'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            conta99.setValor(rs.getDouble("CREDITO"));

            credito = conta99.getValor();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return credito;

    }

    /**
     * Soma dos debitos na data requerida, utilizado para conferencia das
     * entradas e saidas
     *
     * @param dataCapturada data da movimentacao (yyyy-MM-dd)
     * @return soma dos debitos
     */
    public Double saldoDebito99(String dataCapturada) {

        Conta99 conta99 = new Conta99();
        Double debito = null;

        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT sum(valor) AS debito FROM " + tabelaBanco
                    + " WHERE lancamento = 'D' and DATA_MOV = '" + dataCapturada + "'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            conta99.setValor(rs.getDouble("DEBITO"));

            debito = conta99.getValor();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return debito;

    }

    /**
     * Pesquisa os TED's ou DOC's das capturas da data selecionada
     *
     * @param codigo 623 para DOC ou 976 para TED
     * @param dataCapturada data que a rotina foi gravada (yyyy-MM-dd)
     * @return lista dos TED's da Conta99
     */
    public List<Conta99> buscaDocTed(String codigo, String dataCapturada) {

        List<Conta99> listaConta99 = new ArrayList<>();

        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT id_conta99, data_mov, documento, valor FROM " + tabelaBanco
                    + " WHERE cod_historico = " + codigo + " AND data_mov = '" + dataCapturada + "'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Conta99 conta99 = new Conta99();

                conta99.setCodigoConta99(rs.getInt("ID_CONTA99"));
                conta99.setDataMovimentacao(rs.getDate("DATA_MOV"));
                conta99.setNumeroDoc(rs.getLong("DOCUMENTO"));
                conta99.setValor(rs.getDouble("VALOR"));

                listaConta99.add(conta99);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return listaConta99;
    }

    /**
     * Pesquisa as datas de movimentação de cada registro do retag realizado na
     * data que foi executado a rotina
     *
     * @param dataCapturada data da rotina que foi executada (yyyy-MM-dd)
     * @return lista das datas de movimentação Conta99
     */
    public List<Conta99> dataMovimentacaoRotina(String dataCapturada) {

        List<Conta99> listaDataRotina = new ArrayList<>();

        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT data_mov FROM " + tabelaBanco
                    + " WHERE data_rotina = '" + dataCapturada + "' GROUP BY data_mov ORDER BY data_mov";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Conta99 conta99 = new Conta99();

                conta99.setDataMovimentacao(rs.getDate("DATA_MOV"));

                listaDataRotina.add(conta99);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return listaDataRotina;
    }

    /**
     * Atualização registros no BD
     *
     * @param coluna nome variável (coluna) no MySQL
     * @param valor nova entrada
     * @param id id do registro do BD
     */
    public void updateConta99(String coluna, String valor, int id) {

        Connection con;
        PreparedStatement stmt;

        try {

            con = ConnectionFactory.conectar(bancoDados);

            String sql = "UPDATE " + tabelaBanco + " SET " + coluna + " = ? WHERE id_conta99 = ?";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, valor);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

}
