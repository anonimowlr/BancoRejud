package rotinasPortal;

import util.Utils;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import org.openqa.selenium.support.ui.WebDriverWait;
import static rotinasPortal.Coletas.driver;

public class TratarIneditoPortal {
     int marcador;
    static WebDriverWait wait = null;

    static String e;
    static String nomeElemento;
    static String valorElemento;

    Coletas coletas = new Coletas();

    public TratarIneditoPortal() {
    }

    public TratarIneditoPortal(int marcador) {
        this.marcador = marcador;
    }
    
    

    public String tratarIneditoPortal(BigDecimal valorResgate, String contaDepositaria, String numeroOficio, String agenciaPagadora) {
        String msgfim = null;
        String valorResgatePortal = null;
       
        if ( marcador == 0) {
            coletas.autenticarUsuario();
            marcador = 1;
           
        }

        coletas.setURL("https://juridico.intranet.bb.com.br/paj/deposito/lancamento/tratar");

        coletas.selecionarElementoID("tratarLancamentoForm:situacaoDecorate:situacaoListMenu", 0);

        coletas.procuraElementoPorId(driver, "tratarLancamentoForm:numeroContaDecorate:numeroContaInput", contaDepositaria);

        coletas.clickElementId(driver, "tratarLancamentoForm:btPesquisar");

        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {

                msgfim = n;
            }
        }

        // ler gegistros na pagina
        Integer qtdreg = null;
        if (coletas.isElementPresentID(driver, "tratarLancamentoForm:j_id280")) {

            String n = coletas.lerValorElementoID("tratarLancamentoForm:j_id280");

            qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));
        } else {

            msgfim = "Nenhum registro encontrado";
            return msgfim;
        }

//        if (qtdreg > 9) {
//            
//            coletas.clickElementXpath(driver, ".//*[@id='tratarLancamentoForm:dataTabletableLancamentos:ds_table']/tbody/tr/td[6]");
//            msgfim = "Mais de 9 registros, desenvolva o método";
//            //JOptionPane.showMessageDialog(null, msgfim);
//            return msgfim;
//
//        }

        for (int reg = 0; reg <= qtdreg; reg++) {
            
            if(reg==10 || reg ==20 || reg == 30 || reg == 40 || reg == 50 || reg == 60 || reg == 70 || reg == 80){
               
                coletas.clickElementXpath(driver, ".//*[@id='tratarLancamentoForm:dataTabletableLancamentos:ds_table']/tbody/tr/td[6]");
          
                coletas.pausar(2000);
            
            }
           
           
            
             if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        msgfim = n;
                        
                        return msgfim;
                    }
                }
            
            
           
            if (reg == qtdreg) {

                msgfim = "Não localizado no portal Jurídico registro apto a ser tratado ou já tratado";
                return msgfim;

            }

            valorResgatePortal = coletas.lerValorElementoID("tratarLancamentoForm:dataTabletableLancamentos:" + reg + ":j_id345");
            String situacao = coletas.lerValorElementoID("tratarLancamentoForm:dataTabletableLancamentos:" + reg + ":situacaoLancamentoDecorate:situacaoLancamentoOutput");
            situacao = coletas.lerValorElementoID("tratarLancamentoForm:dataTabletableLancamentos:" + reg + ":situacaoLancamentoDecorate:situacaoLancamentoOutput");
            String tipo = coletas.lerValorElementoID("tratarLancamentoForm:dataTabletableLancamentos:" + reg + ":tipoLancamentoDecorate:tipoLancamentoOutput");
            BigDecimal valorResgatePortalConvertido = Utils.converterDobleParaBigDecimal(valorResgatePortal);

            if (valorResgatePortalConvertido.compareTo(valorResgate) == 0 && situacao.equals("Lançamento inédito")) {

                coletas.clickElementName(driver, "tratarLancamentoForm:dataTabletableLancamentos:" + reg + ":j_id391");

                if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        msgfim = n;
                        
                        return msgfim;
                    }
                }

                coletas.aguardaElementoTelaByID("incluirLevantamentoForm:btSalvarInclusao");
                coletas.clickElementId(driver, "incluirLevantamentoForm:pesquisarDependenciaIsIneditoDecorate:j_id1151");
                coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:prefixoDecorate:prefixoInput");
                coletas.procuraElementoPorId(driver, "includePopup:pesquisarDependenciaForm:prefixoDecorate:prefixoInput", agenciaPagadora);
                coletas.clickElementId(driver, "includePopup:pesquisarDependenciaForm:pesquisarDependencia");
                coletas.aguardaElementoTelaByID("includePopup:pesquisarDependenciaForm:dataTablePopupUnidadesOrganizacionais:0:selecionar");
                coletas.clickElementId(driver, "includePopup:pesquisarDependenciaForm:dataTablePopupUnidadesOrganizacionais:0:selecionar");
                coletas.aguardaElementoTelaByID("incluirLevantamentoForm:dtValidadeOrdemDecorate:dtValidadeOrdemInputInputDate");
                coletas.pausar(1000);
                
                String numeroOficioPortal = coletas.lerValorElementoID("incluirLevantamentoForm:numOficioDecorate:j_id1111");
                
                coletas.clickElementId(driver, "incluirLevantamentoForm:dtValidadeOrdemDecorate:dtValidadeOrdemInputInputDate");
                
                String validadeOficio = coletas.validadeOficio();
                coletas.pausar(1000);
                coletas.procuraElementoPorId(driver, "incluirLevantamentoForm:dtValidadeOrdemDecorate:dtValidadeOrdemInputInputDate", validadeOficio);
                
                coletas.procuraElementoPorName(driver, "incluirLevantamentoForm:ObservacaoDecorate:ObservacaoInput", "Tratamento de Lançamento inédito cfe IN 869-2 item 4.1.2");

                
                coletas.clickElementId(driver, "incluirLevantamentoForm:btSalvarInclusao");
                coletas.pausar(1000);
                driver.switchTo().alert().accept();
                
                
                
               
                
                 coletas.pausar(1000);
                 if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

                    String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

                    if (!n.equals("")) {

                        msgfim = n;
                        return msgfim;
                    }
                }
                
                
                
                
            }

        }

        return msgfim;

    }

}
