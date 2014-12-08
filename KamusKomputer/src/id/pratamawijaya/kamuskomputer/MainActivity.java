package id.pratamawijaya.kamuskomputer;

import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements TextWatcher, OnItemClickListener{
	private EditText			search;
	private ListView			lv;
	private DatabaseHelper		dbHelper;
	private ArrayAdapter<Kamus>	adapter;
	private List<Kamus>			listKamus;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv_data);
		lv.setEmptyView(findViewById(R.id.empty));
		search = (EditText) findViewById(R.id.search);
		dbHelper = DatabaseHelper.getInstance(this);
		setData();
		search.addTextChangedListener(this);
		lv.setOnItemClickListener(this);
	}

	private void setData(){
		listKamus = dbHelper.getAllKamus();
		adapter = new ArrayAdapter<Kamus>(this, android.R.layout.simple_expandable_list_item_1, listKamus);
		lv.setAdapter(adapter);
	}

	@Override
	public void afterTextChanged(Editable arg0){}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){}

	@Override
	public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3){
		adapter.getFilter().filter(s.toString());
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id)
	{
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putString("istilah", adapter.getItem(position).getIstilah());
		b.putString("arti", adapter.getItem(position).getArti());

		Intent i = new Intent(this, ArtiActivity.class);
		i.putExtras(b);
		startActivity(i);

	}

}
