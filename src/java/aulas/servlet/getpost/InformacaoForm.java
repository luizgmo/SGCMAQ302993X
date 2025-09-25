package aulas.servlet.getpost;

// javabean tem um construtor sem argumentos e metodos de acesso get e set
public class InformacaoForm {

    private String campoA;
    private String opcaoA;
    private String opcaoB;
    
    public InformacaoForm() {
    }

    public String getCampoA() {
        return campoA;
    }

    public void setCampoA(String campoA) {
        this.campoA = campoA;
    }

    public String getOpcaoA() {
        return opcaoA;
    }

    public void setOpcaoA(String opcaoA) {
        this.opcaoA = opcaoA;
    }

    public String getOpcaoB() {
        return opcaoB;
    }

    public void setOpcaoB(String opcaoB) {
        this.opcaoB = opcaoB;
    }
        
}
