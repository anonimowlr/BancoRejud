
package entidade;

public class Usuario {
    
    static private String matricula;
    static private String nomeUsuario;
    static private String senha;
    static private String atividade;
    static String grupoUsuario;
    static private int codigoUor;
    static private int codigoCargo;

    public  int getCodigoUor() {
        return codigoUor;
    }

    public  void setCodigoUor(int codigoUor) {
        Usuario.codigoUor = codigoUor;
    }

    public  int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        Usuario.codigoCargo = codigoCargo;
    }

    public Usuario() {
    }

    public Usuario(String matricula, String senha) {
        this.matricula = matricula;
        this.senha = senha;

    }

    public Usuario(String matricula, String senha, String nomeUsuario, String grupoUsuario) {
        this.matricula = matricula;
        this.senha = senha;
        this.nomeUsuario=nomeUsuario;
        this.grupoUsuario = grupoUsuario;
        
    }
    
    public Usuario(String matricula, String senha,String nomeUsuario,String grupoUsuario,int codigoUor,int codigoCargo) {
        this.matricula = matricula;
        this.senha = senha;
        this.nomeUsuario=nomeUsuario;
        this.grupoUsuario = grupoUsuario;
        this.codigoUor=codigoUor;
        this.codigoCargo = codigoCargo;
        
    }

    public String getGrupoUsuario() {
        return grupoUsuario;
    }

    public void setGrupoUsuario(String grupoUsuario) {
        this.grupoUsuario = grupoUsuario;
    }
    
    
    
    
    
   
    
    
    
    
    
    
    
    
    public static String getAtividade() {
        return atividade;
    }

    public static void setAtividade(String atividade) {
        Usuario.atividade = atividade;
    }

    

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
    
    
      public  String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        Usuario.nomeUsuario = nomeUsuario;
    }
    
    
    
}
