/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.UploadDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import entidade.TipoUpload;
import entidade.UploadOficio;

/**
 *
 * @author f5078775
 */
public class UploadAOF {
    
    UploadDAO uploadDAO =  new UploadDAO();
//    public static void main(String[] args) throws IOException, InterruptedException {
//        
//        String diretorio = "C:\\Users\\f5078775\\Desktop\\DocumentosAOF";
//        lerDiretorio(diretorio);
//    }
    
    
    
    
     public  void lerDiretorio(String diretorio) throws IOException, InterruptedException {

        
         
         
        File file = new File(diretorio);
        File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];
            
            UploadOficio uploadOficio = new UploadOficio();
            TipoUpload tipoUpload = new TipoUpload();

            Boolean b = arquivos.getName().endsWith(".pdf");
           
                String nomeArquivo = arquivos.getName();
                String caminhoCompleto = diretorio + "'\'" + nomeArquivo;
           
           tipoUpload.setCdTipEnvio(1);
           uploadOficio.setNomeArquivo(caminhoCompleto);
           uploadOficio.setCdTipoEnvio(tipoUpload);
           
           uploadDAO.salvar(uploadOficio);

        }

    }
    
    
    
    
    
}
