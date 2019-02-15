
package portalgeneric;

import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import entidade.Usuario;


public class Coletas extends Thread{
static WebDriverWait wait=null;

static WebElement elemento=null;
static  WebDriver driver;
static String e;
static String nomeElemento;
static String valorElemento;

    public Coletas() {
    }
    
    
    
    
    @Override    
    public void  run(){  
    
    Usuario user = new Usuario();
    System.setProperty("webdriver.firefox.marionette", "C:\\Users\\PC_LENOVO\\Desktop\\selenium_java\\geckodriver.exe");
    WebDriver driver=new FirefoxDriver ();
     wait =new WebDriverWait(driver, 3000);
    driver.get("https://juridico.intranet.bb.com.br/paj");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.name("callback_0")));
    //driver.manage().window().maximize();
    nomeElemento="txtSenha";
    valorElemento="88454583";
    procuraElementoPorId(driver,nomeElemento,valorElemento);
    
    
    
    
     
    nomeElemento= "callback_0";
   String valorElemento=user.getMatricula();
  //pausar(3000);
  procuraElementoPorName(driver,nomeElemento,valorElemento);
  nomeElemento="callback_2";
  clicarElementoPorNome(nomeElemento);
  //driver.close();
       
    
 } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

   
      
  
 
     
    
  
  
 


 
   
 
 
 
       
            
        
 
       
  
  
  
  
  
  
  
  
    
    
    
    
    private void procuraElementoPorName(WebDriver driver,String nomeElemento,String valorElemento) {
      Boolean n = null;   
       
            try{
           Boolean isElementPresent = driver.findElement(By.name(nomeElemento)).isDisplayed();
                
                    if(isElementPresent){
                      elemento=  driver.findElement(By.name(nomeElemento));
                      elemento.sendKeys(valorElemento);
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Caiu na exceção na localização do elemento");
                        procuraElementoPorName(driver,nomeElemento,valorElemento);
                    }
           
           
            
            }catch(Exception ex ){
                JOptionPane.showMessageDialog(null, ex);
            }
          
    }

    
    
    private void procuraElementoPorId(WebDriver driver,String nomeElemento,String valorElemento) {
      Boolean n = null;   
       
            try{
           Boolean isElementPresent = driver.findElement(By.id(nomeElemento)).isDisplayed();
                
                    if(isElementPresent){
                      elemento=  driver.findElement(By.id(nomeElemento));
                      elemento.sendKeys(valorElemento);
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Caiu na exceção na localização do elemento");
                        procuraElementoPorName(driver,nomeElemento,valorElemento);
                    }
           
           
            
            }catch(Exception ex ){
                JOptionPane.showMessageDialog(null, ex);
            }
          
    }
    
    
    
    
    
    private void pausar(int i) {
    try {
        Thread.sleep(i);
        
    } catch (InterruptedException ex) {
       JOptionPane.showMessageDialog(null, ex);
    }
    }
    
    
    
    
    
    

    private void clicarElementoPorNome(String nomeElemento) {
        
        
       driver.findElement(By.name("callback_2")).click();
                        
                    
    
    
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}


