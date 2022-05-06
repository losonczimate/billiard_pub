package com.example.billiard_pub;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    private FirebaseUser user;
    private RecyclerView mRecyclerView;
    private ArrayList<Appointments> mItemsData;
    private AppointmentAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private NotificationHelper mNotificationHelper;
    private JobScheduler mJobScheduler;
    private boolean viewRow = true;

    private int gridNumber = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {finish();}


        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));

        mItemsData = new ArrayList<>();

        mAdapter = new AppointmentAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Appointments");
        queryData();

        mNotificationHelper = new NotificationHelper(this);
        mJobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        setJobScheduler();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void queryData() {
        mItemsData.clear();
        mFirestore.collection("appointments").whereEqualTo("foglalte", false).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Appointments idopont = document.toObject(Appointments.class);
                        mItemsData.add(idopont);
                    }
                    mAdapter.notifyDataSetChanged();
                });
    }

    public void bookaDate(Appointments item) {
        DocumentReference ref = mItems.document(item.getId());
        ref.update("foglalt", true)
                .addOnSuccessListener(success -> {
                    Toast.makeText(this, "Sikeres foglalas: " + item.getDesc(), Toast.LENGTH_LONG).show();
                    mNotificationHelper.send("Sikeres foglalas: " + item.getDesc());
                })
                .addOnFailureListener(fail -> Toast.makeText(this, "Sikertelen", Toast.LENGTH_LONG).show());

        queryData();
        mNotificationHelper.cancel();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setJobScheduler() {
        // SeekBar, Switch, RadioButton
        int networkType = JobInfo.NETWORK_TYPE_UNMETERED;
        Boolean isDeviceCharging = true;
        int hardDeadline = 5000; // 5 * 1000 ms = 5 sec.

        ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceName)
                .setRequiredNetworkType(networkType)
                .setRequiresCharging(isDeviceCharging)
                .setOverrideDeadline(hardDeadline);

        JobInfo jobInfo = builder.build();
        mJobScheduler.schedule(jobInfo);


    }

    public void foglalas(Appointments currentItem) {
    }
}
