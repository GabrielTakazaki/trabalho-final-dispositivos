package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;

public class Principal extends AppCompatActivity {

    private ListView listViewChurras;
    private static final int REQUEST_ALTERAR_CHURRAS = 2;
    List<String> listaDados;
    ArrayAdapter<Pessoa>  listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listViewChurras = findViewById(R.id.lstViewPessoa);



        listViewChurras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Churrasco churras = (Churrasco) parent.getItemAtPosition(position);

                ChurrascoActivity.alterar(Principal.this,
                        REQUEST_ALTERAR_CHURRAS,
                        churras);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemChurrasco:
                ListaChurrascoActivity.abrir(this);
                return true;

            case R.id.menuItemPessoa:
                ListaPessoaActivity.abrir (this);
                return true;

            case R.id.menuItemSobre:
                Tela_Sobre.abrir(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
