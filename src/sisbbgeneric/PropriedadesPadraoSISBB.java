package sisbbgeneric;

import com.ibm.eNetwork.ECL.ECLSession;
import com.ibm.eNetwork.beans.HOD.Session;
import com.ibm.eNetwork.beans.HOD.Screen;

/**
 * Esta classe extende java.util.Properties, com o objetivo de criar um objeto
 * Proprerties com  valores padr�es usados no SISBB para conex�o do terminal 
 * IBM3270. Dessa forma uma inst�ncia desta classe evita a trabalhosa 
 * tarefa de configurar os dados da conex�o manualmente.
 *  *
 *
 * @author F8366503 Rafael de Almeida Ronchi
 * @author F0437381 Alexandre de Oliveira Santana
 * 
 * Alteração para compilar HOD3270 em 29/03/2016
 */
 
public class PropriedadesPadraoSISBB extends java.util.Properties {
    /**
     * O class initialize n�o necessita de nenhum par�metro e gera uma 
     * inst�ncia do objeto com as configura��es padr�o do para o sisbb :
     * Terminal: Tipo 3270
     * P�gina de c�digo (teclado e v�deo) : Brasil
     * Host: 3270.df.bb
     * Porta: 8023
     * E com as op��es de auto conectar e reconectar ativadas.
     * 
     */
    public PropriedadesPadraoSISBB() {
//        this.put(ECLSession.SESSION_TYPE, ECLSession.SESSION_TYPE_3270_STR);
//		this.put(ECLSession.SESSION_CODE_PAGE, ECLSession.SESSION_CODE_PAGE_BRAZIL_OLD);
//		this.put(Session.HOST, "3270.df.bb");
//		this.put(Session.PORT, "9023");
//		this.put(Session.AUTO_CONNECT, "true");
//		this.put(Session.AUTO_RECONNECT, "true");
//		this.put(Screen.SCREEN_3D, "false");
        
        this.put(ECLSession.SESSION_TYPE, ECLSession.SESSION_TYPE_3270_STR);
        this.put(ECLSession.SESSION_CODE_PAGE_KEY, "KEY_BR");
        this.put(ECLSession.SESSION_CODE_PAGE, ECLSession.SESSION_CODE_PAGE_BRAZIL_OLD);
        this.put(Session.CODE_PAGE, ECLSession.SESSION_CODE_PAGE_BRAZIL_EURO);

        this.put(Session.HOST, "172.17.78.29");  // IP do Host-on-Demand Intranet
        this.put(Session.PORT, "9023");
        this.put(Session.HOST_BACKUP1, "172.17.227.98");
        this.put(Session.PORT_BACKUP1, "9023");
        this.put(Session.HOST_BACKUP2, "172.17.78.220");     // 3270.df.bb
        this.put(Session.PORT_BACKUP2, "9023");
        this.put(Session.AUTO_CONNECT, "true");
        this.put(Session.AUTO_RECONNECT, "true");
        this.put(Screen.SCREEN_3D, "false");
        
        this.put(Session.SSL, "true");
        this.put(Session.SSL_USE_JSSE, "true");
        this.put(Session.SSL_JSSE_TRUSTSTORE, "C:\\Solicitacao Levantamento\\sisbb\\sisbb.jks"); // Local do arquivo dos certificados
        this.put(Session.SSL_JSSE_TRUSTSTORE_TYPE, Session.SSL_JSSE_TRUSTSTORE_TYPE_JKS);
        this.put(Session.SSL_JSSE_TRUSTSTORE_PASSWORD, "sisbbjava");
        
//        this.put(Session.SSL, "true");
//        this.put(Session.SSL_USE_JSSE, "true");
//        this.put(Session.SSL_JSSE_TRUSTSTORE, "C:/Users/suporte/sisbb.jks"); // Local do arquivo dos certificados
//        this.put(Session.SSL_JSSE_TRUSTSTORE_TYPE, Session.SSL_JSSE_TRUSTSTORE_TYPE_JKS);
//        this.put(Session.SSL_JSSE_TRUSTSTORE_PASSWORD, "sisbbjava");
    }
    
}
