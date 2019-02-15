
package rotinasSisbb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import entidade.Usuario;


public class Coletas {
 
    
    
static WebDriverWait wait=null;

static WebElement elemento=null;
static WebDriver driver;
static String e;
static String nomeElemento;
static String valorElemento;
  


    public Coletas() {
    }
    
    

    
    
public void autenticarUsuario() {
    
    
Usuario user = new Usuario();
System.setProperty("webdriver.firefox.marionette", "");
driver=new FirefoxDriver();

wait =new WebDriverWait(driver, 3000);
driver.get("https://juridico.intranet.bb.com.br/paj"); 

  
    
 wait.until(ExpectedConditions.presenceOfElementLocated(By.name("callback_0")));
    
  nomeElemento= "callback_0";
  valorElemento=user.getMatricula();
  procuraElementoPorName(driver,nomeElemento,valorElemento);
  nomeElemento="txtSenha";
  
  clickElementId(driver,nomeElemento);
  
  



  wait.until(ExpectedConditions.presenceOfElementLocated(By.id("busca_solicitacoes")));   
  
  
  
  
  
  
  
     
 
  
  
     
 
  
  
 
    }
    
     
public void setURL(String url){

   driver.get(url); 
}        
     
     
     
     
     
     
     
     
     
     
     
     
    
    
    public void procuraElementoPorName(WebDriver driver,String nomeElemento,String valorElemento) {
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
          
          nomeElemento="";
          valorElemento="";
          elemento=null;   
    }

    
    public Boolean isElementPresentID(WebDriver driver,String nomeElemento){
       
    try {
        driver.findElement(By.id(nomeElemento));
    } catch (NoSuchElementException e) {
        return false;
    }finally{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }  
    
    
   return true;

    }
    
     public Boolean isElementPresentXpath(WebDriver driver,String nomeElemento){
       
    try {
          driver.findElement(By.xpath(nomeElemento));
    } catch (NoSuchElementException e) {
        return false;
    }finally{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }  
    
    return true;

    }
    
    
    
    public void procuraElementoPorId(WebDriver driver,String nomeElemento,String valorElemento) {
      Boolean n = null;   
       
         try{   
          elemento = driver.findElement(By.id(nomeElemento));
           n = elemento.isDisplayed();
                
                    if(n){
                      elemento=  driver.findElement(By.id(nomeElemento));
                      elemento.sendKeys(valorElemento);
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error procuraElementoPorId");
                       
                    }
           
           }catch(WebDriverException ex ){
                JOptionPane.showMessageDialog(null, ex);
            }
            
            
          
        nomeElemento="";
        valorElemento="";
        elemento=null;         
    }
    
