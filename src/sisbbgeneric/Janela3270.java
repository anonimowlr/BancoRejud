
package sisbbgeneric;

import sisbbgeneric.Term3270;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.util.Properties;


/**
 * Extende a classe Term3270 e a insere em um JFrame e implementa m�todos de manipula��o
 * dessa janela bem como detecta evento de fechamento disparado pelo usu�rio.
 * Tem como como diferencial  * ser totalmente independente da classe que a invoca. Observe
 * que pode ser fechada pelo usuario e que neste caso lan�ara InterrompidoException
 * para os comandos enviados pela classe que a invocou.
 *
 * @author F8366503 Rafael de Almeida Ronchi
 * @author F0437381 Alexandre de Oliveira Santana
 * @since 17/10/2010
 * @version 1.0
 */ 
public class Janela3270 extends Term3270{
    private JFrame janela=null;
    public Janela3270() throws PropertyVetoException {
        super();
        janela = new JFrame("SISBB - Sistema de Informações do Banco do Brasil");
        janela.setLayout(new BorderLayout());
        janela.add(this, BorderLayout.CENTER);
        janela.setVisible(true);
     

        janela.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                rotinaEncerramento();
             }
        });

    }

    public Janela3270(Properties p) throws PropertyVetoException {
        super(p);
        janela = new JFrame("SISBB - Sistema de Informações do Banco do Brasil");
        janela.setLayout(new BorderLayout());
        janela.add(this, BorderLayout.CENTER);
        janela.setVisible(true);

        janela.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                rotinaEncerramento();
            }
        });

    }

    public void setTamanho(int largura, int altura){
        janela.setSize(largura, altura);
    }
    public void setLocal(int x, int y){
        janela.setLocation(x, y);
    }
    public void rotinaEncerramento(){
        this.paradaForcada();
        janela.remove(this);
        janela.dispose();
        janela=null;
    }

}
