package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entidade.SolicitacaoLevantamento;

public class LevantamentoTableModel extends AbstractTableModel {

    private List<SolicitacaoLevantamento> dados = new ArrayList<>();
    private String[] colunas = {"CODIGO", "NPJ", "VARIACAO_NPJ", "VALOR DA SOLICITAÇÃO", "SALDO", "OFICIO", "DATA_OFICIO", "RESULTADO_INCLUSAO_PORTAL", "RESULTADO_CONTABILIZAÇÃO", "OBS_DESPACHO", "RESULTADO_INCLUSAO_BENEFICIARIO", "ENDEREÇO_DOCUMENTO1", "ENDEREÇO_DOCUMENTO2", "beneficiario", "CPF_BENEFICIARIO", "DATA_SOLICITAÇÃO", "DATA_INCLUSAO", "RESPONSAVEL_INCLUIR_PORTAL","TIPO_LEVANTAMENTO","LEVANTADOR", "FINALIDADE", "ESPECIFICAÇÃO","Situação", "Responsavel Solicitação"};

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
                return dados.get(linha).getValorSolicitacao();

            case 4:
                return dados.get(linha).getSaldo();

            case 5:
                return dados.get(linha).getOficio();

            case 6:
                return dados.get(linha).getDataOficio();

            case 7:
                return dados.get(linha).getObs();

            case 8:
                return dados.get(linha).getObsContabillizacao();
            case 9:
                return dados.get(linha).getObsDespacho();
            case 10:
                return dados.get(linha).getObsIncluirParte();
            case 11:
                return dados.get(linha).getEnderecoDocumento1();
            case 12:
                return dados.get(linha).getEnderecoDocumento2();
            case 13:
                return dados.get(linha).getBeneficiario();
            case 14:
                return dados.get(linha).getCpfBeneficiario();
            case 15:
                return dados.get(linha).getDataSolicitacao();
            case 16:
                return dados.get(linha).getDataInclusao();
            case 17:
                return dados.get(linha).getResponsavelInclusao();
                 case 18:
                return dados.get(linha).getTipoLevantamento();
                 case 19:
                return dados.get(linha).getLevantador();
                 case 20:
                return dados.get(linha).getFinalidade();
                 case 21:
                return dados.get(linha).getEspecificacao();
                 case 22:
                return dados.get(linha).getIdSituacao();
                 case 23:
                return dados.get(linha).getResponsavelSolicitacaoInclusao();

        }
        return null;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void addRow(SolicitacaoLevantamento s) {

        dados.add(s);
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

            case 2:
                dados.get(linha).setVariacaoNpj(Integer.parseInt((String) valor));
                break;
            case 3:
                dados.get(linha).setValorSolicitacao((String) valor);

                break;
            case 4:
                dados.get(linha).setSaldo((String) valor);
                break;

            case 5:
                dados.get(linha).setOficio((String) valor);
                break;

            case 6:
                dados.get(linha).setDataOficio((String) valor);
                break;
            case 7:
                dados.get(linha).setObs((String) valor);
                break;
            case 8:
                dados.get(linha).setObsContabillizacao((String) valor);
                break;
            case 9:
                dados.get(linha).setObsDespacho((String) valor);
                break;
            case 10:
                dados.get(linha).setObsIncluirParte((String) valor);
                break;
            case 13:
                dados.get(linha).setBeneficiario((String) valor);
                break;
            case 14:
                dados.get(linha).setCpfBeneficiario((String) valor);
                break;
                 case 18:
                dados.get(linha).setTipoLevantamento((String) valor);
                break;
                 case 19:
                dados.get(linha).setLevantador((String) valor);
                break;
                 case 20:
                dados.get(linha).setFinalidade((String) valor);
                break;
                 case 21:
                dados.get(linha).setEspecificacao((String) valor);
                break;

        }

        fireTableRowsUpdated(linha, linha);

    }

}
