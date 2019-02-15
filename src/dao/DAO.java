package dao;

import conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import entidade.Extrato;
import entidade.ProcessoBloqueio;
import entidade.SolicitacaoLevantamento;
import entidade.SolicitacaoTarifa;
import entidade.Usuario;

public class DAO<E> {

    Usuario user = new Usuario();
    private final List<E> list;
    private final String bancoDados;

    /**
     * Inicia o construtor informando a qual Banco de Dados a ser conectado
     * 
     * @param bancoDados nome do BD
     */
    public DAO(String bancoDados) {
        list = new ArrayList();
        this.bancoDados = bancoDados;
    }

    public void inserir(SolicitacaoLevantamento s) {
        Usuario user = new Usuario();
        Connection con;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO tb_solicitacao_levantamento (NPJ,VARIACAO_NPJ,OFICIO,DATA_OFICIO,HISTORICO,VALOR_SOLICITACAO,ENDERECO_DOCUMENTO1,SALDO,ENDERECO_DOCUMENTO2,LEVANTADOR,TIPO_LEVANTAMENTO,TIPO_DESTINACAO,ESPECIFICA_DESTINACAO,BENEFICIARIO,CPF_BENEFICIARIO,INCLUIR_PARTE,RESPONSAVEL_SOLICITACAO_INCLUSAO,DATA_SOLICITACAO_INCLUSAO) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getNpj());
            stmt.setInt(2, s.getVariacaoNpj());
            stmt.setString(3, s.getHistorico());
            stmt.setString(4, s.getOficio());
            stmt.setString(5, s.getDataOficio());
            stmt.setString(6, s.getValorSolicitacao());
            stmt.setString(7, s.getEnderecoDocumento1());
            stmt.setString(8, s.getSaldo());
            stmt.setString(9, s.getEnderecoDocumento2());
            stmt.setString(10, s.getLevantador());
            stmt.setString(11, s.getTipoLevantamento());
            stmt.setString(12, s.getFinalidade());
            stmt.setString(13, s.getEspecificacao());
            stmt.setString(14, s.getBeneficiario());
            stmt.setString(15, s.getCpfBeneficiario());
            stmt.setString(16, s.getIncluirParte());
            stmt.setString(17, user.getMatricula());
            stmt.setString(18, s.getDataSolicitacao());

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void inserirUsuario(Usuario s) {

        Connection con = null;
        try {
            con = conexao.ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO tb_usuarios (MATRICULA,SENHA,NOME_USUARIO,GRUPO_USUARIO) VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getMatricula());
            stmt.setString(2, s.getSenha());
            stmt.setString(3, s.getNomeUsuario());
            stmt.setString(4, s.getGrupoUsuario());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public int consultarUsuario(Usuario s) throws SQLException {

        PreparedStatement stmt;
        Connection con;
        con = ConnectionFactory.conectar(bancoDados);
        String query = "Select * from tb_usuarios where MATRICULA = ? AND SENHA= ?";
        stmt = con.prepareStatement(query);
        stmt.setString(1, s.getMatricula());
        stmt.setString(2, s.getSenha());
        ResultSet rs = stmt.executeQuery();

        int n = contareg(rs);
        if (n == 1) {

            do {
                String nome = rs.getString("NOME_USUARIO");
                String matricula = rs.getString("MATRICULA");
                String senha = rs.getString("SENHA");
                String grupoUsuario = rs.getString("GRUPO_USUARIO");
                int codigoUor = rs.getInt("CODIGO_UOR");
                int codigoFuncao = rs.getInt("CODIGO_CARGO");
                Usuario user = new Usuario(matricula, senha, nome, grupoUsuario, codigoUor, codigoFuncao);

            } while (rs.next());
        }

        rs.close();
        stmt.close();
        con.close();

        return n;

    }

    public int contareg(ResultSet rs) {

        int numResultados = 0;
        try {
            rs.last();
            numResultados = rs.getRow();
            rs.first();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return numResultados;

    }

    public void editar(String nomeTabela, String nomeCampo, String n, int num) throws SQLException {
        Usuario user = new Usuario();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE " + nomeTabela + " SET " + nomeCampo + " = ? where CODIGO = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        //seta os valores
        // stmt.setString(1, nomeCam);
        stmt.setString(1, n);
        stmt.setInt(2, num);

        //executa o codigo
        stmt.executeUpdate();
        con.close();
        stmt.close();

    }

    public void editarBaseWeb(String nomeTabela, String nomeCampo, String n, int num) throws SQLException {
        Usuario user = new Usuario();

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE " + nomeTabela + " SET " + nomeCampo + " = ? where CODIGO_DESCONCILIACAO = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        //seta os valores
        // stmt.setString(1, nomeCam);
        stmt.setString(1, n);
        stmt.setInt(2, num);

        //executa o codigo
        stmt.executeUpdate();
        con.close();
        stmt.close();

    }

    public List<SolicitacaoLevantamento> readALL() throws SQLException {

        List<SolicitacaoLevantamento> listaLevantamento = new ArrayList<>();

        Connection con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_solicitacao_levantamento where RESPONSAVEL_SOLICITACAO_INCLUSAO = ? and (ID_SITUACAO <> 'Concluída'  or ID_SITUACAO = '')";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, user.getMatricula());

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            sql = "select * from tb_solicitacao_levantamento where  ID_SITUACAO <> 'Concluída' or ID_SITUACAO = '' or ID_SITUACAO is null";
            stmt = con.prepareStatement(sql);

        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoLevantamento s = new SolicitacaoLevantamento();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setOficio(rs.getString("OFICIO"));
            s.setDataOficio(rs.getString("DATA_OFICIO"));
            s.setHistorico(rs.getString("HISTORICO"));
            s.setValorSolicitacao(rs.getString("VALOR_SOLICITACAO"));
            s.setEnderecoDocumento1(rs.getString("ENDERECO_DOCUMENTO1"));
            s.setAdversoBB(rs.getString("ADVERSO_BB"));
            s.setContaJudicialOB(rs.getString("CONTA_JUDICIAL_OB"));
            s.setSaldo(rs.getString("SALDO"));
            s.setResponsavelSolicitacaoInclusao(rs.getString("RESPONSAVEL_SOLICITACAO_INCLUSAO"));
            s.setDataSolicitacao(rs.getString("DATA_SOLICITACAO_INCLUSAO"));
            s.setResponsavelInclusao(rs.getString("RESPONSAVEL_INCLUSAO"));
            s.setDataInclusao(rs.getString("DATA_INCLUSAO"));
            s.setObs(rs.getString("OBS"));
            s.setContaJudicial(rs.getString("CONTA_JUDICIAL"));
            s.setSaldoContabil(rs.getString("SALDO_CONTABIL"));
            s.setEnderecoDocumento2(rs.getString("ENDERECO_DOCUMENTO2"));
            s.setLevantador(rs.getString("LEVANTADOR"));
            s.setFinalidade(rs.getString("TIPO_DESTINACAO"));
            s.setTipoLevantamento(rs.getString("TIPO_LEVANTAMENTO"));
            s.setEspecificacao(rs.getString("ESPECIFICA_DESTINACAO"));
            s.setBeneficiario(rs.getString("BENEFICIARIO"));
            s.setCpfBeneficiario(rs.getString("CPF_BENEFICIARIO"));
            s.setIdSituacao(rs.getString("ID_SITUACAO"));
            s.setSaldoColetadoportal(rs.getString("SALDO_COLETADO_PORTAL"));
            s.setObsDespacho(rs.getString("OBS_DESPACHO"));
            s.setObsContabillizacao(rs.getString("OBS_CONTABILIZACAO"));
            s.setObsIncluirParte(rs.getString("OBS_INCLUIR_PARTE"));
            s.setIncluirParte(rs.getString("INCLUIR_PARTE"));

            listaLevantamento.add(s);

        }
        con.close();
        rs.close();
        stmt.close();

        return listaLevantamento;
    }

    public List<SolicitacaoTarifa> readALLTarifa() throws SQLException {

        List<SolicitacaoTarifa> listaTarifa = new ArrayList<>();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_tarifa where FUNCIONARIO_RESPONSAVEL = ? and (ID_SITUACAO <> 'Concluída'  or ID_SITUACAO = '' or ID_SITUACAO is null) order by CODIGO ASC";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, user.getMatricula());

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            sql = "select * from tb_tarifa where  ID_SITUACAO <> 'Concluída' or ID_SITUACAO = '' or ID_SITUACAO is null order by CODIGO ASC";
            stmt = con.prepareStatement(sql);

        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoTarifa s = new SolicitacaoTarifa();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setValorTarifa(rs.getDouble("VALOR_TARIFA"));
            s.setValorCredito(rs.getDouble("VALOR_CREDITO"));
            s.setDataCredito(rs.getDate("DATA_CREDITO"));
            s.setDependenciaContabil(rs.getInt("DEPENDENCIA_CONTABIL"));
            s.setBancoBeneficiario(rs.getString("BANCO_BENEFICIARIO"));
            s.setIdSituacao(rs.getString("ID_SITUACAO"));
            s.setObsIncluirBancoBeneficiario(rs.getString("OBS_INCLUIR_BANCO_BENEFICIARIO"));
            s.setObsIncluirTarifaPortal(rs.getString("OBS_INCLUIR_TARIFA_PORTAL"));
            s.setObsAnaliseLiberacao(rs.getString("OBS_ANALISE_LIBERACAO"));
            s.setObsEfetivarTarifa(rs.getString("OBS_EFETIVAR_TARIFA"));
            s.setObsDespachoTarifa(rs.getString("OBS_DESPACHO_TARIFA"));
            s.setFuncionarioResponsavel(rs.getString("FUNCIONARIO_RESPONSAVEL"));
            s.setDataSolicitacao(rs.getDate("DATA_SOLICITACAO"));
            s.setObsPartidaEgt(rs.getString("OBS_PARTIDA_EGT"));
            s.setNumeroEgt(rs.getString("NUMERO_EGT"));
            s.setIdSituacaoEgt(rs.getString("ID_SITUACAO_EGT"));
            s.setDataDespachoPortal(rs.getString("DATA_DESPACHO_PORTAL"));

            listaTarifa.add(s);

        }
        con.close();
        rs.close();
        stmt.close();

        return listaTarifa;
    }

