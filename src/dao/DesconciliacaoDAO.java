
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
import entidade.Desconciliacao;
import entidade.Usuario;
import util.Utils;

public class DesconciliacaoDAO<E> {

    private final String bancoDados;
    
    Usuario user = new Usuario();

    /**
     * Inicia o construtor informando a qual Banco de Dados a ser conectado
     * 
     * @param bancoDados nome do BD
     */
    public DesconciliacaoDAO(String bancoDados) {
        this.bancoDados = bancoDados;
    }
    

    public List<Desconciliacao> buscar() {
        Desconciliacao desconciliacao = null;
        List<Desconciliacao> desconciliacoes = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_desconciliacao_djo_paj where (SITUACAO <> 'REGULARIZADO' OR SITUACAO IS NULL OR SITUACAO = '' ) AND(MATERIA IS NULL OR MATERIA = '') order by VALOR_DESCONCILIACAO DESC,DATA_DESCONCILIACAO ASC";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
           

            while (rs.next()) {

                 desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setNpj(rs.getString("NPJ"));
                desconciliacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                desconciliacao.setContaControle(rs.getString("CONTA_CONTROLE"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                desconciliacao.setSaldoContaControle(rs.getDouble("SALDO_CONTA_CONTROLE"));
                desconciliacao.setSaldoDeposito(rs.getDouble("SALDO_DEPOSITO"));
                desconciliacao.setValorDesconciliacao(rs.getDouble("VALOR_DESCONCILIACAO"));
                desconciliacao.setSituacao(rs.getString("SITUACAO"));
                desconciliacao.setDataSituacao(rs.getDate("DATA_SITUACAO"));
                desconciliacao.setFuncionarioResponsavelSituacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SITUACAO"));
                desconciliacao.setFuncionarioAtual(rs.getString("FUNCIONARIO_ATUAL"));
                desconciliacao.setNomeTratamento(rs.getString("NOME_TRATAMENTO"));
                desconciliacao.setAvocado(rs.getString("AVOCADO"));
                desconciliacao.setDataAvocacao(rs.getDate("DATA_AVOCACAO"));
                desconciliacao.setDataDesconciliacao(rs.getDate("DATA_DESCONCILIACAO"));
                desconciliacao.setDiasDesconciliado(rs.getInt("DIAS_DESCONCILIADO"));
                desconciliacao.setDataEntradaBd(rs.getDate("DATA_ENTRADA_BD"));
                desconciliacao.setObsLivre(rs.getString("OBS_LIVRE"));
                desconciliacao.setAutor(rs.getString("AUTOR"));
                desconciliacao.setMateria(rs.getString("MATERIA"));
                desconciliacao.setAssunto(rs.getString("ASSUNTO"));

                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }
         return desconciliacoes;  
      }

    public List<Desconciliacao> buscarRetornoAgencia() {
        Desconciliacao desconciliacao = null;
        List<Desconciliacao> desconciliacoes = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_desconciliacao_djo_paj where (SITUACAO = 'EM TRATAMENTO' and NOME_TRATAMENTO LIKE '%' 'RETORNO' '%')  order by DATA_SITUACAO ASC";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setNpj(rs.getString("NPJ"));
                desconciliacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                desconciliacao.setContaControle(rs.getString("CONTA_CONTROLE"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                desconciliacao.setSaldoContaControle(rs.getDouble("SALDO_CONTA_CONTROLE"));
                desconciliacao.setSaldoDeposito(rs.getDouble("SALDO_DEPOSITO"));
                desconciliacao.setValorDesconciliacao(rs.getDouble("VALOR_DESCONCILIACAO"));
                desconciliacao.setSituacao(rs.getString("SITUACAO"));
                desconciliacao.setDataSituacao(rs.getDate("DATA_SITUACAO"));
                desconciliacao.setFuncionarioResponsavelSituacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SITUACAO"));
                desconciliacao.setFuncionarioAtual(rs.getString("FUNCIONARIO_ATUAL"));
                desconciliacao.setNomeTratamento(rs.getString("NOME_TRATAMENTO"));
                desconciliacao.setAvocado(rs.getString("AVOCADO"));
                desconciliacao.setDataAvocacao(rs.getDate("DATA_AVOCACAO"));
                desconciliacao.setDataDesconciliacao(rs.getDate("DATA_DESCONCILIACAO"));
                desconciliacao.setDiasDesconciliado(rs.getInt("DIAS_DESCONCILIADO"));
                desconciliacao.setDataEntradaBd(rs.getDate("DATA_ENTRADA_BD"));
                desconciliacao.setObsLivre(rs.getString("OBS_LIVRE"));
                desconciliacao.setAutor(rs.getString("AUTOR"));
                desconciliacao.setMateria(rs.getString("MATERIA"));
                desconciliacao.setAssunto(rs.getString("ASSUNTO"));

                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return desconciliacoes;
    }

  
      
    public List<Desconciliacao> buscarParaLerSaldo() {
        Desconciliacao desconciliacao = null;
        List<Desconciliacao> desconciliacoes = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_desconciliacao_djo_paj where (SITUACAO <> 'REGULARIZADO' OR SITUACAO IS NULL OR SITUACAO = '')  order by VALOR_DESCONCILIACAO DESC,DATA_DESCONCILIACAO ASC";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setNpj(rs.getString("NPJ"));
                desconciliacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                desconciliacao.setContaControle(rs.getString("CONTA_CONTROLE"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                desconciliacao.setSaldoContaControle(rs.getDouble("SALDO_CONTA_CONTROLE"));
                desconciliacao.setSaldoDeposito(rs.getDouble("SALDO_DEPOSITO"));
                desconciliacao.setValorDesconciliacao(rs.getDouble("VALOR_DESCONCILIACAO"));
                desconciliacao.setSituacao(rs.getString("SITUACAO"));
                desconciliacao.setDataSituacao(rs.getDate("DATA_SITUACAO"));
                desconciliacao.setFuncionarioResponsavelSituacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SITUACAO"));
                desconciliacao.setFuncionarioAtual(rs.getString("FUNCIONARIO_ATUAL"));
                desconciliacao.setNomeTratamento(rs.getString("NOME_TRATAMENTO"));
                desconciliacao.setAvocado(rs.getString("AVOCADO"));
                desconciliacao.setDataAvocacao(rs.getDate("DATA_AVOCACAO"));
                desconciliacao.setDataDesconciliacao(rs.getDate("DATA_DESCONCILIACAO"));
                desconciliacao.setDiasDesconciliado(rs.getInt("DIAS_DESCONCILIADO"));
                desconciliacao.setDataEntradaBd(rs.getDate("DATA_ENTRADA_BD"));
                desconciliacao.setObsLivre(rs.getString("OBS_LIVRE"));
                desconciliacao.setAutor(rs.getString("AUTOR"));
                desconciliacao.setMateria(rs.getString("MATERIA"));
                desconciliacao.setAssunto(rs.getString("ASSUNTO"));

                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return desconciliacoes;
    }

 
    /**
     * Verifica os registros não regularizados para atualização dos saldos do DJO
     * 
     * @return LIST desconciliacoes nao regularizados
     * @autor f4281065
     */  
    public List<Desconciliacao> buscarParaLerSaldoDJO() {
        
        List<Desconciliacao> desconciliacoes = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_desconciliacao_djo_paj "
                    + "where (SITUACAO <> 'REGULARIZADO' OR SITUACAO IS NULL OR SITUACAO = '') "
                    + "and (DATA_ROTINA_SALDO_DJO <> curdate() OR DATA_ROTINA_SALDO_DJO IS NULL) "
                    + "order by VALOR_DESCONCILIACAO DESC, DATA_DESCONCILIACAO ASC";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Desconciliacao desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));               
    
                desconciliacoes.add(desconciliacao);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return desconciliacoes;
        
    }


    
    public void editarGenerico(String nomeTabela, String nomeCampo, String n, int num)  {
        

        try {
            Connection con = null;
            con = ConnectionFactory.conectar(bancoDados);
            String sql = "UPDATE " + nomeTabela + " SET " + nomeCampo + " = ? where CODIGO_DESCONCILIACAO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            //seta os valores
            // stmt.setString(1, nomeCam);
            stmt.setString(1, n);
            stmt.setInt(2, num);

            //executa o codigo
            stmt.executeUpdate();

        
        }catch (SQLException ex){            
            JOptionPane.showMessageDialog(null, ex);            
        } finally{            
            ConnectionFactory.fecharConexao();
        }
        

    }
    
    /**
     * ATENÇÃO! Apaga registros (Truncate) na tabela do Bando de Dados
     *
     * @param tabela do banco selecionado
     */
    public void apagaRegistrosTabela(String tabela) {

        String sql;

        try {

            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar(bancoDados);

            sql = "truncate " + tabela;
            stmt = con.prepareStatement(sql);
            
            stmt.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally{
            ConnectionFactory.fecharConexao();
        }

    }
    
     public void inserirNovaDesconciliacao(Desconciliacao entidade) {

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar(bancoDados);

            sql = "INSERT INTO tb_desconciliacao_djo_paj (NPJ,VARIACAO_NPJ,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO) VALUES (?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, entidade.getNpj());
            stmt.setInt(2, entidade.getVariacaoNpj());
            stmt.setString(3, entidade.getAutor());
            stmt.setString(4, entidade.getContaDepositaria());
            stmt.setDouble(5, entidade.getValorDesconciliacao());
            stmt.setString(6, entidade.getDataDesconciliacao().toString());

            stmt.executeUpdate();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null,"Erro ao tentar salvar reinicie o procedimento");
            System.exit(0);

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

     
     /**
      * Insere os registros das desconciliações na tabela temporária
      * 
      * @param desconciliacao Objeto desconciliacao
      * @param tabela temporária do banco selecionado
      */
     public void inserirRegistroNovoTbTemporaria(Desconciliacao desconciliacao, String tabela)  {        

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar(bancoDados);

            sql = "INSERT INTO " + tabela + "(NPJ,VARIACAO_NPJ,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO) "
                    + "VALUES (?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, desconciliacao.getNpj());
            stmt.setInt(2, desconciliacao.getVariacaoNpj());
            stmt.setString(3, desconciliacao.getAutor());
            stmt.setString(4, desconciliacao.getContaDepositaria());
            stmt.setDouble(5, desconciliacao.getValorDesconciliacao());
            stmt.setString(6, desconciliacao.getDataDesconciliacao().toString());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar salvar: " + ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }
    
    /**
     * Consulta BD tabelas
     * 
     * @param tabela Nome da tabela
     * @return Lista das Desconciliações
     */
    public List<Desconciliacao> consultaTbTemp(String tabela) {
        
        List<Desconciliacao> desconciliacoes = new ArrayList<>();
        
        try {
            
            Connection con = ConnectionFactory.conectar(bancoDados);
            
            String sql = "SELECT * FROM " + tabela;
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Desconciliacao desconciliacao = new Desconciliacao();

                desconciliacao.setNpj(rs.getString("NPJ"));
                desconciliacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                desconciliacao.setAutor(rs.getString("AUTOR"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                desconciliacao.setValorDesconciliacao(rs.getDouble("VALOR_DESCONCILIACAO"));
                desconciliacao.setDataDesconciliacao(rs.getDate("DATA_DESCONCILIACAO"));

                desconciliacoes.add(desconciliacao);

            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ConnectionFactory.fecharConexao();
        }
        
        return desconciliacoes;
    }

  
    @SuppressWarnings("null")
    public void atualizacaoDiaria() throws SQLException  {
 
          // CallableStatement callableStatement = null;
        Connection con = null;

        try {

            con = ConnectionFactory.conectar(bancoDados);
//             callableStatement = (CallableStatement) con.prepareCall("{CALL atualizar_diario}");
//             callableStatement.execute();

            con.setAutoCommit(false);
            String sql = "drop table if exists tb_tmp_incremento";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "drop table if exists tb_tmp_concluidos";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "drop table if exists tb_falso_regularizado";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            //cria tabela com registros não repetidos, novo npj, nova conta
            sql = "create table if not exists tb_tmp_incremento as "
                    + "select * from tb_temporaria_desconciliacao_djo_paj as t1 "
                    + "where not exists "
                        + "(select * from tb_desconciliacao_djo_paj as t2 "
                        +  "where(t1.NPJ = t2.NPJ and "
                        +        "t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA) )";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            //insere os novos registros
            sql = "insert into tb_desconciliacao_djo_paj "
                    + "(NPJ,VARIACAO_NPJ,CONTA_DEPOSITARIA,AUTOR,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO,DATA_ENTRADA_BD)"
                    + "(select NPJ,VARIACAO_NPJ,CONTA_DEPOSITARIA,AUTOR,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO,curdate() FROM tb_tmp_incremento)";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "create table if not exists tb_tmp_concluidos as "
                    + "select * from tb_desconciliacao_djo_paj as t1 "
                    + "where not exists "
                    +   "(select * from tb_temporaria_desconciliacao_djo_paj as t2 "
                    +    "where(t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA) )";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            /*  Todos registros novos são NÃO REGULARIZADOS, 
                verifica se tem o mesmo registro antigo com situação REGULARIZADO e transforma em NÃO REGULARIZADO */
            sql = "create table tb_falso_regularizado as "
                    + "(select t1.NPJ,t1.SITUACAO,t1.CONTA_DEPOSITARIA,t1.VALOR_DESCONCILIACAO,t1.DATA_SITUACAO,t1.DATA_ENTRADA_BD FROM tb_desconciliacao_djo_paj as t1 "
                    + " inner join tb_temporaria_desconciliacao_djo_paj as t2 on t1.NPJ = t2.NPJ AND t1.CONTA_DEPOSITARIA =t2.CONTA_DEPOSITARIA AND t1.SITUACAO = 'REGULARIZADO' AND t1.DATA_SITUACAO < subdate(curdate(),interval 1 day) )";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = " update tb_desconciliacao_djo_paj as t1 "
                    + "set SITUACAO = NULL, NOME_TRATAMENTO = NULL,DATA_SITUACAO = NULL, OBS_LIVRE = 'Este registro estava marcado como regularizado, foi desmarcado automaticamente devido à persistencia da desconciliação' "
                    + "WHERE exists (select * from tb_falso_regularizado as t2 "
                    +               "where t1.NPJ=t2.NPJ and t1.CONTA_DEPOSITARIA=t2.CONTA_DEPOSITARIA "
                    +               "and   t1.SITUACAO ='REGULARIZADO')";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            //Se o registro não estiver na lista dos novos registros, significa que está REGULARIZADO
            sql = "update tb_desconciliacao_djo_paj as t1 "
                    + "set SITUACAO = 'REGULARIZADO', NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA',DATA_SITUACAO = curdate(),OBS_LIVRE = 'Este registro foi anotado como regularizado automaticamente devido nao estar mais na lista de desconciliação do Portal Jurídico',FUNCIONARIO_RESPONSAVEL_SITUACAO = '" + user.getMatricula() + "'  "
                    + "WHERE exists (select * from tb_tmp_concluidos as t2 "
                    +               "where t1.NPJ=t2.NPJ "
                    +               "and   t1.CONTA_DEPOSITARIA=t2.CONTA_DEPOSITARIA "
                    +               "and  (t1.SITUACAO is null or t1.SITUACAO =''))";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            //inclui novo registro no histórico das cargas
            sql = "INSERT INTO tb_serie_historica_desconciliacao_dj_paj"
                    + "(QUANTIDADE,VALOR,DATA,VALOR_ABSOLUTO) "
                    + "(select count(CODIGO) AS QUANTIDADE,sum((VALOR_DESCONCILIACAO) ) AS VALOR, curdate(),SUM(ABS(VALOR_DESCONCILIACAO)) AS VLR_ABSOLUTO from tb_temporaria_desconciliacao_djo_paj)";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "update tb_desconciliacao_djo_paj as t1 set t1.AVOCADO = NULL  where AVOCADO = 'SIM' AND DATA_AVOCACAO < curdate()";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "update tb_desconciliacao_djo_paj set SITUACAO = 'INEDITO TRATADO' where (SITUACAO = 'INEDITO SOLICITADO' AND DATA_SITUACAO < curdate())";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "update tb_desconciliacao_djo_paj as t1 set DIAS_DESCONCILIADO = datediff(curdate(),DATA_ENTRADA_BD) where DATA_PRIMEIRO_TRATAMENTO IS NULL AND DATA_ENTRADA_BD IS NOT NULL";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj AS t1 set DATA_PRIMEIRO_TRATAMENTO = (t1.DATA_SITUACAO)  where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA' AND DATA_PRIMEIRO_TRATAMENTO IS NULL";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj as t1 set DIAS_DESCONCILIADO = datediff(DATA_PRIMEIRO_TRATAMENTO,DATA_ENTRADA_BD) where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj  as t1 set TRATADO_PRAZO = 'SIM' where (DIAS_DESCONCILIADO  <=10 and DIAS_DESCONCILIADO>=0 OR DIAS_DESCONCILIADO IS NULL)  AND (TRATADO_PRAZO IS NULL OR TRATADO_PRAZO = 'NAO') AND DATA_PRIMEIRO_TRATAMENTO IS NOT NULL AND SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            con.commit();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro - Todas as atualizações serão desfeitas, reinicie o procedimento:\n\n" + ex);
            con.rollback();
            
        } finally {
            con.setAutoCommit(true);
            ConnectionFactory.fecharConexao();

        }

    }
   
    
    @SuppressWarnings("null")
    public void atualizacaoOnline() throws SQLException {

        // CallableStatement callableStatement = null;
        Connection con = null;

        try {

            con = ConnectionFactory.conectar(bancoDados);
//             callableStatement = (CallableStatement) con.prepareCall("{CALL atualizar_diario}");
//             callableStatement.execute();

            con.setAutoCommit(false);
            String sql;
            PreparedStatement stmt;

            // CONTABILIZAÇÃO IGUAIS
            sql = "update tb_desconciliacao_djo_paj as t1 "
                + "inner join tb_cpj_cadastro as t2 "
                    + "on t1.NPJ = t2.NPJ "
                    + "and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA "
                    + "and t1.VALOR_DESCONCILIACAO=t2.ID_Valor "
                    + "set SITUACAO = 'REGULARIZADO', "
                    +     "NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA',"
                    +     "DATA_SITUACAO = curdate(),"
                    +     "OBS_LIVRE = 'Este registro foi anotado como regularizado automaticamente devido contabiliação de resgate igual a desconciliação atual',"
                    +     "FUNCIONARIO_RESPONSAVEL_SITUACAO = '" + user.getMatricula() + "' "
                + "where SITUACAO is null "
                + "and t2.OBS_CONTABILIZACAO like '%' 'sucesso' '%'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj AS t1 set DATA_PRIMEIRO_TRATAMENTO = (t1.DATA_SITUACAO)  where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA' AND DATA_PRIMEIRO_TRATAMENTO IS NULL";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj as t1 set DIAS_DESCONCILIADO = datediff(DATA_PRIMEIRO_TRATAMENTO,DATA_ENTRADA_BD) where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj  as t1 set TRATADO_PRAZO = 'SIM' where (DIAS_DESCONCILIADO  <=10 and DIAS_DESCONCILIADO>=0 OR DIAS_DESCONCILIADO IS NULL)  AND (TRATADO_PRAZO IS NULL OR TRATADO_PRAZO = 'NAO') AND DATA_PRIMEIRO_TRATAMENTO IS NOT NULL AND SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            // ATUALIZA DEVOLUÇOES
            sql = "update tb_desconciliacao_djo_paj  as t1 inner join tb_cpj_cadastro as t2 on t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA and t1.VALOR_DESCONCILIACAO=t2.ID_Valor set SITUACAO = 'EM TRATAMENTO', NOME_TRATAMENTO = 'EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA',DATA_SITUACAO = curdate(),OBS_LIVRE = 'ANOTACAO AUTOMATICA',FUNCIONARIO_RESPONSAVEL_SITUACAO = '" + user.getMatricula() + "'  where SITUACAO is null and OBS_DEVOLUCAO like '%' 'sucesso' '%'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj AS t1 set DATA_PRIMEIRO_TRATAMENTO = (t1.DATA_SITUACAO)  where NOME_TRATAMENTO = 'EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA' AND OBS_LIVRE = 'ANOTACAO AUTOMATICA' AND DATA_PRIMEIRO_TRATAMENTO IS NULL";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            sql = "UPDATE tb_desconciliacao_djo_paj  as t1 set TRATADO_PRAZO = 'SIM' where (DIAS_DESCONCILIADO  <=10 and DIAS_DESCONCILIADO>=0 OR DIAS_DESCONCILIADO IS NULL)  AND (TRATADO_PRAZO IS NULL OR TRATADO_PRAZO = 'NAO') AND DATA_PRIMEIRO_TRATAMENTO IS NOT NULL AND NOME_TRATAMENTO = 'EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA' AND OBS_LIVRE = 'ANOTACAO AUTOMATICA'";
            stmt = con.prepareStatement(sql);
            stmt.execute();

            con.commit();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro - Todas as atualizações serão desfeitas, reinicie o procedimento");
            con.rollback();

        } finally {
            con.setAutoCommit(true);
            ConnectionFactory.fecharConexao();

        }

    }

    public List<Desconciliacao> buscarRegistroTratatoAutomatico() throws Exception {

        Desconciliacao desconciliacao = null;
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "SELECT * FROM tb_desconciliacao_djo_paj where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA' AND DATA_SITUACAO  LIKE '%' '" + Utils.getDataAtualFormatoMysql() + "' '%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<Desconciliacao> desconciliacoes = new ArrayList<>();

            while (rs.next()) {

                desconciliacao = new Desconciliacao();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setNpj(rs.getString("NPJ"));
                desconciliacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                desconciliacao.setContaControle(rs.getString("CONTA_CONTROLE"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                desconciliacao.setSaldoContaControle(rs.getDouble("SALDO_CONTA_CONTROLE"));
                desconciliacao.setSaldoDeposito(rs.getDouble("SALDO_DEPOSITO"));
                desconciliacao.setValorDesconciliacao(rs.getDouble("VALOR_DESCONCILIACAO"));
                desconciliacao.setSituacao(rs.getString("SITUACAO"));
                desconciliacao.setDataSituacao(rs.getDate("DATA_SITUACAO"));
                desconciliacao.setFuncionarioResponsavelSituacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SITUACAO"));
                desconciliacao.setFuncionarioAtual(rs.getString("FUNCIONARIO_ATUAL"));
                desconciliacao.setNomeTratamento(rs.getString("NOME_TRATAMENTO"));
                desconciliacao.setAvocado(rs.getString("AVOCADO"));
                desconciliacao.setDataAvocacao(rs.getDate("DATA_AVOCACAO"));
                desconciliacao.setDataDesconciliacao(rs.getDate("DATA_DESCONCILIACAO"));
                desconciliacao.setDiasDesconciliado(rs.getInt("DIAS_DESCONCILIADO"));
                desconciliacao.setDataEntradaBd(rs.getDate("DATA_ENTRADA_BD"));
                desconciliacao.setObsLivre(rs.getString("OBS_LIVRE"));
                desconciliacao.setAutor(rs.getString("AUTOR"));
                desconciliacao.setMateria(rs.getString("MATERIA"));
                desconciliacao.setAssunto(rs.getString("ASSUNTO"));
                desconciliacao.setDataPrimeiroTratamento(rs.getDate("DATA_PRIMEIRO_TRATAMENTO"));
                desconciliacoes.add(desconciliacao);

                salvarHistoricoRegistroTratadoAutomatico(desconciliacao);

            }

            return desconciliacoes;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar dados");
        } finally {
            ConnectionFactory.fecharConexao();
        }
        return null;

    }

       
       private void salvarHistoricoRegistroTratadoAutomatico(Desconciliacao entidade)  {

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar(bancoDados);

            sql = "INSERT INTO tb_historico_desconciliacao_djo_paj (CODIGO_DESCONCILIACAO,NPJ,VARIACAO_NPJ,CONTA_CONTROLE,CONTA_DEPOSITARIA,SALDO_CONTA_CONTROLE,SALDO_DEPOSITO,VALOR_DESCONCILIACAO,SITUACAO,DATA_SITUACAO,FUNCIONARIO_RESPONSAVEL_SITUACAO,NOME_TRATAMENTO,DATA_DESCONCILIACAO,DIAS_DESCONCILIADO,DATA_ENTRADA_BD,MES_TRATAMENTO,CODIGO_FUNCAO,UOR_LOCALIZACAO,CODIGO_TAREFA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, entidade.getCodigoDesconciliacao());
            stmt.setString(2, entidade.getNpj());
            stmt.setInt(3, entidade.getVariacaoNpj());
            stmt.setString(4, entidade.getContaControle());
            stmt.setString(5, entidade.getContaDepositaria());
            stmt.setDouble(6, entidade.getSaldoContaControle());
            stmt.setDouble(7, entidade.getSaldoDeposito());

            stmt.setDouble(8, entidade.getValorDesconciliacao());

            stmt.setString(9, entidade.getSituacao());

            stmt.setDate(10, (Date) entidade.getDataSituacao());

            stmt.setString(11, user.getMatricula());
            stmt.setString(12, entidade.getNomeTratamento());
            stmt.setDate(13, (Date) entidade.getDataDesconciliacao());
            stmt.setInt(14, entidade.getDiasDesconciliado());
            stmt.setDate(15,(Date) entidade.getDataEntradaBd());
            stmt.setString(16,Utils.getMesAtual());
            stmt.setInt(17,user.getCodigoCargo());
            stmt.setInt(18,user.getCodigoUor());
            if((entidade.getValorDesconciliacao()<50000)){
                
                stmt.setInt(19,21444);
            } else{
                stmt.setInt(19,21443);
            }

            stmt.executeUpdate();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null,"Erro ao tentar salvar histórico");

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

    
}
