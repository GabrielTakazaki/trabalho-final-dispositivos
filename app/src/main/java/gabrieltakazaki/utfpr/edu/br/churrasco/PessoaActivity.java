package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.sql.SQLException;

import gabrieltakazaki.utfpr.edu.br.churrasco.R;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;
import gabrieltakazaki.utfpr.edu.br.churrasco.persistencia.DatabaseChurras;

public class PessoaActivity extends AppCompatActivity {

    public static final String MODO    = "MODO";
    public static final String ID      = "ID";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

    private EditText editNome;
    private RadioGroup radioBeber;
    private RadioGroup radioComer;

    private Pessoa pessoa;
    private int modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editNome = (EditText) findViewById(R.id.editTextNome);
        radioComer = (RadioGroup) findViewById(R.id.radioGroupComer);
        radioBeber = (RadioGroup) findViewById(R.id.radioGroupBebida);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO);

        if (modo == ALTERAR) {
            int id = bundle.getInt(ID);
            try {
                DatabaseChurras con = DatabaseChurras.getInstance(this);

                pessoa = con.getPessoaDAO().queryForId(id);
                editNome.setText(pessoa.getNome());
                if (radioComer )
            }catch (SQLException e) {
                    e.printStackTrace();

            }
        }
    }

}
