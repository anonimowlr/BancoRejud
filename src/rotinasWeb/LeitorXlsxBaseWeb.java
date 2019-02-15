/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotinasWeb;

import dao.DesconciliacaoDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import entidade.Desconciliacao;
import util.Utils;
import view.FormProgresso;

public class LeitorXlsxBaseWeb extends Thread {

    private final String bancoDados;
    private int qtdRegistros;

    /**
     * Construtor iniciado para executar procedimentos de carga para Banco de
     * Dados selecionado.
     *
     * @param bancoDados
     */
    public LeitorXlsxBaseWeb(String bancoDados) {
        this.bancoDados = bancoDados;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LeitorXlsxBaseWeb other = (LeitorXlsxBaseWeb) obj;
        if (!Objects.equals(this.bancoDados, other.bancoDados)) {
            return false;
        }
        return true;
    }

    /**
     * Início do procedimento de leitura do arquivo excel
     *
     * @param arq arquivo selecionado
     * @param txtArea
     */
    public void lerXlsx(String arq, JLabel txtArea) {
        File file = new File(arq);
        FileInputStream fisPlanilha;

        try {

            fisPlanilha = new FileInputStream(file);
            //  cria um workbook , planilha com todas as abas
            XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
            // recuperar apenas a primeira aba
            XSSFSheet sheet = workbook.getSheetAt(0);
            //retorna todas as linhas da planilha 0
            Iterator<Row> rowIterator = sheet.iterator();

            varrerPlanilha(rowIterator, txtArea);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LeitorXlsxBaseWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeitorXlsxBaseWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LeitorXlsxBaseWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Grava os dados das linhas e colunas do arquivo REJUD ou RETAB do excel
     *
     * @param rowIterator leitura das linhas
     * @param prefixo arquivo REJUD ou RETAB
     * @throws Exception
     */
    private void varrerPlanilha(Iterator<Row> rowIterator, JLabel txtArea) throws Exception {

        DesconciliacaoDAO desconciliacaoDAO = new DesconciliacaoDAO("rejud");

        //varre todas as linhas da planilha
        int i = 0;
        while (rowIterator.hasNext()) {

            int numeroLinha = i;
            txtArea.setText("Linha sendo carregada: " + numeroLinha + " de " + qtdRegistros);

            // recebe cada linha da planilha
            Row row = rowIterator.next();
            //pega todas as celulas da linha
            Iterator<Cell> cellIterator = row.iterator();
            Desconciliacao desconciliacao = new Desconciliacao();
            //varremos todas as celulas da linha atual
            int j = 1;
            while (cellIterator.hasNext()) {
                //criamos uma celula
                Cell cell = cellIterator.next();
                if (numeroLinha > 0) {
                  
                    switchColunas(desconciliacao, cell, j);
                    
//                    if (bancoDados.equals("retab")) {
//                        switchRetab(desconciliacao, cell, j);
//                    }

                }
                j++;
            }
            i++;

            if (numeroLinha > 0) {
                desconciliacaoDAO.inserirRegistroNovoTbTemporaria(desconciliacao, "tb_temporaria_desconciliacao_djo_paj");
            }

        }

    }

    /**
     * Leitura celula XLSX
     *
     * @param desconciliacao Objeto desconciliação
     * @param cell Celula do excel
     * @param j Coluna a ser lido
     * @throws Exception
     */
    public void switchColunas(Desconciliacao desconciliacao, Cell cell, int j) throws Exception {

        switch (j) {
            case 1: //npj
                desconciliacao.setNpj(Utils.converterDoubleToStr(cell.getNumericCellValue()));
                break;
            case 2: //variação
                desconciliacao.setVariacaoNpj((int) cell.getNumericCellValue());
                break;
            case 3: //Razão social
                desconciliacao.setAutor(cell.getStringCellValue());
                break;
            case 4: //Conta depositaria
                desconciliacao.setContaDepositaria(Utils.converterDoubleToStr(cell.getNumericCellValue()));
                break;
            case 5: //Valor diferença
                desconciliacao.setValorDesconciliacao(cell.getNumericCellValue());
                break;
            case 6: //Data diferença
                desconciliacao.setDataDesconciliacao((Utils.getDataDesconciliacaoFormatoMysql(cell.getDateCellValue())));
                break;
        }

    }

    /**
     * Leitura celula XLSX para arquivos RETAB
     *
     * @param desconciliacao Objeto desconciliação
     * @param cell Celula do excel
     * @param j Coluna a ser lido
     * @throws Exception
     */
//    private void switchRetab(Desconciliacao desconciliacao, Cell cell, int j) throws Exception {
//
//        switch (j) {
//            case 1: //chave
//                break;
//            case 2: //npj
//                desconciliacao.setNpj(Utils.converterDoubleToStr(cell.getNumericCellValue()));
//                break;
//            case 3: //var npj
//                desconciliacao.setVariacaoNpj((int) cell.getNumericCellValue());
//                break;
//            case 4: //razao social
//                desconciliacao.setAutor(cell.getStringCellValue());
//                break;
//            case 5: //conta depositaria
//                desconciliacao.setContaDepositaria(Utils.converterDoubleToStr(cell.getNumericCellValue()));
//                break;
//            case 6: //valor diferença
//                desconciliacao.setValorDesconciliacao(cell.getNumericCellValue());
//                break;
//            case 7: //data diferença
//                desconciliacao.setDataDesconciliacao((Utils.getDataDesconciliacaoFormatoMysql(cell.getDateCellValue())));
//                break;
//        }
//
//    }

    /**
     * Verifica quantidade de linhas da planilha do excel
     *
     * @param arquivo arquivo selecionado .xlsx
     * @return quantidade de linhas
     */
    public int contarRegistros(String arquivo) {

        File file = new File(arquivo);
        FileInputStream fisPlanilha;

        try {

            fisPlanilha = new FileInputStream(file);
            //  cria um workbook , planilha com todas as abas
            XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
            // recuperar apenas a primeira aba
            XSSFSheet sheet = workbook.getSheetAt(0);
            //retorna todas as linhas da planilha 0

            int primeiroRegistro = sheet.getFirstRowNum();
            int ultimoRegistro = sheet.getLastRowNum();
            qtdRegistros = ultimoRegistro - primeiroRegistro;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormProgresso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormProgresso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qtdRegistros;

    }

}
