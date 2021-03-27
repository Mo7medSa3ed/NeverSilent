package com.Mo7mdSa3ed55.NeverSilent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    database db = new database(this);
    static final int PICK_CONTACT=1;
TextView textView;
    TextView textView2;
    ImageView img;
Button vibrate ;
Button btn ;
Button add ;
Button DeleteAll ;
ListView list;
SwitchCompat switchCompat;
EditText edit;
ArrayList<String> array =new ArrayList<String>();
    ArrayList<String> array2 =new ArrayList<String>();
Intent intent;
Context c ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        vibrate = (Button)findViewById(R.id.button);
        add = (Button)findViewById(R.id.button3);
        DeleteAll= (Button)findViewById(R.id.button4);
        btn = (Button)findViewById(R.id.button2);
        edit = (EditText)findViewById(R.id.editText);
        switchCompat=(SwitchCompat)findViewById(R.id.switch1) ;
        c = getApplicationContext();

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        switchCompat.setChecked(sharedPreferences.getBoolean("value",true));

        if (switchCompat.isChecked()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent i = new  Intent(getApplicationContext(),Reoncreate.class);
                c.startService(i);
            }else {
                Intent i = new  Intent(getApplicationContext(),Reoncreate.class);
                c.startService(i);
            }
        }else {
            Intent i = new  Intent(getApplicationContext(),Reoncreate.class);
            stopService(i);

        }

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchCompat.setChecked(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Intent i = new  Intent(getApplicationContext(),Reoncreate.class);
                        c.startService(i);
                    }else {
                        Intent i = new  Intent(getApplicationContext(),Reoncreate.class);
                        c.startService(i);
                    }
                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchCompat.setChecked(false);
                    Intent i = new  Intent(getApplicationContext(),Reoncreate.class);
                    stopService(i);
                    }

            }
        });




        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(list.getVisibility()== View.VISIBLE){
                    list.setVisibility(View.INVISIBLE);
                    btn.setText("Show");
                }
                else{
                    list.setVisibility(View.VISIBLE);
                    btn.setText("Hide");
                    Display();
                }


            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=edit.getText().toString();
                String s=number.replace(" ","");
                if (number.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Number Entered", Toast.LENGTH_SHORT).show();
                }else{
                        Toast.makeText(getApplicationContext(),"Added Done",Toast.LENGTH_SHORT).show();
                        db.insert(number,"...................");
                        edit.setText("");
                        Display();
                    }

                }

        });
        DeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Done Delete",Toast.LENGTH_SHORT).show();
                db.Delete();
                Display();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        String name="";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                              numbers.moveToNext();
                                 num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                 name = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number="";
                                number=num.replace(" ","");
                                 test(name, number);
                        }

                    }

                    break;
                }
        }
        }



    class listAdapter extends BaseAdapter {

        ArrayList<listitme> list2 =new ArrayList<listitme>();
        listAdapter(ArrayList<listitme>list2){
            this.list2=list2;
        }

        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return list2.get(position).name;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=getLayoutInflater();
            final View view=layoutInflater.inflate(R.layout.shape,null);
            textView=(TextView) view.findViewById(R.id.textView);
             img=(ImageView) view.findViewById(R.id.imageView);
            textView2=(TextView) view.findViewById(R.id.textView2);
            textView.setText(list2.get(position).num);
            textView2.setText(list2.get(position).name);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name=list2.get(position).num;
                    Integer result = db.Delete_id(name);
                    if (result>0){
                        Display();
                    }else {
                        Toast.makeText(getApplicationContext(),"Fail Delete",Toast.LENGTH_SHORT).show();
                        Display();
                    }
                }
            });

            return view;
        }
    }
public void Display(){
    array = db.getAllDataName();
    array2=db.getAllDataNumber();
    ArrayList<listitme>listnames=new ArrayList<listitme>();
    for (int i=0;i<array.size();i++){
        listnames.add(new listitme(array2.get(i),array.get(i)));
    }
    listAdapter ad=new listAdapter(listnames);
    list.setAdapter(ad);

}

public void test(String name ,String num){
    int count=0;
    array2=db.getAllDataNumber();
    array=db.getAllDataName();
    if (array.size()>0){
        for (int i=0;i<array.size();i++){
            if (array.get(i).equals(name)) {
                Toast.makeText(getApplicationContext(), "This Number Selected Before", Toast.LENGTH_SHORT).show();
                count++;
                break;
            }
        }
            if (count==0) {
                db.insert(num,name);
                Display();

            }


    }else {
        db.insert(num,name);
        Display();


    }
}


}
