package sisbbgeneric;

import br.com.bb.jibm3270.InterrompidoException;
import br.com.bb.jibm3270.RoboException;
import java.util.Properties;
import java.beans.PropertyVetoException;
import com.ibm.eNetwork.ECL.ECLOIA;
import com.ibm.eNetwork.ECL.ECLPS;
import com.ibm.eNetwork.ECL.ECLSession;
import  com.ibm.eNetwork.beans.HOD.Terminal;
import java.awt.*;
// import com.ibm.eNetwork.ECL.ECLXfer;




/**
 *
 * Implementa o rob� em um Jpanel que pode ser inserido em qualquer classe java. Esta classe
 * tamb�m possui o pr�prio terminal IBM3270 integrado o que dispensa at� mesmo a instala��o
 * do Host On Demand na maquina host.
 * Cont�m os tradicionais m�todos de manipula��o de dados e telas (copiar, colar , teclar...)
 * para atuar so
 *
 * A mesma ainda necessita de aprimoramentos para que possa enviar e receber
 * arquivos do mainframe.
 *
 * @author F8366503 Rafael de Almeida Ronchi
 * @author F0437381 Alexandre de Oliveira Santana
 * @since 12/10/2010
 * @version 1.1
 *
 */
 
public class Term3270  extends javax.swing.JPanel{
    private Terminal terminalInterno =null; //terminal interno do objeto hod 3270;
	private ECLSession sessao = null;  //objeto de sess�o de teminal
	private ECLPS tela = null;         //objeto tela do terminal literalmente
	private ECLOIA rodape = null;      // objeto rodap�, cont�m alguns status
    private boolean habilitado=true;
    /* desativado at� descobrir como essa merda funciona
    private ECLXfer arquivos = null;   //objeto que envia e recebe arquivos do mainframe
	*/

    private long tempoEsperaPadrao = 600000; // tempo que o rob� ir� esperar por cada Transi��o



    /**
     * M�todo de inicializa��o similiar ao da classe  com.ibm.eNetwork.beans.HOD.Terminal
     * recebe um objeto Properties com os par�metros da sess�o e inicia o terminal.
     * Use quando a conex�o n�o  for com o SISBB do Banco do Brasil
     * 
     * @param propriedadesSessao - recebe uma inst�ncia da classe properties
     * com os mesmos par�metros exigidos por com.ibm.eNetwork.beans.HOD.Terminal
     * 
     * @throws java.beans.PropertyVetoException
     */
    public Term3270(Properties propriedadesSessao)
            throws PropertyVetoException {
        
        terminalInterno= new Terminal(propriedadesSessao); //constroi e conecta o terminal
        sessao= terminalInterno.getECLSession(); //obtem a sess�o
        tela = sessao.GetPS(); // da sess�o obtem a tela, onde o rob� atua
		rodape = sessao.GetOIA(); // da sess�o obtem o rodap� de onde o rob� tira informa��es de status
       
        // arquivos= sessao.GetXfer(); Desabilitado at� descobrir como configurar o modo MVS/TSO
     }


    /**
     * Inicializa a classe com os valores padr�o para o SISBB do Banco
     * do Brasil
     *
     * @throws java.beans.PropertyVetoException
     */
    public Term3270() throws PropertyVetoException {
        super();
        this.setLayout(new BorderLayout());
        terminalInterno= new Terminal( new PropriedadesPadraoSISBB()); //invoca o terminal, com propriedades padr�o pr� definidas na classe PropriedadesPadrao SIsbb
        this.add(terminalInterno, BorderLayout.CENTER);
        sessao= terminalInterno.getECLSession(); //obtem a sess�o
        tela = sessao.GetPS(); // da sess�o obtem a tela, onde o rob� atua
        rodape = sessao.GetOIA(); // da sess�o obtem o rodap� de onde o rob� tira informa��es de status
        //arquivos= sessao.GetXfer();
    }

