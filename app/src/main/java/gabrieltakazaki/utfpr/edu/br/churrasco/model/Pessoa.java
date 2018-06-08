package gabrieltakazaki.utfpr.edu.br.churrasco.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Pessoa {


    public static final String DESCRICAO = "descricao";
    public static final String PESSOA_ID = "pessoa_id";

    @DatabaseField (generatedId = true, id = true)
    private int id_pessoa;

    @DatabaseField (canBeNull = false)
    private String nome;

    @DatabaseField
    private int comer;

    @DatabaseField
    private int beber;

    @DatabaseField (foreign = true)
    private Churrasco churras;

    public Pessoa () {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public Churrasco getChurras() {
        return churras;
    }

    public void setChurras(Churrasco churras) {
        this.churras = churras;
    }

    public int getComer() {
        return comer;
    }

    public void setComer(int comer) {
        this.comer = comer;
    }

    public int getBeber() {
        return beber;
    }

    public void setBeber(int beber) {
        this.beber = beber;
    }

    @Override
    public String toString() {
        return nome;
    }
}
