
package rotinasPortal;

import static rotinasPortal.Coletas.driver;
import static rotinasPortal.Coletas.nomeElemento;
import static rotinasPortal.Coletas.wait;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AcessoOutrosBancos {
    
     Coletas coletas = new Coletas();
    
    public void logarCef(){
        
       
        String usuario="LUIZGBUR17";
        String senha="lljm2807";
        
System.setProperty("webdriver.firefox.marionette", "");
driver=new FirefoxDriver();
wait =new WebDriverWait(driver, 3000);
driver.get("https://depositojudicial.caixa.gov.br/sigsj_internet/Autenticacao");   
    
Boolean b =coletas.isElementPresentXpath(driver, "/html/body/div[@id='global']/div[@id='content']/div[@id='main']/form[@id='form0']/div/div[2]/div[1]/div/ul/li[1]/input[@id='usuarioInternet']");
    
nomeElemento= "usuarioInternet";
coletas.aguardaElementoTelaByID(nomeElemento);
coletas.procuraElementoPorId(driver, nomeElemento, usuario);

  
  
 nomeElemento="senhaInternet";
 coletas.aguardaElementoTelaByID(nomeElemento);
 coletas.procuraElementoPorId(driver, nomeElemento, senha);
 
 nomeElemento="//*[@id='imagem']/input";
 coletas.clickElementXpath(driver, nomeElemento);
  
 
          
  
 
    }
    
    
    
    public void logarBanrisul(){
        
        
                     Coletas coletas = new Coletas();
                    String usuario="001";
                    String senha="BBrasil";
        
        
                    System.setProperty("webdriver.firefox.marionette", "");
                    driver=new FirefoxDriver();
                    wait =new WebDriverWait(driver, 3000);
                    driver.get("https://www.banrisul.com.br/bbz/link/djr/msie/v50/bbzp11hw.htm"); 
                   
                    
                    
                    
                    
                    
                    
                    
                    
                    
                       
    }
    
    
   

    
      public void logarPortalRejud(){
        
        
                     Coletas coletas = new Coletas();
                    String usuario="001";
                    String senha="BBrasil";
        
        
                    System.setProperty("webdriver.firefox.marionette", "");
                    driver=new FirefoxDriver();
                    wait =new WebDriverWait(driver, 3000);
                    driver.get(" http://10.105.87.250/teste/jocimar/EstatisticaRejud/index.php"); 
                   
                    
                    
                    
                    
                    
                    
                    
                    
                    
                       
    }
    
    
    
    
    private void verSecaiu(String nomeElemento) {
        
        Boolean n = coletas.isElementPresentID(driver, nomeElemento);
        
            if (n){
                logarCef();
            } else{
                verSecaiu(nomeElemento);
            }
        
        
        
        
    }
    
    
    
    
    public void instrucaoRejud(){
        
        
                     Coletas coletas = new Coletas();
                    String usuario="001";
                    String senha="BBrasil";
        
        
                    System.setProperty("webdriver.firefox.marionette", "");
                    driver=new FirefoxDriver();
                    wait =new WebDriverWait(driver, 3000);
                    driver.get("http://cenopservicoscwb.intranet.bb.com.br/apps/judicial/assistente_dinamico/relatorio.php?filtroEqp=55"); 
                   
                    
                    
                    
                    
                    
                    
                    
                    
                    
                       
    }
    
}