    public void atualizaSolicitacao(SolicitacaoLevantamento s) throws SQLException {
        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE tb_solicitacao_levantamento SET NPJ = ?, VARIACAO_NPJ = ?, VALOR_SOLICITACAO = ?, SALDO = ?, OFICIO = ?, DATA_OFICIO = ?, OBS = ?, OBS_CONTABILIZACAO = ?, OBS_DESPACHO = ?, OBS_INCLUIR_PARTE = ?, ENDERECO_DOCUMENTO1 = ?, ENDERECO_DOCUMENTO2 = ?, BENEFICIARIO = ?, CPF_BENEFICIARIO = ?, TIPO_LEVANTAMENTO = ?, LEVANTADOR = ? , TIPO_DESTINACAO = ?, ESPECIFICA_DESTINACAO = ?  WHERE CODIGO = ? ";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, s.getNpj());
        stmt.setInt(2, s.getVariacaoNpj());
        stmt.setString(3, s.getValorSolicitacao());
        stmt.setString(4, s.getSaldo());
        stmt.setString(5, s.getOficio());
        stmt.setString(6, s.getDataOficio());
        stmt.setString(7, s.getObs());
        stmt.setString(8, s.getObsContabillizacao());
        stmt.setString(9, s.getObsDespacho());
        stmt.setString(10, s.getObsIncluirParte());
        stmt.setString(11, s.getEnderecoDocumento1());
        stmt.setString(12, s.getEnderecoDocumento2());
        stmt.setString(13, s.getBeneficiario());
        stmt.setString(14, s.getCpfBeneficiario());
        stmt.setString(15, s.getTipoLevantamento());
        stmt.setString(16, s.getLevantador());
        stmt.setString(17, s.getFinalidade());
        stmt.setString(18, s.getEspecificacao());
        stmt.setInt(19, s.getCodigo());

        stmt.executeUpdate();
        stmt.close();
        con.close();

    }

