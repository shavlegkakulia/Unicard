package ge.unicard.pos.utils;

import android.support.annotation.NonNull;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Akaki on 11/5/18.
 */
public final class ActionModeUtils {

    private static final ActionMode.Callback DUMMY_ACTION_MODE_CALLBACK = new ActionMode.Callback() {
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };

    public static void removeSelectionAction(@NonNull EditText editText) {
        editText.setCustomSelectionActionModeCallback(DUMMY_ACTION_MODE_CALLBACK);
    }

    private ActionModeUtils(){}
}
