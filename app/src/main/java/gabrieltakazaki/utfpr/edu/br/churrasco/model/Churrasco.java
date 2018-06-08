package gabrieltakazaki.utfpr.edu.br.churrasco.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Churrasco {
    @DatabaseField (generatedId = true, id = true)
    private int id_churrasco;

    @DatabaseField (canBeNull = false)
    private String nome_churras;

    @DatabaseField
    private double preco_carne;

    @DatabaseField
    private double preco_bebida;

    @ForeignCollectionField
    private ForeignCollection<Pessoa> pessoas;


    public int getId_churrasco() {
        return id_churrasco;
    }

    public void setId_churrasco(int id_churrasco) {
        this.id_churrasco = id_churrasco;
    }

    public String getNome_churras() {
        return nome_churras;
    }

    public void setNome_churras(String nome_churras) {
        this.nome_churras = nome_churras;
    }

    public double getPreco_carne() {
        return preco_carne;
    }

    public void setPreco_carne(double preco_carne) {
        this.preco_carne = preco_carne;
    }

    public double getPreco_bebida() {
        return preco_bebida;
    }

    public void setPreco_bebida(double preco_bebida) {
        this.preco_bebida = preco_bebida;
    }

    public ForeignCollection<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ForeignCollection<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }


}
