package util;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import entidade.Usuario;
import view.FormPrincipal;

public class Utils {

    public static int convertStringToInt(String v) {

        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return Integer.parseInt(numeroTratado);

    }

    Usuario user = new Usuario();

    private static String lastDir = null;

    public static String separaAgenciaDeConta(String contaDepositaria) {

        String numeroTratado = "";

        int tamanhoValorRecebido = contaDepositaria.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = contaDepositaria.subSequence(i, i + 1).toString();

            if (num.equals("/")) {

                numeroTratado = "";
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;

    }

    public static JFileChooser getFileChooser() {
        if (lastDir != null) {
            JFileChooser fc = new JFileChooser(lastDir);
            

            return fc;
        } else {
            JFileChooser fc = new JFileChooser("G://PUBLICA//");
            return fc;
        }
    }

    
    public static void setLastDir(File file) {
        lastDir = file.getParent();
    }

    
    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    
    public static java.sql.Date getDataAtualFormatoMysql() throws Exception {

        String data = getDataAtual();
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }
    
    /**
     * Data atual do sistema em formato mySQL yyyy-MM-dd
     * 
     * @return date yyyy-MM-dd
     * @throws Exception 
     */
    public static java.sql.Date getDataAtualFormatoYMD() throws Exception {

        String data = getDataAtual();
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    
    /**
     * Retorna a data atual do sistema formato MySQL(yyyy-MM-dd)
     * 
     * @return String data atual formato yyyy-MM-dd
     * @author f4281065
     */
    public static String getDataAtualYMD() {                    

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        return (formatarDate.format(data));

    }
    
    
    public static String getDataAtual() {

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
        return (formatarDate.format(data));

    }

        
    public  static String getAnoAtual(){
        
       String dataAtualTexto = getDataAtual();
       String ano  = (String) dataAtualTexto.subSequence(6,10);
    
       return ano;
    }
     
    
     public  static String getDiaAtual(){
        
       String dataAtualTexto = getDataAtual();
       String dia = (String) dataAtualTexto.subSequence(0, 2);
    
       return dia;
    }
    
      
     public  static String getMesAtual(){
        
       String dataAtualTexto = getDataAtual();
       String mes = (String) dataAtualTexto.subSequence(3, 5);
    
       return mes;
    }
    
    
    public  static String formatDataTexto(String data){ // recebe data em texto no formafo yyyy - MM - dd e o converte para texto no formato dd/mm/yyyy
        
        String ano = (String) data.subSequence(0,4);
        String mes = (String) data.subSequence(5, 7);
        String dia = (String) data.subSequence(8, 10);
        
        data = dia + "/" + mes + "/" + ano;
        return data;
    
    }
    
    
    public static void setPropriedades() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    public static Double converteParaDouble(String v) {
        
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        Double n = Double.parseDouble(numeroTratado);

        return n;
        
    }

    
    public static String tratarVariavel(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    
    }

       
    public static String tratarNpjColetaSequencial(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length() - 4;

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals("/")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

          
     public  static String format(String data){ // 01.06.2017
        
        String dia = (String) data.subSequence(0,2);
        String mes = (String) data.subSequence(3, 5);
        String ano = (String) data.subSequence(6, 10);
        
        data = ano + "/" + mes + "/" + dia;
        return data;
    
    } 
    
    public static BigDecimal converterDobleParaBigDecimal(Double valor) {

        String v = Double.toString(valor);
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && (i == tamanhoValorRecebido - 3 || i == tamanhoValorRecebido - 2)) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        BigDecimal n = new BigDecimal(numeroTratado);
        n = n.setScale(2, BigDecimal.ROUND_HALF_UP);
        return n;
    }

    public static BigDecimal converterDobleParaBigDecimal(String v) {

        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && (i == tamanhoValorRecebido - 3 || i == tamanhoValorRecebido - 2)) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        BigDecimal n = new BigDecimal(numeroTratado);
        n = n.setScale(2, BigDecimal.ROUND_HALF_UP);
        return n;
    }

  
    public static java.sql.Date getDataDesconciliacaoFormatoMysql(Date d) throws Exception {

        SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
        String data = (formatarDate.format(d));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        return date;
        
    }

    
    /**
     * Altera o formato do String data de yyyy-MM-dd para ddMMyyyy
     * 
     * @param data formato yyyy-MM-dd
     * @return data formato ddMMyyyy
     */
    public static String formataDataDMY(String data) {
        
        String ano = (String) data.subSequence(0,4);
        String mes = (String) data.subSequence(5, 7);
        String dia = (String) data.subSequence(8, 10);
        
        data = dia + mes + ano;
        return data;
        
    }
    
    
    
    public static String getDataHoraAtualMysql() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(d);
    }
   
    
    public static Double converterStringParaDouble(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return Double.parseDouble(numeroTratado);
    }

    
    public static String tratarVariavel(Double v) {
        String numeroTratado = "";

        String w = Double.toString(v);

        int tamanhoValorRecebido = w.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = w.subSequence(i, i + 1).toString();

            if (num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;
            }

            if (num.equals(".") || num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;

            }

            if (num.equals(",")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    /**
     * Transforma um double em String
     * 
     * @param v valor double
     * @return valor String
     */
    public static String converterDoubleToStr(Double v) {
        Long numero = v.longValue();        
        return numero.toString();       
    }
   
    /**
     * 
     * @param v
     * @return 
     */
    public static String converterParteInteiraDouble(Double v) {
        String numeroTratado = "";

        String w = Double.toString(v);

        int tamanhoValorRecebido = w.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = w.subSequence(i, i + 1).toString();

            if (num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;
            }

            if (num.equals(".") || num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;

            }

            if (num.equals(",")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }


    public static Date converterNumeroEmData(Long numeroTipoLong) {

        long time1 = numeroTipoLong;
        Date date = new Date(time1);

        return date;

    }

    
    public static Long converterStringEmLong(String numeroTexto) {
        Long numeroRetornado = Long.parseLong(numeroTexto);

        return numeroRetornado;

    }
    
    
     public static String tratarContaDepositaria(Double v) {
        
        Long numero = v.longValue();
        System.out.println(numero);
        
        return numero.toString();
    }
    
    
    public static BigDecimal converterStringParaBigDecimal(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        BigDecimal numeroConvertido = new BigDecimal(numeroTratado);

        return numeroConvertido;
    }

        
    /**
     * Converte uma data String(ano-mes-dia) para um objeto Date em formato MySql(DATE)
     *
     * @param data String no formato yyyy-MM-dd a ser formatada
     * @return Date Objeto Date MySQL(DATE) ou null caso receba uma String vazia ou nula
     * @throws Exception - Caso a String esteja no formato errado
     */
    public java.sql.Date formataStrToDate(String data) throws Exception { 
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }
    
    /**
     * Formata data(tipo Date) capturada SQL para String yyyy-MM-dd
     * 
     * @param data captura SQL
     * @return String formato yyyy-MM-dd
     * @throws Exception 
     */
    public static String formataDateSQL(Date data) throws Exception {
        if (data == null) {
            return null;
        }

        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormat = fd.format(data);

        return dataFormat;
    }

    /**
     * Divide um ArrayList em várias subListas iguais.
     * 
     * @param <T> Objeto da lista
     * @param lista Lista a ser dividada
     * @param iPartes Quantidade de partes a ser dividida
     * @return Uma nova lista da lista
     */
    public static <T> List<List<T>> dividirListaPartes(final List<T> lista, final int iPartes) {
        
        final List<List<T>> listaPartes = new ArrayList<>();
        final int iTamanhoPedaço = lista.size() / iPartes;
        int iLeftOver = lista.size() % iPartes;
        @SuppressWarnings("UnusedAssignment")
        int iTake = iTamanhoPedaço;

        for (int i = 0, iT = lista.size(); i < iT; i += iTake) {
            if (iLeftOver > 0) {
                iLeftOver--;

                iTake = iTamanhoPedaço + 1;
            } else {
                iTake = iTamanhoPedaço;
            }

            listaPartes.add(new ArrayList<>(lista.subList(i, Math.min(iT, i + iTake))));
        }

        return listaPartes;
    }

    
    
     public static String tratarNpj(Double v) {
        Long numero = v.longValue();
        System.out.println(numero);
        
        return numero.toString();
       
    }
    
}
