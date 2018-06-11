package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;
import gabrieltakazaki.utfpr.edu.br.churrasco.persistencia.DatabaseChurras;
import gabrieltakazaki.utfpr.edu.br.churrasco.utils.UtilsGUI;

public class ChurrascoActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int NOVO  = 1;
    public static final int ALTERAR = 2;


    private static final int REQUEST_NOVO_PESSOA    = 1;
    private static final int REQUEST_ALTERAR_PESSOA = 2;
    private static final int REQUEST_NOVO_PESSOACHURRAS = 3;

    private EditText editChurras;
    private EditText editCarne;
    private EditText editBebida;
    private ListView listViewPessoa;
    private Button buttonAdicionar;

    private ArrayAdapter<Pessoa> listaAdapter;
    private int modo;

    private Churrasco churras;

    public  static void novo (Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ChurrascoActivity.class);
        intent.putExtra(MODO,NOVO);
        activity.startActivityForResult(intent,NOVO);
    }

    public static void alterar (Activity activity, int requestCode, Churrasco churrasco) {
        Intent intent = new Intent (activity, ChurrascoActivity.class);

        intent.putExtra(MODO,ALTERAR);
        intent.putExtra(ID, churrasco.getId_churrasco());

        activity.startActivityForResult(intent,ALTERAR);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_churrasco);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editChurras = (EditText) findViewById(R.id.editTextChurrasco);
        editCarne = (EditText) findViewById(R.id.editTextCarne);
        editBebida = (EditText) findViewById(R.id.editTextBebida);
        buttonAdicionar = (Button) findViewById(R.id.ButtonAdicionar);

        listViewPessoa = (ListView) findViewById(R.id.listViewPessoa);
        listViewPessoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Pessoa pessoa = (Pessoa) adapterView.getItemAtPosition(position);
                PessoaActivity.alterar(ChurrascoActivity.this, REQUEST_ALTERAR_PESSOA, pessoa);
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        modo = bundle.getInt(MODO);
        if (modo == ALTERAR) {
            int id = bundle.getInt(ID);

            try {
                DatabaseChurras con = DatabaseChurras.getInstance(this);

                churras = con.getChurrasDAO().queryForId(id);
                editChurras.setText(churras.getNome_churras());
                editBebida.setText(String.valueOf(churras.getPreco_bebida()));
                editCarne.setText(String.valueOf(churras.getPreco_carne()));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            churras = new Churrasco();
            setTitle(getString(R.string.novo_churrasco));
        }
        popularPessoa ();
        registerForContextMenu(listViewPessoa);
    }

    private void popularPessoa() {
        List<Pessoa> lista = new ArrayList<>();
        ForeignCollection<Pessoa> pessoas = churras.getPessoas();

        if (pessoas != null) {
            try {
                DatabaseChurras con = DatabaseChurras.getInstance(this);
                CloseableIterator<Pessoa> iterator = pessoas.closeableIterator();
                while (iterator.hasNext()) {
                    Pessoa p = iterator.next();
                    con.getPessoaDAO().refresh(p);
                    lista.add(p);
                }
                iterator.close();
            }catch (Exception e) {
                Log.e("popularLista()", e.getMessage(), e);
                return;
            }
        }
        Collections.sort(lista,Pessoa.comparador);
        listaAdapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, lista);
        listViewPessoa.setAdapter(listaAdapter);
    }

    private void salvar(){
        String nome  = UtilsGUI.validaCampoTexto(this,
                editChurras,
                R.string.nome_vazio);
        if (nome == null){
            return;
        }
        churras.setNome_churras(nome);
        churras.setPreco_bebida(Double.parseDouble(editCarne.getText().toString()));
        churras.setPreco_carne(Double.parseDouble(editBebida.getText().toString()));
        try {

            DatabaseChurras con = DatabaseChurras.getInstance(this);

            if (modo == NOVO) {

                con.getChurrasDAO().create(churras);

            } else {

                con.getChurrasDAO().update(churras);
            }

            setResult(Activity.RESULT_OK);
            finish();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void novaPessoa(View view){
        ChurrasPessoaActivity.churras(this, REQUEST_NOVO_PESSOACHURRAS ,churras);
    }

    private void excluirPessoa(final Pessoa p){

        String mensagem = getString(R.string.deseja_apagar)
                + "\n" + p.getNome();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                try {
                                    DatabaseChurras con =
                                            DatabaseChurras.getInstance(ChurrascoActivity.this);

                                    con.getPessoaDAO().delete(p);

                                    listaAdapter.remove(p);

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_NOVO_PESSOA || requestCode == REQUEST_ALTERAR_PESSOA)
                && resultCode == Activity.RESULT_OK){

            popularPessoa();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.item_selecionado, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Pessoa p = (Pessoa) listViewPessoa.getItemAtPosition(info.position);

        switch(item.getItemId()){

            case R.id.menuItemExcluir:
                excluirPessoa(p);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
