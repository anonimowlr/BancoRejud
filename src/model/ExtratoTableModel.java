package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entidade.Extrato;
import entidade.SolicitacaoLevantamento;

public class ExtratoTableModel extends AbstractTableModel {

    private List<Extrato> dados = new ArrayList<>();
    private String[] colunas = {"CODIGO", "NPJ","AGENCIA", "CONTA", "DV", "MODALIDADE_DEPOSITO","observação"};

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
                return dados.get(linha).getAgenciaDdepositaria();

            case 3:
                return dados.get(linha).getContaDepositaria();

            case 4:
                return dados.get(linha).getVariacaoContaDepositaria();

            case 5:
                return dados.get(linha).getModalidadeDeposito();
                
            case 6:
                return dados.get(linha).getObs();

           

        }
        return null;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void addRow(Extrato s) {

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
                dados.get(linha).setContaDepositaria((String) valor);
                break;
          

        }

        fireTableRowsUpdated(linha, linha);

    }

}