    /**
     * Aguarda por uma determinada sequ�ncia de caracteres em uma posi��o
     * especifica da tela, pelo tempo padr�o. � case sensitive.
     *
     * @param linha - posi�ao da string
     * @param coluna - posi��o da string
     * @param texto - texto a ser encontrada
     *
     * @throws -erros de robo
     */
    public void  aguardar(int linha, int coluna, String texto) throws RoboException{
        isHabilitado();
        aguardar(linha, coluna, texto, tempoEsperaPadrao);
    }

    /**
     * Aguarda por uma determinada sequ�ncia de caracteres em uma posi��o
     * especifica da tela, por um tempo determinado. � case sensitive.
     * @param linha - posi�ao da string
     * @param coluna - posi��o da string
     * @param texto - texto a ser encontrada
     * @param tempo - intervalo de espera em milesegundos
     * @throws br.com.bb.Jibm3270.RoboException
     */
    public void aguardar (int linha, int coluna, String texto, long tempo) throws RoboException{
        isHabilitado();

        try{
            if (rodape.WaitForInput()){
                if (!(tela.WaitForString(texto, linha, coluna, tempo, true,true))){
                    
                    throw new RoboException("Tela incorreta ou tempo de esperada esgotado");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Aguarda indefinidamente por um conjunto de caracteres numa posi��o da tela.
     *
     * @param linha -posi��o
     * @param coluna - posi��o
     * @param texto - texto a ser encontrado
     * @throws br.com.bb.Jibm3270.RoboException
     */
    public void aguardarInd(int linha, int coluna, String texto) throws RoboException{
       isHabilitado();
        try{
            while (!(tela.WaitForString(texto, linha, coluna, tempoEsperaPadrao, true,true))){
                isHabilitado();
                Thread.sleep(500);
            }
        }catch (Exception e){
             e.printStackTrace();
            throw new RoboException("Falha sess�o interrompida");
           
        }

    }
    
    /**
     * Digita o texto especificado na posi��o especificada na tela do terminal.
     * 
     * @param linha - posi��o
     * @param coluna - posi��o
     * @param texto - texto a ser digitado na tela
     * 
     * @throws br.com.bb.Jibm3270.RoboException - erros de robo
     */
    public void colar(int linha, int coluna, String texto) throws RoboException{
		isHabilitado();
        try {
			if(rodape.WaitForInput(tempoEsperaPadrao)) {
				tela.SetText(texto, linha, coluna);
			}
		} catch(Exception e) {
			throw new RoboException("N�o poss�vel colar o texto");
		}
    }

    /**
     * Copia o texto da tela na posi��o e tamanho especificado retornando
     * uma String
     *
     * @param linha -posi��o
     * @param coluna - posi��o
     * @param tamanho - quanto caracteres a direita da posi��o inicial dever�o ser copiados
     * @return - retorna uma String com texto copiado
     * @throws br.com.bb.Jibm3270.RoboException - erros de obo
     */
    public String copiar(int linha, int coluna, int tamanho  )  throws RoboException{
      	isHabilitado();
        int posicao = (80 * (linha - 1)) + coluna - 1;
		return tela.getString().substring(posicao, posicao + tamanho).trim().replace("'", " ");
    }

    /**
     * Move o cursor do teclado para uma posi��o definida na tela.
     *
     * @param linha - posi��o
     * @param coluna - posi��o
     * @throws br.com.bb.Jibm3270.RoboException - erro de rob�
     */
    public void posicionar(int linha, int coluna) throws RoboException{
        isHabilitado();
        try{
            tela.SetCursorPos(linha, coluna);
        }catch(Exception e){
            throw new RoboException("N�o poss�vel mover o cursor. Tela inibida ou posi��o fora do range do cursor.");
        }
    }

    /**
     * Envia comnandos de teclado(enter b, F3, TAB...) para a tela na posi��o atual do cursor.
     *
     * @param texto - comando a ser enviado ex F3= @3
     * @throws br.com.bb.Jibm3270.RoboException erros de rob�
     */
    public void teclar (String texto) throws RoboException{
       isHabilitado();
        if(texto.length() > 0) {
			texto = texto.replace("@2", "[pf2]");
			texto = texto.replace("@3", "[pf3]");
			texto = texto.replace("@4", "[pf4]");
			texto = texto.replace("@5", "[pf5]");
			texto = texto.replace("@6", "[pf6]");
			texto = texto.replace("@7", "[pf7]");
			texto = texto.replace("@8", "[pf8]");
			texto = texto.replace("@9", "[pf9]");
			texto = texto.replace("@10", "[pf10]");
			texto = texto.replace("@11", "[pf11]");
			texto = texto.replace("@12", "[pf12]");
			texto = texto.replace("@E", "[enter]");
			texto = texto.replace("@TAB", "[tab]");

			try {
				if(rodape.WaitForInput(tempoEsperaPadrao)) {
					tela.SendKeys(texto);   
                }
			}catch(Exception e) {
				throw new RoboException("N�o foi poss�vel teclar. Terminal inibido ou desconectado");
			}
		} else {
			throw new RoboException("Tecla de fun��o inv�lida.");
		}
    }

    /**
     * Envia uma tecla de fun��o ao terminal e aguarda pelo tempo de espera padr�o
     * at� uma tela dom dados diferentes seja enviada.
     * Cuidado ao usar esta fun��o pois o "�ltima p�gina" , "Fim de consulta..."
     * que custumam aparecer no fim da tela , configuram um tela diferente mesmo que
     * os demais dados, inclusive a hora sejam iguais.
     *
     * @param texto - comando a ser enviado ex:"@E"
     *
     * @throws br.com.bb.Jibm3270.RoboException -erros de rob�
     */
    public void teclarAguardarTroca(String texto) throws RoboException{
       isHabilitado();
        this.teclarAguardarTroca(texto, tempoEsperaPadrao);
    }

    /**
     * Envia uma tecla fun��o ao terminal e aguarda por um tempo pr�-determinado
     * at� que uma tela com dados diferentes  seja enviada.
     * Cuidado ao usar esta fun��o pois o "�ltima p�gina" , "Fim de consulta..."
     * que custumam aparecer no fim da tela , configuram um tela diferente mesmo que
     * os demais dados, inclusive a hora sejam iguais.
     *
     * @param texto - comando a ser enviado ex:"@E"
     * @param tempo - tempo em milesegundos que se deve esperar por uma nova tela
     *
     * @throws br.com.bb.Jibm3270.RoboException - erros de rob�
     */
    public void teclarAguardarTroca(String texto, long tempo )throws RoboException{
        isHabilitado();
        String inicial = new String();
        long espera =0;
        inicial= tela.getString();
        this.teclar(texto);
       try{
            while( (tela.getString().trim().equals("") ) || (tela.getString().equals(inicial))){
                Thread.sleep(100);
                espera= espera +100;
                if (espera >= tempo ){
                    throw new RoboException("Tempo de espera excedido ou falha de conex�o.");
                }
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
            System.exit(0);
        }


    }
    /*
    // * desativado at� descobrir como mudar o modo de transfer�ncia para MVS/TSO
    public void receberArquivoTexto(String nomeHost, String nomeLocal){
        try{
            arquivos.SetCodePage("437"); //fonte :sisbb web
            arquivos.SetMTUSize(2500);  //idem
            arquivos.ReceiveFile( nomeHost , nomeLocal, "   ASCII CRLF ");
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    */


    /**
     * Tempo que por padr�o o robo ir� esperar em cada transi��o de tela e
     * comando executado
     *
     * @return - valor retornado em milesegundos.
     */
    public long getTempoEsperaPadrao (){
        return tempoEsperaPadrao;
    }

    /**
     * Seta quanto por padr�o o robo ir� aguardar em cada transi��o de tela
     * e comando , antes de acusar erro;
     *
     * @param tempoMilisegundos - tempo de espera em milesegundos
     */
    public void setTemporEsperaPadrao(long tempoMilisegundos){
        tempoEsperaPadrao=tempoMilisegundos;
    }


    /**
     * Interrompe o rob� e recusa qualquer novo comando
     */
    public void paradaForcada(){
        terminalInterno.stopCommunication();
        terminalInterno=null;
        tela=null;
        rodape=null;
        sessao=null;
        habilitado=false;


    }
    private void isHabilitado() throws InterrompidoException{
        if (!(habilitado)){
            throw new InterrompidoException();
        }
    }
}

