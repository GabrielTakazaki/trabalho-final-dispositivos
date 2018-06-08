package gabrieltakazaki.utfpr.edu.br.churrasco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        listViewChurras = (ListView) findViewById(R.id.lstViewPessoa);
        listViewChurras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Churrasco churras = (Churrasco) parent.getItemAtPosition(position);


            }
        });
    }
}
