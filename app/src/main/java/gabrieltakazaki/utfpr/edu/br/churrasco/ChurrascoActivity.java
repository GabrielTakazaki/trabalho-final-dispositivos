package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;

public class ChurrascoActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int NOVO  = 1;
    public static final int ALTERAR = 2;

    private EditText editChurras;
    private EditText editCarne;
    private EditText editBebida;
    private ListView listViewPessoa;
    private ArrayAdapter<Pessoa> listaAdapter;

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

        listViewPessoa = (ListView) findViewById(R.id.listViewPessoa);
        listViewPessoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Pessoa pessoa = (Pessoa) adapterView.getItemAtPosition(position);

            }
        });
    }
}
