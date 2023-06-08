package com.example.testlistview;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //SearchView searchView;
    ListView listView;

    int images[ ]= {R.drawable.profile1, R.drawable.profile2, R.drawable.profile3};

    List<Patient> listItems = new ArrayList<>();
    CustomAdapter customAdapter;

    ArrayList<Patient> patients;
   // ArrayAdapter<Patient> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //creating patients for testing
        Patient patient1 = new Patient("Anna Papadopoulou", "123456", "cosima 12", 1);
        Patient patient2 = new Patient("Christina Alex", "789789", "road 34",2);
        Patient patient3 = new Patient("George Mike", "112233", "street 87",3);

        String names[]={patient1.getName(), patient2.getName(), patient3.getName()};
        String amka[]={patient1.getAmka(), patient2.getAmka(), patient3.getAmka()};
        String street[] = {patient1.getStreet(), patient2.getStreet(), patient3.getStreet()};

        patients = new ArrayList<Patient>();
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        //searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listView);
        for(int i=0; i< names.length; i++){
            Patient itemsModel = new Patient(names[i],amka[i],street[i], images[i]);
            listItems.add(itemsModel);
        }
        customAdapter = new CustomAdapter(listItems, this);
        listView.setAdapter(customAdapter);

        //listView.setVisibility(View.GONE);

//        for(Patient p: patients) {
//            Log.d("§§§§§§§", p.getName() );
//        }


        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);
       // listView.setAdapter(adapter);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                listView.setVisibility(View.VISIBLE);
//               // adapter.getFilter().filter(s);
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                customAdapter.getFilter().filter(s);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search_view){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<Patient> itemsModelList;
        private List<Patient> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<Patient> itemsModelList, Context context) {
            this.itemsModelList = itemsModelList;
            this.itemsModelListFiltered = itemsModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View covertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items, null);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView itemName = view.findViewById(R.id.itemsName);
            TextView itemAmka = view.findViewById(R.id.itemsAmka);

            imageView.setImageResource(itemsModelListFiltered.get(position).getImage());
            itemName.setText(itemsModelListFiltered.get(position).getName());
            itemAmka.setText(itemsModelListFiltered.get(position).getAmka());


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, PatientView.class).putExtra("name", itemsModelListFiltered.get(position)));
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if (constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelList.size();
                        filterResults.values = itemsModelList;
                    }else{
                        String searchStr = constraint.toString().toLowerCase();
                        List<Patient> resultData = new ArrayList<>();

                        for(Patient p: itemsModelList){
                            if(p.getName().contains(searchStr) || p.getAmka().contains(searchStr)){
                                resultData.add(p);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                   itemsModelListFiltered = (List<Patient>) results.values;
                   notifyDataSetChanged();
                }
            };
            return filter;
        }
    }


}