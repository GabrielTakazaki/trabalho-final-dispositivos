package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;
import gabrieltakazaki.utfpr.edu.br.churrasco.persistencia.DatabaseChurras;
import gabrieltakazaki.utfpr.edu.br.churrasco.utils.UtilsGUI;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView listViewPessoa;
    private ArrayAdapter<Pessoa> listaPessoa;

    private static final int REQUEST_NOVA_PESSOA = 1;
    private static final int REQUEST_ALTERAR_PESSOA = 2;

    public static final String MODO       = "MODO";
    public static final String ID_CHURRAS = "ID_CHURRAS";
    public static final int CHURRAS = 3;


    private int     modo;
    private Pessoa p;

    public static void abrir(Activity activity){

        Intent intent = new Intent(activity, ListaPessoaActivity.class);

        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listViewPessoa = (ListView) findViewById(R.id.lstViewPessoa);
        if (listViewPessoa != null) {
            listViewPessoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pessoa pessoa = (Pessoa) parent.getItemAtPosition(position);
                    PessoaActivity.alterar(ListaPessoaActivity.this, REQUEST_ALTERAR_PESSOA, pessoa);
                }
            });
        }
        popularLista();
        registerForContextMenu(listViewPessoa);

        setTitle(R.string.Pessoa);
    }

    private void popularLista() {
        List<Pessoa> lista = null;

        try {
            DatabaseChurras con = DatabaseChurras.getInstance(this);
            lista = con.getPessoaDAO()
                    .queryBuilder()
                    .orderBy(Pessoa.NOME,true)
                    .query();


        }catch (SQLException e) {
            Log.e("popularLista()", e.getMessage(), e);
            return;
        }
        if (lista != null) {
            listaPessoa = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1,lista);
            listViewPessoa.setAdapter(listaPessoa);
        }
    }
    private void excluirPessoa (final Pessoa p) {
        try {
            DatabaseChurras con = DatabaseChurras.getInstance(this);
            List<Pessoa> lista = con.getPessoaDAO()
                    .queryBuilder()
                    .where().eq(Pessoa.PESSOA_ID, p.getId_pessoa())
                    .query();
        }catch (Exception e) {
            e.printStackTrace();
        }
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
                                            DatabaseChurras.getInstance(ListaPessoaActivity.this);

                                    con.getPessoaDAO().delete(p);

                                    listaPessoa.remove(p);

                                } catch (Exception e) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_NOVA_PESSOA || requestCode == REQUEST_ALTERAR_PESSOA)
                && resultCode == Activity.RESULT_OK){

            popularLista();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adicionar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdicionar:
                PessoaActivity.novo (this,REQUEST_NOVA_PESSOA);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.item_selecionado,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Pessoa pessoa = (Pessoa) listViewPessoa.getItemAtPosition(info.position);

        switch(item.getItemId()){

            case R.id.menuItemExcluir:
                excluirPessoa(pessoa);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
