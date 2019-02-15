package enumEntidade;

/**
 * Enum das contas jurídicas para procedimentos via YC9(Extrato de Título Razão
 * geral) RETAG.
 *
 * @author f4281065
 */
public enum ContaEnum {

    CONTA691("rejud", "tb_conta691", "3793", "99738691"),
    CONTA700("rejud", "tb_conta700", "3793", "99738700");

    private final String bancoDados;
    private final String tabelaBancoDados;
    private final String numeroAgencia;
    private final String numeroConta;

    private ContaEnum(String bancoDados, String tabelaBancoDados, String numeroAgencia, String numeroConta) {
        this.bancoDados = bancoDados;
        this.tabelaBancoDados = tabelaBancoDados;
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = numeroConta;
    }

    public String getBancoDados() {
        return bancoDados;
    }

    public String getTabelaBancoDados() {
        return tabelaBancoDados;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

}