    public List<SolicitacaoLevantamento> consultarNPJ(String npj) throws SQLException {
        List<SolicitacaoLevantamento> listaLevantamento = new ArrayList<>();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_solicitacao_levantamento where NPJ LIKE  '%'  ?   '%'  and (RESPONSAVEL_SOLICITACAO_INCLUSAO = ? and (ID_SITUACAO <> 'Concluída'  or ID_SITUACAO = ''))";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, npj);
        stmt.setString(2, user.getMatricula());

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            sql = "select * from tb_solicitacao_levantamento where NPJ LIKE  '%'  ?   '%' and (ID_SITUACAO <> 'Concluída'  or ID_SITUACAO = '') ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, npj);

        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoLevantamento s = new SolicitacaoLevantamento();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setOficio(rs.getString("OFICIO"));
            s.setDataOficio(rs.getString("DATA_OFICIO"));
            s.setHistorico(rs.getString("HISTORICO"));
            s.setValorSolicitacao(rs.getString("VALOR_SOLICITACAO"));
            s.setEnderecoDocumento1(rs.getString("ENDERECO_DOCUMENTO1"));
            s.setAdversoBB(rs.getString("ADVERSO_BB"));
            s.setContaJudicialOB(rs.getString("CONTA_JUDICIAL_OB"));
            s.setSaldo(rs.getString("SALDO"));
            s.setResponsavelSolicitacaoInclusao(rs.getString("RESPONSAVEL_SOLICITACAO_INCLUSAO"));
            s.setDataSolicitacao(rs.getString("DATA_SOLICITACAO_INCLUSAO"));
            s.setResponsavelInclusao(rs.getString("RESPONSAVEL_INCLUSAO"));
            s.setDataInclusao(rs.getString("DATA_INCLUSAO"));
            s.setObs(rs.getString("OBS"));
            s.setContaJudicial(rs.getString("CONTA_JUDICIAL"));
            s.setSaldoContabil(rs.getString("SALDO_CONTABIL"));
            s.setEnderecoDocumento2(rs.getString("ENDERECO_DOCUMENTO2"));
            s.setLevantador(rs.getString("LEVANTADOR"));
            s.setFinalidade(rs.getString("TIPO_DESTINACAO"));
            s.setTipoLevantamento(rs.getString("TIPO_LEVANTAMENTO"));
            s.setEspecificacao(rs.getString("ESPECIFICA_DESTINACAO"));
            s.setBeneficiario(rs.getString("BENEFICIARIO"));
            s.setCpfBeneficiario(rs.getString("CPF_BENEFICIARIO"));
            s.setIdSituacao(rs.getString("ID_SITUACAO"));
            s.setSaldoColetadoportal(rs.getString("SALDO_COLETADO_PORTAL"));
            s.setObsDespacho(rs.getString("OBS_DESPACHO"));
            s.setObsContabillizacao(rs.getString("OBS_CONTABILIZACAO"));
            s.setObsIncluirParte(rs.getString("OBS_INCLUIR_PARTE"));
            s.setIncluirParte(rs.getString("INCLUIR_PARTE"));

            listaLevantamento.add(s);

        }
        con.close();
        rs.close();
        stmt.close();
        return listaLevantamento;
    }

    public List<SolicitacaoLevantamento> consultarNPJMurchado(String npj) throws SQLException {
        List<SolicitacaoLevantamento> listaLevantamento = new ArrayList<>();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_solicitacao_levantamento where NPJ LIKE  '%'  ?   '%'  and (RESPONSAVEL_SOLICITACAO_INCLUSAO = ? and (ID_SITUACAO = 'Concluída' ))";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, npj);
        stmt.setString(2, user.getMatricula());

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            sql = "select * from tb_solicitacao_levantamento where NPJ LIKE  '%'  ?   '%' and (ID_SITUACAO = 'Concluída') ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, npj);

        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoLevantamento s = new SolicitacaoLevantamento();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setOficio(rs.getString("OFICIO"));
            s.setDataOficio(rs.getString("DATA_OFICIO"));
            s.setHistorico(rs.getString("HISTORICO"));
            s.setValorSolicitacao(rs.getString("VALOR_SOLICITACAO"));
            s.setEnderecoDocumento1(rs.getString("ENDERECO_DOCUMENTO1"));
            s.setAdversoBB(rs.getString("ADVERSO_BB"));
            s.setContaJudicialOB(rs.getString("CONTA_JUDICIAL_OB"));
            s.setSaldo(rs.getString("SALDO"));
            s.setResponsavelSolicitacaoInclusao(rs.getString("RESPONSAVEL_SOLICITACAO_INCLUSAO"));
            s.setDataSolicitacao(rs.getString("DATA_SOLICITACAO_INCLUSAO"));
            s.setResponsavelInclusao(rs.getString("RESPONSAVEL_INCLUSAO"));
            s.setDataInclusao(rs.getString("DATA_INCLUSAO"));
            s.setObs(rs.getString("OBS"));
            s.setContaJudicial(rs.getString("CONTA_JUDICIAL"));
            s.setSaldoContabil(rs.getString("SALDO_CONTABIL"));
            s.setEnderecoDocumento2(rs.getString("ENDERECO_DOCUMENTO2"));
            s.setLevantador(rs.getString("LEVANTADOR"));
            s.setFinalidade(rs.getString("TIPO_DESTINACAO"));
            s.setTipoLevantamento(rs.getString("TIPO_LEVANTAMENTO"));
            s.setEspecificacao(rs.getString("ESPECIFICA_DESTINACAO"));
            s.setBeneficiario(rs.getString("BENEFICIARIO"));
            s.setCpfBeneficiario(rs.getString("CPF_BENEFICIARIO"));
            s.setIdSituacao(rs.getString("ID_SITUACAO"));
            s.setSaldoColetadoportal(rs.getString("SALDO_COLETADO_PORTAL"));
            s.setObsDespacho(rs.getString("OBS_DESPACHO"));
            s.setObsContabillizacao(rs.getString("OBS_CONTABILIZACAO"));
            s.setObsIncluirParte(rs.getString("OBS_INCLUIR_PARTE"));
            s.setIncluirParte(rs.getString("INCLUIR_PARTE"));

            listaLevantamento.add(s);

        }
        con.close();
        rs.close();
        stmt.close();
        return listaLevantamento;
    }

    public List<SolicitacaoLevantamento> baseMurchada() throws SQLException {

        List<SolicitacaoLevantamento> listaLevantamento = new ArrayList<>();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_solicitacao_levantamento where RESPONSAVEL_SOLICITACAO_INCLUSAO = ?  and ID_SITUACAO = 'Concluída' ";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, user.getMatricula());

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            sql = "select * from tb_solicitacao_levantamento  where  ID_SITUACAO = 'Concluída' ;";
            stmt = con.prepareStatement(sql);

        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoLevantamento s = new SolicitacaoLevantamento();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setOficio(rs.getString("OFICIO"));
            s.setDataOficio(rs.getString("DATA_OFICIO"));
            s.setHistorico(rs.getString("HISTORICO"));
            s.setValorSolicitacao(rs.getString("VALOR_SOLICITACAO"));
            s.setEnderecoDocumento1(rs.getString("ENDERECO_DOCUMENTO1"));
            s.setAdversoBB(rs.getString("ADVERSO_BB"));
            s.setContaJudicialOB(rs.getString("CONTA_JUDICIAL_OB"));
            s.setSaldo(rs.getString("SALDO"));
            s.setResponsavelSolicitacaoInclusao(rs.getString("RESPONSAVEL_SOLICITACAO_INCLUSAO"));
            s.setDataSolicitacao(rs.getString("DATA_SOLICITACAO_INCLUSAO"));
            s.setResponsavelInclusao(rs.getString("RESPONSAVEL_INCLUSAO"));
            s.setDataInclusao(rs.getString("DATA_INCLUSAO"));
            s.setObs(rs.getString("OBS"));
            s.setContaJudicial(rs.getString("CONTA_JUDICIAL"));
            s.setSaldoContabil(rs.getString("SALDO_CONTABIL"));
            s.setEnderecoDocumento2(rs.getString("ENDERECO_DOCUMENTO2"));
            s.setLevantador(rs.getString("LEVANTADOR"));
            s.setFinalidade(rs.getString("TIPO_DESTINACAO"));
            s.setTipoLevantamento(rs.getString("TIPO_LEVANTAMENTO"));
            s.setEspecificacao(rs.getString("ESPECIFICA_DESTINACAO"));
            s.setBeneficiario(rs.getString("BENEFICIARIO"));
            s.setCpfBeneficiario(rs.getString("CPF_BENEFICIARIO"));
            s.setIdSituacao(rs.getString("ID_SITUACAO"));
            s.setSaldoColetadoportal(rs.getString("SALDO_COLETADO_PORTAL"));
            s.setObsDespacho(rs.getString("OBS_DESPACHO"));
            s.setObsContabillizacao(rs.getString("OBS_CONTABILIZACAO"));
            s.setObsIncluirParte(rs.getString("OBS_INCLUIR_PARTE"));
            s.setIncluirParte(rs.getString("INCLUIR_PARTE"));

            listaLevantamento.add(s);

        }
        con.close();
        rs.close();
        stmt.close();

        return listaLevantamento;
    }

    public void removeRegistro(String nomeTabela, int codigo) throws SQLException {
        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "DELETE FROM " + nomeTabela + " where CODIGO =  ? ";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, codigo);
        stmt.execute();
        con.close();

        stmt.close();

    }

    public List<Extrato> readExtrato() throws SQLException {

        List<Extrato> listaExtrato = new ArrayList<>();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select *  from tb_pog where FUNCIONARIO_RESPONSAVEL = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, user.getMatricula());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Extrato s = new Extrato();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setAgenciaDdepositaria(rs.getString("AGENCIA_DEPOSITARIA"));
            s.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
            s.setVariacaoContaDepositaria(rs.getInt("VARIACAO_CONTA_DEPOSITARIA"));
            s.setModalidadeDeposito(rs.getString("MODALIDADE_DEPOSITO"));
            s.setObs(rs.getString("OBS"));

            listaExtrato.add(s);

        }
        con.close();
        rs.close();
        stmt.close();

        return listaExtrato;
    }

    public void inserirExtrato(Extrato s) {
        Usuario user = new Usuario();
        Connection con = null;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO tb_pog (NPJ,AGENCIA_DEPOSITARIA,CONTA_DEPOSITARIA,VARIACAO_CONTA_DEPOSITARIA,MODALIDADE_DEPOSITO,FUNCIONARIO_RESPONSAVEL) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getNpj());
            stmt.setString(2, s.getAgenciaDdepositaria());
            stmt.setString(3, s.getContaDepositaria());
            stmt.setInt(4, s.getVariacaoContaDepositaria());
            stmt.setString(5, s.getModalidadeDeposito());
            stmt.setString(6, s.getFuncionarioResponsavel());

            stmt.execute();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public List<SolicitacaoTarifa> consultarNPJTarifa(String npj) throws SQLException {
        List<SolicitacaoTarifa> listaTarifa = new ArrayList<>();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "select * from tb_tarifa where NPJ LIKE  '%'  ?   '%'  and (FUNCIONARIO_RESPONSAVEL = ? and (ID_SITUACAO <> 'Concluída'  or ID_SITUACAO = '' or ID_SITUACAO is null))order by CODIGO ASC";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, npj);
        stmt.setString(2, user.getMatricula());

        if (user.getGrupoUsuario().equals("ADMINISTRADOR")) {
            sql = "select * from tb_tarifa where NPJ LIKE  '%'  ?   '%' and (ID_SITUACAO <> 'Concluída'  or ID_SITUACAO = '' or ID_SITUACAO is null) order by CODIGO ASC ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, npj);

        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            SolicitacaoTarifa s = new SolicitacaoTarifa();
            s.setCodigo(rs.getInt("CODIGO"));
            s.setNpj(rs.getString("NPJ"));
            s.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
            s.setValorTarifa(rs.getDouble("VALOR_TARIFA"));
            s.setValorCredito(rs.getDouble("VALOR_CREDITO"));
            s.setDataCredito(rs.getDate("DATA_CREDITO"));
            s.setDependenciaContabil(rs.getInt("DEPENDENCIA_CONTABIL"));
            s.setBancoBeneficiario(rs.getString("BANCO_BENEFICIARIO"));
            s.setIdSituacao(rs.getString("ID_SITUACAO"));
            s.setObsIncluirBancoBeneficiario(rs.getString("OBS_INCLUIR_BANCO_BENEFICIARIO"));
            s.setObsIncluirTarifaPortal(rs.getString("OBS_INCLUIR_TARIFA_PORTAL"));
            s.setObsAnaliseLiberacao(rs.getString("OBS_ANALISE_LIBERACAO"));
            s.setObsEfetivarTarifa(rs.getString("OBS_EFETIVAR_TARIFA"));
            s.setObsDespachoTarifa(rs.getString("OBS_DESPACHO_TARIFA"));
            s.setFuncionarioResponsavel(rs.getString("FUNCIONARIO_RESPONSAVEL"));
            s.setDataSolicitacao(rs.getDate("DATA_SOLICITACAO"));
            s.setObsPartidaEgt(rs.getString("OBS_PARTIDA_EGT"));
            s.setNumeroEgt(rs.getString("NUMERO_EGT"));
            s.setIdSituacaoEgt(rs.getString("ID_SITUACAO_EGT"));
            s.setDataDespachoPortal(rs.getString("DATA_DESPACHO_PORTAL"));

            listaTarifa.add(s);

        }
        con.close();
        rs.close();
        stmt.close();
        return listaTarifa;
    }

    public void editarGenerico(String nomeTabela, String nomeCampo, String n, int num) throws SQLException {
        Usuario user = new Usuario();

        Connection con = null;
        con = ConnectionFactory.conectar(bancoDados);
        String sql = "UPDATE " + nomeTabela + " SET " + nomeCampo + " = ? where CODIGO = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        //seta os valores
        // stmt.setString(1, nomeCam);
        stmt.setString(1, n);
        stmt.setInt(2, num);

        //executa o codigo
        stmt.executeUpdate();
        con.close();
        stmt.close();

    }

    public void inserirSolicitacaoTarifa(SolicitacaoTarifa t) {

        Usuario user = new Usuario();

        Connection con = null;
        try {
            con = ConnectionFactory.conectar(bancoDados);

            String sql = "INSERT INTO tb_tarifa (NPJ,VARIACAO_NPJ,VALOR_TARIFA,BANCO_BENEFICIARIO,ENDERECO_DOCUMENTO,FUNCIONARIO_RESPONSAVEL,DATA_SOLICITACAO,DEPENDENCIA_CONTABIL,NUMERO_DOCUMENTO,DATA_DOCUMENTO,PROTOCOLO_GSV,VALOR_CREDITO,DATA_CREDITO) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, t.getNpj());
            stmt.setInt(2, t.getVariacaoNpj());
            stmt.setDouble(3, t.getValorTarifa());
            stmt.setString(4, t.getBancoBeneficiario());
            stmt.setString(5, t.getEnderecoDocumento());
            stmt.setString(6, t.getFuncionarioResponsavel());
            stmt.setDate(7, (Date) t.getDataSolicitacao());
            stmt.setInt(8, t.getDependenciaContabil());
            stmt.setString(9, t.getNumeroDocumento());
            stmt.setDate(10, (Date) t.getDataDocumento());
            stmt.setString(11, t.getProtocoloGsv());
            stmt.setDouble(12, t.getValorCredito());
            stmt.setDate(13, (Date) t.getDataCredito());

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!!");
            con.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    /**
     * Pesquisa por registros com acesso bloqueado devido a utilização por outro
     * usuário
     *
     * @param npj Busca pelo NPJ bloqueado
     * @return Lista dos bloqueios
     */
    public List<ProcessoBloqueio> buscaBloqueios(String npj) {
        
        List<ProcessoBloqueio> listaBloqueios = new ArrayList<>();
        
        try {

            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_bloqueio_acesso WHERE npj = '" + npj + "'";        
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                ProcessoBloqueio npjBloq = new ProcessoBloqueio();

                npjBloq.setId(rs.getInt("id_bloqueio"));
                npjBloq.setNpj(rs.getString("npj"));
                npjBloq.setDataHora(rs.getDate("data_hora"));

                listaBloqueios.add(npjBloq);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return listaBloqueios;
    }

    
    /**
     * Insere novo registro que esteja com bloqueio de acesso devido a
     * utilização por outro usuário
     *
     * @param npj
     */
    public void insereBloqueado(String npj) {

        String sql = "INSERT INTO tb_bloqueio_acesso (npj, data_hora) VALUES (?, now())";
        Connection con = ConnectionFactory.conectar(bancoDados);

        try {

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, npj);

            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }
    
    /**
     * Lista dos registros que tem X minutos depois do timedate da inclusão
     * 
     * @param qtdTempo quantos minutos depois 
     * @param hora 
     * @return 
     */
    public List<ProcessoBloqueio> buscaBloqueios10(int qtdTempo, java.util.Date hora) {
       
        List<ProcessoBloqueio> listaBloqueios = new ArrayList<>();

        try {
            
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_bloqueio_acesso "
                    + "WHERE now() >= timestampadd(minute, " + qtdTempo + "," + hora + ")";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                ProcessoBloqueio npjBloq = new ProcessoBloqueio();

                npjBloq.setId(rs.getInt("id_bloqueio"));
                npjBloq.setNpj(rs.getString("npj"));
                npjBloq.setDataHora(rs.getDate("data_hora"));

                listaBloqueios.add(npjBloq);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

        return listaBloqueios;
    }

}
