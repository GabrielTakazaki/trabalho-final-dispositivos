package gabrieltakazaki.utfpr.edu.br.churrasco.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gabrieltakazaki.utfpr.edu.br.churrasco.model.Churrasco;
import gabrieltakazaki.utfpr.edu.br.churrasco.model.Pessoa;

public class DatabaseChurras extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "pessoa.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private static DatabaseChurras instance;
    private Dao<Pessoa, Integer> pessoaDAO;
    private Dao<Churrasco, Integer> churrasDAO;

    public static DatabaseChurras getInstance (Context cont) {

        if (instance == null) {
            instance = new DatabaseChurras(cont);
        }
        return instance;
    }
    private DatabaseChurras(Context cont) {
        super(cont, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Pessoa.class);
            TableUtils.createTable(connectionSource, Churrasco.class);

        } catch (SQLException e) {
            Log.e(DatabaseChurras.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            TableUtils.dropTable(connectionSource, Pessoa.class, true);
            TableUtils.dropTable(connectionSource, Churrasco.class, true);

            onCreate(db, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseChurras.class.getName(), "onUpgrade", e);
            throw new RuntimeException(e);
        }

    }

    public Dao<Pessoa, Integer> getPessoaDAO() throws SQLException {
        if (pessoaDAO == null) {
            pessoaDAO = getDao(Pessoa.class);
        }
        return pessoaDAO;
    }

    public Dao<Churrasco, Integer> getChurrasDAO() throws SQLException {
        if (churrasDAO == null) {
            churrasDAO = getDao(Churrasco.class);
        }
        return churrasDAO;
    }
}