      public void procuraElementoPorTagName(WebDriver driver,String nomeElemento,String valorElemento) {
      Boolean n = null;   
       
         try{   
         
                      elemento=  driver.findElement(By.tagName(nomeElemento));
                      elemento.sendKeys(valorElemento);
                 
           
           }catch(WebDriverException ex ){
                JOptionPane.showMessageDialog(null, ex);
            }
            
            
          
        nomeElemento="";
        valorElemento="";
        elemento=null;         
    }
    
    
    
    
    
    
    public void pausar(int i) {
    try {
        Thread.sleep(i);
        
    } catch (InterruptedException ex) {
       JOptionPane.showMessageDialog(null, ex);
    }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
     public void clickElementId(WebDriver driver,String nomeElemento) {
      Boolean n = null;   
       
            try{
          
                      elemento=  driver.findElement(By.id(nomeElemento));
                      elemento.click();
                 
            }catch(Exception ex ){
               
              
            }
          
          
    }
     
     
     
     
     
     
     
     public void clickElementXpath(WebDriver driver,String nomeElemento) {
      Boolean n = null;   
       
            try{
          
                      elemento=  driver.findElement(By.xpath(nomeElemento));
                      elemento.click();
                 
            }catch(Exception ex ){
               
              
            }
          
          
    }
     
     
     
     
     
     
     
     
     
     
     
     

    public void clickElementName(WebDriver driver,String nomeElemento) {
      
       
            try{
                 
                      elemento=  driver.findElement(By.name(nomeElemento));
                      elemento.click();
               
            }catch(Exception ex ){
                
              
            }
          
          
    }
    
     public void clickElementTagName(WebDriver driver,String nomeElemento) {
      
       
            try{
                 
                      elemento=  driver.findElement(By.tagName(nomeElemento));
                      elemento.click();
               
            }catch(Exception ex ){
                
                JOptionPane.showMessageDialog(null, ex);
            }
          
          
    }
    
    
    

    void setSize() {
        Dimension d = new Dimension(400,400);

        driver.manage().window().setSize(d);
    }

    
    
    
    
    
    
    
    
    
    
    
    void aguardaElementoTelaByName(String nomeElemento) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(nomeElemento)));
    }

    
    
    
    
    
    public String lerValorElementoID(String nomeElemento) {
        String n=null;
        
        elemento=  driver.findElement(By.id(nomeElemento));
        n =elemento.getText();
        
        
        
       
        
        return n;
    }

    
    
    
     public String lerValorElementoName(String nomeElemento) {
        String n=null;
        try{
        elemento=  driver.findElement(By.name(nomeElemento));
        n =elemento.getText();
        
        
        
        } catch(Exception e ){
            JOptionPane.showMessageDialog(null, "");
            
        }
        
        return n;
    }
    
     
     
     public String lerValorElementoSelectName(String nomeElemento) {
        String n=null;
        try{
        elemento=  driver.findElement(By.name(nomeElemento));
        n =elemento.getText();
        
        
        
        } catch(Exception e ){
            JOptionPane.showMessageDialog(null, "");
            
        }
        
        return n;
    }
    
     
     
     
     public String lerValorElementoSelectID(String nomeElemento) {
        
        
        WebElement n=null;
        String defaultItem = null;
        try{
        Select select = new Select(driver.findElement(By.id(nomeElemento)));
            WebElement option = select.getFirstSelectedOption();
            defaultItem = option.getText();
            

        
        
        } catch(Exception e ){
            JOptionPane.showMessageDialog(null, e);
            
        }
    
        
        return defaultItem;
    }
     
     
     
    
    
    public String quantidadeRegistrosListados(String n) {
      int tamanho = calculaTamanhoValorElemento(n);
       String reg =n.subSequence(22, tamanho).toString();  
        
      return reg;  
        
    }
    
    
     public String quantidadeRegistrosListadosTelaTelaTarifa(String n) {
      int tamanho = calculaTamanhoValorElemento(n);
       String reg =n.subSequence(tamanho - 1, tamanho).toString();  
        
      return reg;  
        
    }
    

    void selecionarElementoName(String nomeElemento, Integer valorElemento) {
        Select dropdown = new Select(driver.findElement(By.name(nomeElemento)));
        dropdown.selectByIndex(valorElemento);
        
        
        
    }
    
    
    
    void selecionarElementoID(String nomeElemento, Integer valorElemento) {
        Select dropdown = new Select(driver.findElement(By.name(nomeElemento)));
        dropdown.selectByIndex(valorElemento);
        
        
        
    }
    

    public int calculaTamanhoValorElemento(String valorElemento) {
       
     return  valorElemento.length();
       
    }

    public BigDecimal tratarNumero(String v) {
    String numeroTratado="";
    
        int tamanhoValorRecebido = calculaTamanhoValorElemento(v);
        
        
                    for(int i=0;i<tamanhoValorRecebido;i++){
                        String num=v.subSequence(i, i+1).toString();
                        
                        if(num.equals(".") && (i == tamanhoValorRecebido - 3 || i == tamanhoValorRecebido - 2)){
                           num=".";
                          numeroTratado= numeroTratado + num ;
                        }
                        
                        
                        
                        
                        if(num.equals(",")){
                           num=".";
                          numeroTratado= numeroTratado + num ;
                        }
                        
                        
                        if(num.equals("0") || num.equals("1") ||num.equals("2") || num.equals("3") || num.equals("4") ||num.equals("5") || num.equals("6") ||  num.equals("7") || num.equals("8") || num.equals("9")){
                           numeroTratado= numeroTratado + num ;
                            
                        }
                    }
        
        
        BigDecimal n = new BigDecimal(numeroTratado);
        n = n.setScale(2,BigDecimal.ROUND_HALF_UP);  
        return n;
    }
    
    
    
    
    
    public Double converteParaDouble(String v) {
    String numeroTratado="";
    
        int tamanhoValorRecebido = calculaTamanhoValorElemento(v);
        
        
                    for(int i=0;i<tamanhoValorRecebido;i++){
                        String num=v.subSequence(i, i+1).toString();
                        
                        if(num.equals(".") && i == tamanhoValorRecebido - 3){
                           num=".";
                          numeroTratado= numeroTratado + num ;
                        }
                        
                        
                        
                        
                        if(num.equals(",")){
                           num=".";
                          numeroTratado= numeroTratado + num ;
                        }
                        
                        
                        if(num.equals("0") || num.equals("1") ||num.equals("2") || num.equals("3") || num.equals("4") ||num.equals("5") || num.equals("6") ||  num.equals("7") || num.equals("8") || num.equals("9")){
                           numeroTratado= numeroTratado + num ;
                            
                        }
                    }
        
        
        Double n = Double.parseDouble(numeroTratado);
        
        return n;
    }


    
    
    public String tratarVariavel(String v) {
    String numeroTratado="";
    
        int tamanhoValorRecebido = calculaTamanhoValorElemento(v);
        
        
                    for(int i=0;i<tamanhoValorRecebido;i++){
                        String num=v.subSequence(i, i+1).toString();
                        
                       
                        
                        
                        if(num.equals("0") || num.equals("1") ||num.equals("2") || num.equals("3") || num.equals("4") ||num.equals("5") || num.equals("6") ||  num.equals("7") || num.equals("8") || num.equals("9")){
                           numeroTratado= numeroTratado + num ;
                            
                        }
                    }
        
        
        
        
        return numeroTratado;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    void aguardaElementoTelaByID(String nomeElemento) {
         wait.until(ExpectedConditions.presenceOfElementLocated(By.id(nomeElemento)));
    }

    void destinarLevantamento(String levantador,String finalidade,String especificacao,String favorecido, String valorResgate) {
        
                    
                        if(levantador.equals("Adverso ou Terceiro")){
                            
                            clickElementId(driver, "incluirDepositoAdversoForm:levantadorDecorate:levantadorRadio:1");
                        
                            
                            
                            pausar(1000);
                        } else{
                              clickElementId(driver, "incluirDepositoAdversoForm:levantadorDecorate:levantadorRadio:0");
                         }
        
                        
                      
                      
                       
                       procuraElementoPorId(driver, "incluirDepositoAdversoForm:finalidadeDecorate:finalidadeListMenu",finalidade);
                        
                        
                       
                       
                       procuraElementoPorId(driver, "incluirDepositoAdversoForm:especificSelect", especificacao);
                        pausar(1000);
                       procuraElementoPorId(driver, "incluirDepositoAdversoForm:especificSelect", especificacao);
                       int qtdPessoa = contaElementosSeletor(driver,"incluirDepositoAdversoForm:favorecidoDecorate:favorecidoListMenu");
                        
                       
                       procuraElementoPorId(driver, "incluirDepositoAdversoForm:tipPagSelect", "Valores em reais");
                       
                       
                        
                       
                       selecionarFavorecido("incluirDepositoAdversoForm:favorecidoDecorate:favorecidoListMenu",favorecido,qtdPessoa);
                      // procuraElementoPorId(driver, "incluirDepositoAdversoForm:favorecidoDecorate:favorecidoListMenu", favorecido);
                        pausar(1000);
                       aguardaElementoTelaByID("incluirDepositoAdversoForm:valorCausaLevDecorate:valorCausaLevInput");
                        pausar(1000);
                        procuraElementoPorId(driver, "incluirDepositoAdversoForm:valorCausaLevDecorate:valorCausaLevInput",valorResgate);
                       
                       
                       verificaDestinacao(levantador,finalidade,especificacao,favorecido, valorResgate);
                      
                       
    }

    
    
    
    
    
    private void verificaDestinacao(String levantador,String finalidade,String especificacao,String favorecido, String valorResgate) {
        
        String finalidadePortal = lerValorElementoSelectID("incluirDepositoAdversoForm:finalidadeDecorate:finalidadeListMenu");
                
                        if(!finalidadePortal.equals(finalidade)){
                            valorResgate="";
                            destinarLevantamento(levantador, finalidade, especificacao, favorecido, valorResgate);
                        }
        String especificacaoPortal = lerValorElementoSelectID("incluirDepositoAdversoForm:especificSelect");
                        if(!especificacaoPortal.equals(especificacao)){
                            valorResgate="";
                            destinarLevantamento(levantador, finalidade, especificacao, favorecido, valorResgate);
                        }
        
        /*                
        String favorecido = lerValorElementoSelectID("incluirDepositoAdversoForm:favorecidoDecorate:favorecidoListMenu");
                        
                        if(!(favorecido.substring(0,10)).equals(fav.substring(0,10))){
                            vlRsgt="";
                            destinarLevantamento(levantador, finalidade, espec, fav, vlRsgt);
                        }
        
       */
        
        
    }

   

    public int contaElementosSeletor(WebDriver driver, String nomeElemento) {
        Select se = new Select(driver.findElement(By.id(nomeElemento)));

        List<WebElement> l = se.getOptions();
        int n =l.size();
        return n;
    }

    void procuraElementoPorXpath(WebDriver driver, String nomeElemento, String valorElemento) {
        Boolean n = null;   
       
         try{   
          elemento=driver.findElement(By.xpath(nomeElemento));
          
                    
          elemento.sendKeys(valorElemento);
                        
                    
           }catch(WebDriverException ex ){
                JOptionPane.showMessageDialog(null, ex);
            }
            
            
          
        nomeElemento="";
        valorElemento="";
        elemento=null;         
    }

    private void selecionarFavorecido(String nomeElemento, String favorecido, int qtdPessoa) {
        pausar(2000);
        int i;
        for(i = 1; i<qtdPessoa;i++){
            
            selecionarElementoID(nomeElemento, i);
            
        String valorSeletor = lerValorElementoSelectID(nomeElemento);
        int tamanhoValSeletor = calculaTamanhoValorElemento(valorSeletor);
        int tamanhoFavorecido = calculaTamanhoValorElemento(favorecido);
        int menorValor;
            
                    if (tamanhoValSeletor>tamanhoFavorecido){
                        menorValor=tamanhoFavorecido;
                        
                    }else{
                        menorValor=tamanhoValSeletor;
                    }
                
        
            
        
        String a =valorSeletor.subSequence(0, menorValor/2).toString();
        String b =favorecido.subSequence(0, menorValor/2).toString();
            if(a.equals(b)){
                return;

            }
        
        }
        if(i==qtdPessoa){
             selecionarElementoID(nomeElemento, 0);
        }
    }
    
    
     public void selecionarNomeEmSeletor(String nomeElemento, String nome, int qtdElmentos) {
        pausar(2000);
        int i;
        for(i = 1; i<qtdElmentos;i++){
            
            selecionarElementoID(nomeElemento, i);
            
        String valorSeletor = lerValorElementoSelectID(nomeElemento);
        int tamanhoValSeletor = calculaTamanhoValorElemento(valorSeletor);
        int tamanhoNomeRecebido = calculaTamanhoValorElemento(nome);
        int menorValor;
            
                    if (tamanhoValSeletor>tamanhoNomeRecebido){
                        menorValor=tamanhoNomeRecebido;
                        
                    }else{
                        menorValor=tamanhoValSeletor;
                    }
                
        
            
        
        String a =valorSeletor.subSequence(0, menorValor/2).toString();
        String b =nome.subSequence(0, menorValor/2).toString();
            if(a.equals(b)){
                return;

            }
        
        }
        if(i==qtdElmentos){
             selecionarElementoID(nomeElemento, 0);
        }
    
     }
    
    

    Boolean isElementPresentTagName(WebDriver driver, String nomeElemento) {
       try {
        driver.findElement(By.tagName(nomeElemento));
    } catch (NoSuchElementException e) {
        return false;
    }finally{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }  
    
    return true;
    }

    String validadeOficio() {
    Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hoje = sdf.format(d);
        int ano = Integer.parseInt(hoje.subSequence(0, 4).toString());
        int mes = Integer.parseInt(hoje.subSequence(5, 7).toString());
        int dia = Integer.parseInt(hoje.subSequence(8, 10).toString());
        String mm = "" + mes;
        String dd = "" + dia;
        String yyyy = "" + ano;
        
        if(mes==12){
            
           mm = "01";  
           ano = ano + 1; 
           yyyy = "" + ano;
        } else{
            if(mes<9){
               mes = mes + 1;
                mm="0" + mes;
            } else{
                mes = mes + 1;
                mm= "" + mes;
            }
        }
        
        
        if(dia>15){
            dia = dia-15;
            if (dia<10){
                dd = "0" + dia;
            } else{
            dd = "" + dia;
            }
        } else{
            if (dia<10){
              dd = "0" + dia;  
            }
        }
        
        
        
        
    
    

    
    
    
    

String validadeOficio = dd + "/" + mm + "/" + yyyy;
        
        
        
        
        
        
        
        
        
        
        
            
            
            
            
    return validadeOficio;
    
    }

    
    Boolean isElementPresentName(WebDriver driver, String nomeElemento) {
       try {
        driver.findElement(By.name(nomeElemento));
    } catch (NoSuchElementException e) {
        return false;
    }finally{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }  
    
    return true;
    }
    
    
    public boolean compararNomes(String nomeA, String nomeB){
        int tamanhoNomeA = calculaTamanhoValorElemento(nomeA);
        int tamanhoNomeB = calculaTamanhoValorElemento(nomeB);
        int menorValor;
            
                    if (tamanhoNomeA>tamanhoNomeB){
                        menorValor=tamanhoNomeB;
                        
                    }else{
                        menorValor=tamanhoNomeA;
                    }
                
        
            
        
        String a =nomeA.subSequence(0, menorValor/2).toString();
        String b =nomeB.subSequence(0, menorValor/2).toString();
            if(a.equals(b)){
                return true;

            } 
            
        return false;
    }

    public Boolean isVisibleById(WebDriver driver,String nomeElemento){
       
     try {
         clickElementId(driver, nomeElemento);
    } catch (Exception e) {
        return false;
    }finally{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }  
    
    return true;
    }

    
    
     public Boolean isVisibleByIdByLeitura(WebDriver driver,String nomeElemento){
       
     try {
         lerValorElementoID(nomeElemento);
    } catch (Exception e) {
        return false;
    }finally{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }  
    
    return true;
    }
    
    
     public void encerraPortal(WebDriver driver){
        driver.quit();
    }
    
    
     
     
      public String lerValorInputID(String nomeElemento) {
        
        String elementval = driver.findElement(By.id(nomeElemento)).getAttribute("value");
        
        
       
         for (int i = 0; i < elementval.length(); i++) {//Esse laço verifica se elementval tem números, caso tenha não entra no IF
          if (Character.isDigit(elementval.charAt(i))==true)
          {
             BigDecimal elementvalTratado= tratarNumero(elementval);
              break;
          }
   
         }
        
       
        return elementval;
    }
     
     
     
  
     
     
    
    
    
    


}
