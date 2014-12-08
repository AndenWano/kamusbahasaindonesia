package id.pratamawijaya.kamuskomputer;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper
{
	private static final String		DB_NAME		= "db_kamus_komputer";
	private static final int		DB_VER		= 1;
	private static final String		TB_DATA		= "tb_data";
	
	public static final String		COL_ID		= "_id";
	public static final String		COL_ISTILAH	= "istilah";
	public static final String		COL_ARTI	= "arti";

	private static DatabaseHelper	dbInstance;
	private static SQLiteDatabase	db;

	private DatabaseHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VER);
	}

	public static DatabaseHelper getInstance(Context context)
	{
		if (dbInstance == null)
		{
			dbInstance = new DatabaseHelper(context);
			db = dbInstance.getWritableDatabase();
		}
		return dbInstance;
	}

	@Override
	public synchronized void close()
	{
		super.close();
		if (dbInstance != null)
		{
			dbInstance.close();
		}
	}

	public List<Kamus> getAllKamus()
	{
		List<Kamus> lisKamus = new ArrayList<Kamus>();

		Cursor cursor = db.query(TB_DATA, new String[] { COL_ID, COL_ID,
				COL_ARTI, COL_ISTILAH }, null, null, null, null, COL_ISTILAH);
		if (cursor.getCount() >= 1)
		{
			cursor.moveToFirst();

			do
			{
				Kamus kamus = new Kamus();
				kamus.setArti(cursor.getString(cursor
						.getColumnIndexOrThrow(COL_ARTI)));
				kamus.setIstilah(cursor.getString(cursor
						.getColumnIndexOrThrow(COL_ISTILAH)));
				lisKamus.add(kamus);

			} while (cursor.moveToNext());
		}
		return lisKamus;

	}

	public Cursor getBukuByJudul(String query)
	{
		Cursor cursor = null;

		if (TextUtils.isEmpty(query))
		{
			cursor = db.query(TB_DATA, new String[] { COL_ID, COL_ARTI,
					COL_ISTILAH }, null, null, null, null, null, "10");
		} else
		{
			cursor = db.query(TB_DATA, new String[] { COL_ID, COL_ARTI,
					COL_ISTILAH }, COL_ISTILAH + " like '" + query + "%'",
					null, null, null, null);
		}
		return cursor;
	}
}
