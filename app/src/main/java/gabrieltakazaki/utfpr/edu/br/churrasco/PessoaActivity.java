package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.sql.SQLException;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;
import gabrieltakazaki.utfpr.edu.br.churrasco.persistencia.DatabaseChurras;
import gabrieltakazaki.utfpr.edu.br.churrasco.utils.UtilsGUI;

public class PessoaActivity extends AppCompatActivity {

    public static final String MODO    = "MODO";
    public static final String ID      = "ID";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;


    private EditText editNome;
    private RadioGroup radioBeber;
    private RadioGroup radioComer;
    private EditText editMusica;

    private Pessoa pessoa;
    private int modo;

    public static void novo (Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PessoaActivity.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterar (Activity activity, int requestCode, Pessoa p) {
        Intent intent = new Intent(activity, PessoaActivity.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, p.getId_pessoa());

        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editNome = findViewById(R.id.editTextNome);
        radioComer = findViewById(R.id.radioGroupComer);
        radioBeber = findViewById(R.id.radioGroupBebida);
        editMusica = findViewById(R.id.editTextMusica);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO);

        if (modo == ALTERAR) {
            int id = bundle.getInt(ID);
            try {
                DatabaseChurras con = DatabaseChurras.getInstance(this);

                pessoa = con.getPessoaDAO().queryForId(id);
                editNome.setText(pessoa.getNome());
                radioComer.check(pessoa.getComer());
                radioBeber.check(pessoa.getBeber());
                editMusica.setText(pessoa.getMusicas());

            }catch (SQLException e) {
                e.printStackTrace();
            }
            setTitle(R.string.alterar_pessoa);
        } else {
            pessoa = new Pessoa();
            setTitle(R.string.nova_pessoa);
        }
    }
    private void salvar () {
        String nome = UtilsGUI.validaCampoTexto(this, editNome,R.string.nome_vazio);
        if (nome == null) {
            return;
        }
        pessoa.setNome(nome);
        pessoa.setComer(radioComer.getCheckedRadioButtonId());
        pessoa.setBeber(radioBeber.getCheckedRadioButtonId());
        pessoa.setMusicas(editMusica.getText().toString());
        try {
            DatabaseChurras con = DatabaseChurras.getInstance(this);
            if (modo == NOVO) {
                con.getPessoaDAO().create(pessoa);
            } else {
                con.getPessoaDAO().update(pessoa);
            }
            setResult(Activity.RESULT_OK);
            finish();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSalvar:
                salvar();
                return true;
            case R.id.menuItemCancelar:
                cancelar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
