
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


public class AtualizaHistoricoVoLumetriaDAO<E> {
     Usuario user = new Usuario();
     
     
     
      
      
      
      
       public void buscarRegistroTratatoAutomatico() throws Exception  {
        
           
          if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado");
            return;
        }  
           
           
           
        if(!user.getGrupoUsuario().equals("ADMINISTRADOR")){
            JOptionPane.showMessageDialog(null, "Usuário não é administrdor....rotina não permitida");
            return;
        
        }
           
           
           
         Desconciliacao desconciliacao = null;
        try {
            Connection con = ConnectionFactory.conectar("rejud");
            //sql = "SELECT * FROM rejud.tb_desconciliacao_djo_paj where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA' AND DATA_SITUACAO  LIKE '%' '" + Utils.getDataAtualFormatoMysql() + "' '%'";
            String sql = "SELECT * FROM rejud.tb_desconciliacao_djo_paj where (SITUACAO = 'REGULARIZADO' or SITUACAO='EM TRATAMENTO') AND (NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA' OR (NOME_TRATAMENTO='EM TRATAMENTO - AGUARDANDO RETORNO AGENCIA' AND OBS_LIVRE = 'ANOTACAO AUTOMATICA')) AND DATA_SITUACAO  LIKE '%' '" + Utils.getDataAtualFormatoMysql() + "'  '%'";
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

            
         

        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Erro ao listar dados para gravação de historico e volumetria ");
        } finally {
            ConnectionFactory.fecharConexao();
        }
       

    }
       
       
       private void salvarHistoricoRegistroTratadoAutomatico(Desconciliacao entidade)  {

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud");

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
