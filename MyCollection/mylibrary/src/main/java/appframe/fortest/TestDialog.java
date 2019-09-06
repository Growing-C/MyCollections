package appframe.fortest;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

/**
 * Created by RB-cgy on 2016/12/21.
 */

public class TestDialog {
    AlertDialog mTestDialog;

    public TestDialog() {
    }

    public void create(Context context) {
        mTestDialog = new AlertDialog.Builder(context).setTitle("Test")
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    public void show() {
        mTestDialog.show();
    }
}
