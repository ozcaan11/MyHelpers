package semiworld.org.myhelpers;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;
import semiworld.org.helper.Toasty;


public class MainActivity extends AppCompatActivity {

    private static final int CODE = 1422;
    String[] permissions = {Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS};

    Button button_add_user;
    Button button_list_user;
    Button button_request;
    Button button_check;
    EditText editText_username;
    EditText editText_password;
    ListView listView_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Init();

        button_clicks();
    }

    private void Init() {
        button_add_user = (Button) findViewById(R.id.button_add_user);
        button_list_user = (Button) findViewById(R.id.button_list_user);
        button_request = (Button) findViewById(R.id.button_request);
        button_check = (Button) findViewById(R.id.button_check);

        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);

        listView_users = (ListView) findViewById(R.id.listView_users);
    }

    private void button_clicks() {
        button_request.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                EasyPermissions.requestPermissions(MainActivity.this, "Request for messages and contacts", CODE, permissions);
            }
        });

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (EasyPermissions.hasPermissions(getApplicationContext(), permissions))
                    Toasty.success(getApplicationContext(), "Yes !", Toast.LENGTH_SHORT).show();
                else
                    Toasty.error(getApplicationContext(), "No !", Toast.LENGTH_SHORT).show();
            }
        });

        button_add_user.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (!TextUtils.isEmpty(editText_username.getText()) && !TextUtils.isEmpty(editText_password.getText())) {
                    User user = new User();
                    user.username = String.valueOf(editText_username.getText());
                    user.password = String.valueOf(editText_password.getText());
                    user.save();
                    Toasty.success(getApplicationContext(), "User added!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_list_user.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                List<User> userList = new Select().from(User.class).execute();
                final List<String> stringList = new ArrayList<>();
                for (User user : userList)
                    stringList.add(user.toString());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringList);
                listView_users.setAdapter(adapter);
                listView_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setContentText(stringList.get(i))
                                .setConfirmText("Yes,delete it!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        int id = Integer.parseInt(stringList.get(i).split("-")[0]);
                                        new Delete().from(User.class).where("id=?", id).execute();
                                        Toasty.warning(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                    }
                });
            }
        });
    }
}
