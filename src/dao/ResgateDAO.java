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
import java.util.List;
import javax.swing.JOptionPane;
import entidade.Resgate;
import entidade.Usuario;

/**
 *
 * @author f5078775
 * @param <E>
 */
public class ResgateDAO<E> {

    private final String bancoDados;
    
    Usuario user = new Usuario();

    public ResgateDAO(String bancoDados) {
        this.bancoDados = bancoDados;
    }

    
    public void inserirResgate(Resgate resgate) {

        Connection con = null;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO tb_cpj_cadastro (ID_Portal,ID_NPJ,ID_Situação,ID_Valor,Proces_Autor,Proces_Reu,proces_processo,proces_vara,proces_comarca,"
                    + "proces_tpacao,proces_natureza,proces_uja,proces_advogadobb,contab_dependencia,contab_inicio,"
                    + "contab_valororigem,contab_saldo,depos_situacao,depos_banco,depos_agencia,depos_modalidade,"
                    + "depos_deposito,solic_valor,solic_ordem,solic_data,solic_validade,solic_prefixo,liber_valor,"
                    + "liber_tipo,liber_operadora,liber_prefixo,liber_observacao,relcompletoassunto,RelCompletoMateria,ID_Situacao_Data,ID_Situacao_Hora,"
                    + "FUNCI_SITUACAO,NPJ,CONTA_DEPOSITARIA,ID_Deposito) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, resgate.getIDPortal());
            stmt.setString(2, resgate.getIdNpj());
            stmt.setString(3, resgate.getIDSituação());
            stmt.setBigDecimal(4, resgate.getIDValor());
            stmt.setString(5, resgate.getProcesAutor());
            stmt.setString(6, resgate.getProcesReu());
            stmt.setString(7, resgate.getProcesProcesso());
            stmt.setString(8, resgate.getProcesVara());
            stmt.setString(9, resgate.getProcesComarca());
            stmt.setString(10, resgate.getProcesTpacao());
            stmt.setString(11, resgate.getProcesNatureza());
            stmt.setString(12, resgate.getProcesUja());
            stmt.setString(13, resgate.getProcesAdvogadobb());
            stmt.setString(14, resgate.getContabDependencia());
            stmt.setDate(15, (Date) resgate.getContabInicio());
            stmt.setBigDecimal(16, resgate.getContabValororigem());
            stmt.setBigDecimal(17, resgate.getContabSaldo());
            stmt.setString(18, resgate.getDeposSituacao());
            stmt.setString(19, resgate.getDeposBanco());
            stmt.setString(20, resgate.getDeposAgencia());
            stmt.setString(21, resgate.getDeposModalidade());
            stmt.setString(22, resgate.getDeposDeposito());
            stmt.setBigDecimal(23, resgate.getSolicValor());
            stmt.setString(24, resgate.getSolicOrdem());
            stmt.setDate(25, (Date) resgate.getSolicData());
            stmt.setDate(26, (Date) resgate.getSolicValidade());
            stmt.setInt(27, resgate.getSolicPrefixo());
            stmt.setBigDecimal(28, resgate.getLiberValor());
            stmt.setString(29, resgate.getLiberTipo());
            stmt.setInt(30, resgate.getLiberOperadora());
            stmt.setInt(31, resgate.getLiberPrefixo());
            stmt.setString(32, resgate.getLiberObservacao());
            stmt.setString(33, resgate.getRelcompletoassunto());
            stmt.setString(34, resgate.getRelCompletoMateria());
            stmt.setDate(35, (Date) resgate.getIDSituacaoData());
            stmt.setDate(36, (Date) resgate.getIDSituacaoHora());
            stmt.setString(37, resgate.getFunciSituacao());
            stmt.setString(38, resgate.getNpj());
            stmt.setString(39, resgate.getContaDepositaria());
            stmt.setString(40, resgate.getIDDeposito());

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public int proximoSequencial() {
        int i = 0;

        try {
            Connection con = null;
            con = ConnectionFactory.conectar(bancoDados);
            String sql = "select max(ID_Portal) as ultimoID from tb_cpj_cadastro;";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                i = rs.getInt("ultimoID");

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex);

        } finally {
            ConnectionFactory.fecharConexao();
        }

        return i + 1;

    }

    /**
     * Cria uma lista da tb_cpj_cadastro onde banco depositário é o BB e sem
     * alvará eletrônico (null)
     *
     * @return Lista dos resgates
     * @throws SQLException
     */
    public List<Resgate> buscar() throws SQLException {

        List<Resgate> resgates = new ArrayList<>();

        try {
            Connection con = null;
            con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_cpj_cadastro  "
                    + "where depos_banco like '%' 'BANCO DO BRASIL' '%' "
                    + "and ALVARA_ELETRONICO is null "
                    + "and (data_rotina_djo_sequencial <> curdate() OR data_rotina_djo_sequencial IS NULL) "
                    + "order by ID_Situacao_Data desc";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Resgate s = new Resgate();
                s.setCod(rs.getInt("cod"));
                s.setNpj(rs.getString("NPJ"));
                s.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                s.setSolicOrdem(rs.getString("solic_ordem"));
                s.setIDValor(rs.getBigDecimal("ID_Valor"));

                resgates.add(s);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return resgates;
    }

    public List<Resgate> buscarParaSincronizar() throws SQLException {

        List<Resgate> resgates = new ArrayList<>();

        try {
            Connection con = null;
            con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_cpj_cadastro where ENVOLVIDO IS NULL AND (DATA_SINCRONIZACAO IS NULL OR DATA_SINCRONIZACAO < curdate()) ";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Resgate s = new Resgate();
                s.setCod(rs.getInt("cod"));
                s.setNpj(rs.getString("NPJ"));
                s.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                s.setSolicOrdem(rs.getString("solic_ordem"));
                s.setIDValor(rs.getBigDecimal("ID_Valor"));
                s.setIDPortal(rs.getInt("ID_Portal"));

                resgates.add(s);

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex);

        } finally {
            ConnectionFactory.fecharConexao();
        }

        return resgates;
    }

    public void atualizaRegistroResgate(Resgate resgate) {

        Connection con = null;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "Update tb_cpj_cadastro set ID_Situação = ?,contab_saldo = ?, depos_situacao = ?,ID_Situacao_Data=?,"
                    + "FUNCI_SITUACAO = ?,ESPECIFICACAO=?,ENVOLVIDO=?,VALOR_DESTINADO=?,DATA_SINCRONIZACAO=? WHERE cod = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, resgate.getIDSituação());
            stmt.setBigDecimal(2, resgate.getContabSaldo());
            stmt.setString(3, resgate.getDeposSituacao());
            stmt.setDate(4, (Date) resgate.getIDSituacaoData());
            stmt.setString(5, resgate.getFunciSituacao());
            stmt.setString(6, resgate.getEspecificacao());
            stmt.setString(7, resgate.getEnvolvido());
            stmt.setBigDecimal(8, resgate.getValorDestinado());
            stmt.setDate(9, (Date) resgate.getDataSincronizacao());

            stmt.setInt(10, resgate.getCod());

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    public void inserirDadosDJOSequencial(Resgate resgate) {

        Connection con = null;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "Update tb_cpj_cadastro set   djo_51_beneficiario = ?, "
                                                    + "djo_51= ?, "
                                                    + "djo_51_parcela = ?, "
                                                    + "djo_51_protocolo = ?, "
                                                    + "djo_51_ordem =?, "
                                                    + "djo_51_agencia_responsavel = ?, "
                                                    + "FINALIZADO_POR = ?, "
                                                    + "djo_51_finalidade = ?, "
                                                    + "djo_51_f12_parte_nome = ?, "
                                                    + "djo_51_f12_parte_tipo = ?, "
                                                    + "ALVARA_ELETRONICO = ? "
                    + "  WHERE cod = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, resgate.getDjo51Beneficiario());
            stmt.setString(2, resgate.getDjo51());
            stmt.setString(3, resgate.getDjo51Parcela());
            stmt.setString(4, resgate.getDjo51Protocolo());
            stmt.setString(5, resgate.getDjo51Ordem());
            stmt.setString(6, resgate.getDjo51AgenciaResponsavel());
            stmt.setString(7, resgate.getFinalizadoPor());
            stmt.setString(8, resgate.getDjo51Finalidade());
            stmt.setString(9, resgate.getDjo51F12ParteNome());
            stmt.setString(10, resgate.getDjo51F12ParteTipo());
            stmt.setString(11, resgate.getAlvaraEletronico());

            stmt.setInt(12, resgate.getCod());

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
