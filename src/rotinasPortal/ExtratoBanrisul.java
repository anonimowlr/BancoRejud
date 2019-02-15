package rotinasPortal;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;


public class ExtratoBanrisul {
   

	
	public void downloadExtrato() throws Exception {
            System.setProperty("webdriver.firefox.marionette", "");
          
            FirefoxProfile profile = new FirefoxProfile();
             profile.setPreference(   "browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain");
              profile.setPreference("browser.download.manager.showwhenStarting",false);
             profile.setPreference( "browser.download.dir", "C:\\Users\\f5078775\\Desktop\\Documentos_automatizados");
           
              
              FirefoxOptions options = new FirefoxOptions();
              options.setProfile(profile);
              
              WebDriver driver  = new FirefoxDriver(options);
              driver.get("https://www.banrisul.com.br/bbz/link/djr/msie/v50/bbzp11hw.htm");
              for(int i=0;i<100;i++){
              driver.findElement(By.id("textbox")).sendKeys("testing download");
              driver.findElement(By.id("createTxt")).click();
              driver.findElement(By.id("link-to-download")).click();
              Thread.sleep(2000);
              String pasta = "pasta" + i;
              criarDiretorioMacro(pasta);
                
              }
              
            
            
            
            
		
	}
        
        
        
        public  void criarDiretorioMacro(String variavel) {
        try {
            File diretorio = new File("C:\\Users\\f5078775\\Desktop\\Documentos_automatizados\\" + variavel );
            diretorio.mkdir();
            moverArquivos(variavel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o diretorio");
            System.out.println(ex);
        }
    }
        
        public  void  moverArquivos(String variavel) throws IOException, InterruptedException {

	File file = new File("C:\\Users\\f5078775\\Desktop\\Documentos_automatizados");
	File afile[] = file.listFiles();
	int i = 0;
	for (int j = afile.length; i < j; i++) {
		File arquivos = afile[i];
                
                Boolean b = arquivos.getName().endsWith(".txt");
                if (b){
                String nomeArquivo = arquivos.getName();
                String dstPath = "C:\\Users\\f5078775\\Desktop\\Documentos_automatizados\\" + variavel;
                Files.move(arquivos.toPath(), Paths.get(dstPath, nomeArquivo), REPLACE_EXISTING);
                Thread.sleep(2000);
                }
                
	}

}
        
      
        
        
        
        
        
        
        
}