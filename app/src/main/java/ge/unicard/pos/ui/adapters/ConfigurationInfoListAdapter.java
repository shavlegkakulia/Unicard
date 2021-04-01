package ge.unicard.pos.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.unicard.pos.R;
import ge.unicard.pos.model.ConfigurationInfoItem;

/**
 * Created by Akaki on 11/5/18.
 */
public class ConfigurationInfoListAdapter extends SimpleAdapter {

    public ConfigurationInfoListAdapter(@NonNull Context context,
                                        @NonNull List<ConfigurationInfoItem> items) {
        super(
                context,
                mapItems(items),
                R.layout.configuration_info_list_two_item_layout,
                new String[]{KEY_LABEL, KEY_VALUE},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
    }

    private static List<? extends Map<String, ?>> mapItems(List<ConfigurationInfoItem> configurationInfos) {
        final List<Map<String, String>> items = new ArrayList<>();
        if(configurationInfos !=null) {
            for (ConfigurationInfoItem ci : configurationInfos) {
                final Map<String, String> map = new HashMap<>();
                map.put(KEY_LABEL, ci.getLabel());
                map.put(KEY_VALUE, ci.getValue());
                items.add(map);
            }
        }
        else {

        }
        return items;
    }

    static final String KEY_LABEL = "label";
    static final String KEY_VALUE = "value";
}
