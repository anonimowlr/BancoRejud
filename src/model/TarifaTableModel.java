package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entidade.SolicitacaoTarifa;

public class TarifaTableModel extends AbstractTableModel {

    private List<SolicitacaoTarifa> dados = new ArrayList<>();
    private String[] colunas = {"CODIGO", "NPJ", "VARIACAO_NPJ", "VALOR_TARIFA", "VALOR_CREDITO", "DATA_CREDITO", "DEPENDENCIA_CONTABIL", "BANCO_BENEFICIARIO", "ID_SITUACAO", "OBS_INCLUIR_TARIFA_PORTAL", "OBS_ANALISE_LIBERACAO", "OBS_EFETIVAR_TARIFA", "OBS_DESPACHO_TARIFA", "FUNCIONARIO_RESPONSAVEL", "DATA_SOLICITACAO","OBS_PARTIDA_EGT","NUMERO_EGT","ID_SITUACAO_EGT","DATA_DESPACHO_PORTAL"};

    @Override
    public String getColumnName(int colunm) {
        return colunas[colunm];
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {

            case 0:
                return dados.get(linha).getCodigo();

            case 1:
                return dados.get(linha).getNpj();

            case 2:
                return dados.get(linha).getVariacaoNpj();

            case 3:
                return dados.get(linha).getValorTarifa();

            case 4:
                return dados.get(linha).getValorCredito();

            case 5:
                return dados.get(linha).getDataCredito();

            case 6:
                return dados.get(linha).getDependenciaContabil();
            case 7:
                return dados.get(linha).getBancoBeneficiario();
            case 8:
                return dados.get(linha).getIdSituacao();
           
            case 9:
                return dados.get(linha).getObsIncluirTarifaPortal();
            case 10:
                return dados.get(linha).getObsAnaliseLiberacao();
            case 11:
                return dados.get(linha).getObsEfetivarTarifa();
            case 12:
                return dados.get(linha).getObsDespachoTarifa();
            case 13:
                return dados.get(linha).getFuncionarioResponsavel();
            case 14:
                return dados.get(linha).getDataSolicitacao();
            case 15:
                return dados.get(linha).getObsPartidaEgt();
             case 16:
                return dados.get(linha).getNumeroEgt();
             case 17:
                return dados.get(linha).getIdSituacaoEgt();
             case 18:
                return dados.get(linha).getDataDespachoPortal();   
                

        }
        return null;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void addRow(SolicitacaoTarifa t) {

        dados.add(t);
        fireTableDataChanged();

    }

    public void removeRow(int linha) {
        dados.remove(linha);
        fireTableRowsDeleted(linha, linha);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return true;

    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {

        switch (coluna) {

            case 1:
                dados.get(linha).setNpj((String) valor);
                break;
                
//                 case 3:
//                dados.get(linha).setValorTarifa(Double.parseDouble((String) valor));   / / Valor da tarifa desabilitado para edição por motivo de  segurança
//                break;
                
                
                 case 2:
                dados.get(linha).setVariacaoNpj(Integer.parseInt((String) valor));
                break;
                
                
                case 4:
                dados.get(linha).setValorCredito(Double.parseDouble((String) valor));
                break;
                
                 case 5:
                dados.get(linha).setDataCredito(Date.valueOf((String) valor));
                break;
                
                
                  case 6:
                dados.get(linha).setDependenciaContabil(Integer.parseInt((String) valor)); // Valor da tarifa desabilitado para edição por motivo de  segurança
                break;

           
                 case 7:
                dados.get(linha).setBancoBeneficiario((String) valor);
                break;
                 case 8:
                dados.get(linha).setIdSituacao((String) valor);
                break;
                case 9:
                dados.get(linha).setObsIncluirTarifaPortal((String) valor);
                break;
                case 10:
                dados.get(linha).setObsAnaliseLiberacao((String) valor);
                break;
                case 11:
                dados.get(linha).setObsEfetivarTarifa((String) valor);
                break;
                case 12:
                dados.get(linha).setObsDespachoTarifa((String) valor);
                break;
                case 18:
                dados.get(linha).setDataDespachoPortal((String) valor);
                break;
                
                
                

        }

        fireTableRowsUpdated(linha, linha);

    }

}
