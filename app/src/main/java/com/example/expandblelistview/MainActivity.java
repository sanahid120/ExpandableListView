package com.example.expandblelistview;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    ExpandableListView expandableListView;
    List<String> groupData;
    HashMap<String, List<String>> childData;
    int lastExpandedGroup = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandableListView);
        data();
        ExpandableListAdapter adapter =  new com.example.expandblelistview.adapter.ExpandableListAdapter(this,groupData,childData); {
        };
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String groupText = groupData.get(groupPosition);
                Toast.makeText(getApplicationContext(), groupText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                String groupText = groupData.get(groupPosition);
                Toast.makeText(getApplicationContext(), groupText + " is Collapsed", Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String childText = childData.get(groupData.get(groupPosition)).get(childPosition);
                Toast.makeText(getApplicationContext(), childText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedGroup != -1 && lastExpandedGroup != groupPosition){
                    expandableListView.collapseGroup(lastExpandedGroup);
                }
                lastExpandedGroup = groupPosition;
            }
        });
    }

    private void data() {
        String[] headerString = getResources().getStringArray(R.array.famous_people_array);
        String[] childString = getResources().getStringArray(R.array.famous_people_array_subtitle);
        groupData = new ArrayList<>();
        childData = new HashMap<>();

        for (int i = 0; i<headerString.length; i++) {
            groupData.add(headerString[i]);
            List<String> child = new ArrayList<>();
            child.add(childString[i]);
            childData.put(groupData.get(i), child);
        }
    }
}
