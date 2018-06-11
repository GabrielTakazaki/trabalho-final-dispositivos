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

import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;
import gabrieltakazaki.utfpr.edu.br.churrasco.persistencia.DatabaseChurras;
import gabrieltakazaki.utfpr.edu.br.churrasco.utils.UtilsGUI;

public class ListaChurrascoActivity extends AppCompatActivity {
    private ListView listViewChurras;
    private ArrayAdapter<Churrasco> listaAdapter;

    private static final int REQUEST_NOVA_CHURRAS    = 1;
    private static final int REQUEST_ALTERAR_CHURRAS = 2;

    public static void abrir(Activity activity){

        Intent intent = new Intent(activity, ListaChurrascoActivity.class);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listViewChurras = findViewById(R.id.lstViewPessoa);
        if (listViewChurras != null) {
            listViewChurras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Churrasco churras = (Churrasco) parent.getItemAtPosition(position);

                    ChurrascoActivity.alterar(ListaChurrascoActivity.this,
                            REQUEST_ALTERAR_CHURRAS,
                            churras);
                }
            });
        }


        popularLista();

        registerForContextMenu(listViewChurras);
    }

    private void popularLista(){

        List<Churrasco> lista = null;

        try {
            DatabaseChurras con = DatabaseChurras.getInstance(this);

            lista = con.getChurrasDAO()
                    .queryBuilder()
                    .orderBy(Churrasco.NOME, true)
                    .query();

        } catch (SQLException e) {
            Log.e("popularLista()", e.getMessage(), e);
            return;
        }
        listaAdapter = new ArrayAdapter<Churrasco>(this,
                android.R.layout.simple_list_item_1,
                lista);

        listViewChurras.setAdapter(listaAdapter);
    }

    private void excluirChurras(final Churrasco churras){

        String mensagem = getString(R.string.deseja_apagar)
                + "\n" + churras.getNome_churras();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                try {
                                    DatabaseChurras con =
                                            DatabaseChurras.getInstance(ListaChurrascoActivity.this);

                                    DeleteBuilder<Pessoa, Integer> deleteBuilder = con.getPessoaDAO()
                                            .deleteBuilder();

                                    deleteBuilder.where().eq(Pessoa.CHURRAS_ID, churras.getId_churrasco());
                                    deleteBuilder.delete();

                                    con.getChurrasDAO().delete(churras);

                                    listaAdapter.remove(churras);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_NOVA_CHURRAS || requestCode == REQUEST_ALTERAR_CHURRAS)
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
                ChurrascoActivity.novo (this,REQUEST_NOVA_CHURRAS);
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

        Churrasco churras = (Churrasco) listViewChurras.getItemAtPosition(info.position);

        switch(item.getItemId()){

            case R.id.menuItemExcluir:
                excluirChurras(churras);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

}
