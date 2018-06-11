package gabrieltakazaki.utfpr.edu.br.churrasco.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Comparator;

@DatabaseTable
public class Pessoa {

    public static Comparator<Pessoa> comparador = new Comparator<Pessoa>() {
        @Override
        public int compare(Pessoa p1, Pessoa p2) {

            int ordemPessoa = p1.getNome().compareToIgnoreCase(p2.getNome());

            if (ordemPessoa == 0){
                return p1.getNome().compareToIgnoreCase(p2.getNome());
            }else{
                return ordemPessoa;
            }
        }
    };

    public static final String NOME = "nome";
    public static final String PESSOA_ID = "id_pessoa";
    public static final String CHURRAS_ID = "churras_id";

    @DatabaseField (generatedId = true, columnName = PESSOA_ID)
    private int id_pessoa;

    @DatabaseField (canBeNull = false, columnName = NOME)
    private String nome;

    @DatabaseField
    private int comer;

    @DatabaseField
    private int beber;

    @DatabaseField (foreign = true, columnName = CHURRAS_ID)
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

    public Churrasco getChurras() {
        return churras;
    }

    public void setChurras(Churrasco churras) {
        this.churras = churras;
    }
}
