package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import gabrieltakazaki.utfpr.edu.br.churrasco.R;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;

public class Principal extends AppCompatActivity {

    private ListView listViewChurras;
    private ArrayAdapter<Churrasco> listaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);



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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
