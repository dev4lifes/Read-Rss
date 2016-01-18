package net.dev4life.baovietyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.List;

import factories.ListNewspaper;
import fragment.MainFragment;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;
import model.ObjectNewspaper;

/**
 * Created by DEV4LIFE on 1/13/16.
 */
public class MyNavigationDrawer extends MaterialNavigationDrawer {

    private final String ARG_POSITION = "position";

    private int posOfNewpaper = 0;

    private ObjectNewspaper objectNewspaper;
    private List<ObjectNewspaper> packNewsList = ListNewspaper.newInstance();
    private MaterialSection section;


    @Override
    public void init(Bundle savedInstanceState) {
        MaterialAccount account = new MaterialAccount(this.getResources(),
                "Pham Tuan Minh", "dev4lifes@gmail.com",
                R.drawable.splash,
                R.drawable.bamboo);
        this.addAccount(account);
        this.addSubheader(getString(R.string.drawer_header_newspaper));
        this.addSubheader(getString(R.string.drawer_subheader_newspaper));

        posOfNewpaper = readPosOfChoosed();

        if (savedInstanceState != null) {
            objectNewspaper = packNewsList.get(savedInstanceState.getInt(ARG_POSITION));
        } else {
            objectNewspaper = packNewsList.get(posOfNewpaper);
        }



        section = newSection(objectNewspaper.getTitleSection(), objectNewspaper.getIconSection(),
                new MainFragment().newInstance(objectNewspaper));
        addSection(section);

        MaterialSection listPaper = newSection(getString(R.string.section_title_list_paper), R.drawable.ic_list_black_24dp, new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                showDialogList();
            }
        });
        addBottomSection(listPaper);

        setDefaultSectionLoaded(0);
    }

    private int readPosOfChoosed() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;
        int pos = sharedPref.getInt(getString(R.string.position_of_choosed_newspaper), defaultValue);
        return pos;
    }

    private void showDialogList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_choose_paper));
        builder.setItems(getResources().getStringArray(R.array.list_newspaper), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                posOfNewpaper = item;
                savePostChoosed(posOfNewpaper);
//                loadSection(posOfNewpaper);
                restartApp();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void savePostChoosed(int posOfNewpaper) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.position_of_choosed_newspaper), posOfNewpaper);
        editor.commit();
    }

    private void loadSection(int _pos){
        objectNewspaper = packNewsList.get(_pos);
        section = newSection(objectNewspaper.getTitleSection(), objectNewspaper.getIconSection(),
                new MainFragment().newInstance(objectNewspaper));
    }


    private void restartApp() {
        Intent mStartActivity = new Intent(this, MyNavigationDrawer.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50, mPendingIntent);
        System.exit(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_POSITION,posOfNewpaper);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        posOfNewpaper = savedInstanceState.getInt(ARG_POSITION);
    }
}
