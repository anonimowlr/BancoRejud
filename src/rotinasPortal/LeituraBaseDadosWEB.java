package rotinasPortal;

import util.Utils;
import dao.DesconciliacaoDAO;
import java.util.List;

import javax.swing.JOptionPane;
import entidade.Desconciliacao;
import entidade.Usuario;
import static rotinasPortal.Coletas.driver;
import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.valorElemento;

public class LeituraBaseDadosWEB extends Thread {
    
     Integer qtdreg = 0;

    List<Desconciliacao> lista = null;
    Usuario user = new Usuario();
    DesconciliacaoDAO<Desconciliacao> desconciliacaoDAO = new DesconciliacaoDAO<>("rejud");

    Coletas coletas = new Coletas();

    public LeituraBaseDadosWEB() {
    }

    @Override
    public void run() {

        if (user.getMatricula() == null) {
            JOptionPane.showMessageDialog(null, "Sem usuário logado");
            this.stop();
        }

        createList();
        lerLista();
        coletas.setSize();
        JOptionPane.showMessageDialog(null, "fim de rotina");
        coletas.encerraPortal(driver);

    }

    public void createList() {

        lista = desconciliacaoDAO.buscar();
        if (lista.size() < 1) {
            JOptionPane.showMessageDialog(null, "Não há registro para leitura");
        } else {
            coletas.autenticarUsuario();
        }

    }

    public void lerLista() {

        int n = 0;
        int qtdreg = lista.size();

        for (Desconciliacao s : lista) {

            Integer codigo = s.getCodigoDesconciliacao();
            String npj = s.getNpj();

            Integer variacaoNpj = s.getVariacaoNpj();
            String contaDepositaria = s.getContaDepositaria();
            String tratamento = s.getNomeTratamento();

            coletaDadosPortal(codigo, npj, variacaoNpj, contaDepositaria);
            coletaDadosNpj(codigo, npj, variacaoNpj, contaDepositaria);
            n++;

        }

    }

    private void coletaDadosPortal(Integer codigo, String npj, Integer variacaoNpj, String contaDepositaria) {

        
      
        
        if (contaDepositaria == null) {

            //desconciliacaoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "conta invalida", codigo);
            return;

        }

        
        driver.manage().window().maximize();
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/deposito/pesquisar");
        coletas.aguardaElementoTelaByID("pesquisarDepositoForm:linkMostrarPesquisa");
        coletas.clickElementId(driver, "pesquisarDepositoForm:linkMostrarPesquisa");

        
        
        
            nomeElemento = "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput";
            
            
            coletas.clickElementId(driver, "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoInput");
            valorElemento = npj.subSequence(0, 4).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

            nomeElemento = "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput";
            coletas.clickElementId(driver, "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoNumeroProcessoInput");
            int tamanhoNpj = coletas.calculaTamanhoValorElemento(npj);
            
            
            
           valorElemento =npj.subSequence(4, tamanhoNpj).toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);
            //coletas.pausar(1000);

            nomeElemento = "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput";
            coletas.clickElementId(driver, "pesquisarDepositoForm:bbjurDecorate:bbjurAnoDecorate:bbjurAnoVariacaoInput");
            valorElemento = variacaoNpj.toString();
            coletas.aguardaElementoTelaByName(nomeElemento);
            coletas.procuraElementoPorId(driver, nomeElemento, valorElemento);

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        coletas.clickElementId(driver, "pesquisarDepositoForm:btPesquisar");

        if (coletas.isElementPresentID(driver, "modaldivMessagesGlobal")) {

            String n = coletas.lerValorElementoID("modaldivMessagesGlobal");

            if (!n.equals("")) {

                
                return;
            }
        }

        // ler gegistros na pagina
        Integer qtdreg = null;
        if (coletas.isElementPresentID(driver, "pesquisarDepositoForm:j_id630")) {

            String n = coletas.lerValorElementoID("pesquisarDepositoForm:j_id630");

            qtdreg = Integer.parseInt(coletas.quantidadeRegistrosListados(n));
        } else {

            return;
        }


        for (int reg = 0; reg <= qtdreg; reg++) {

            
            
            
                if (reg == 10 || reg == 20 || reg == 30 || reg == 40 || reg == 50 || reg == 50 ){
                    
                   coletas.clickElementXpath(driver, ".//*[@id='pesquisarDepositoForm:dataTabletableDepositoConglomerado:ds_table']/tbody/tr/td[6]");
                    
                }   
            
            
            
            
            
            
            
            if (reg == qtdreg) {

               // desconciliacaoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "nao localizada a conta para complementar", codigo);
                return;

            }

            String numeroContaPortal = coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":j_id689");

            numeroContaPortal = Utils.separaAgenciaDeConta(numeroContaPortal);

            String saldoPortal = coletas.lerValorElementoID("pesquisarDepositoForm:dataTabletableDepositoConglomerado:" + reg + ":j_id731");

            if (numeroContaPortal.equals(contaDepositaria)) {

                saldoPortal = Utils.tratarVariavel(saldoPortal);
                desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "SALDO_CONTA_CONTROLE", saldoPortal, codigo);

                return;

               

            }


            
            
            
            
            
          

          

        }

    }

    
    
    
    
    
    
    
     private void coletaDadosNpj(Integer codigo, String npj, Integer variacaoNpj, String contaDepositaria) {

        
      
        
        if (contaDepositaria == null) {

            //desconciliacaoDAO.editarGenerico("tb_inedito", "OBS_COMPLEMENTAR", "conta invalida", codigo);
            return;

        }

        
        driver.manage().window().maximize();
        coletas.setURL("https://juridico.intranet.bb.com.br/paj/paginas/negocio/processo/consultar/relatorioCompleto.seam?idProcessoPrincipal=" + npj + "");
       

        coletas.aguardaElementoTelaByID("detalharProcessoForm:j_id202");
       // coletas.pausar(1000);
        coletas.clickElementId(driver, "detalharProcessoForm:j_id202");
        //coletas.pausar(1000);
        
        coletas.aguardaElementoTelaByID("detalharProcessoForm:reuDecorate:reuOutput");
       
       if(!coletas.isElementPresentID(driver, "detalharProcessoForm:assuntoDecorate:assuntoOutput")){
           
           return;
       }
      
        
       String assunto =  coletas.lerValorElementoID("detalharProcessoForm:assuntoDecorate:assuntoOutput");
       
       
      
       
       String materia =  coletas.lerValorElementoID("detalharProcessoForm:materiaDecorate:materiaOutput");
       
      desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "ASSUNTO", assunto, codigo);
      desconciliacaoDAO.editarGenerico("tb_desconciliacao_djo_paj", "MATERIA", materia, codigo);
      
      
        
            

      
          

        

    
    
    
    
    
    
    
     }
    
    
    
     
     

     
     
     
     
     
     
    
    
    
}