package ge.unicard.pos.presentation.configuration_info;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ge.unicard.pos.BuildConfig;
import ge.unicard.pos.R;
import ge.unicard.pos.model.ConfigurationInfoItem;
import ge.unicard.pos.ui.adapters.ConfigurationInfoListAdapter;
import ge.unicard.pos.ui.base.BaseDialogFragment;
import ge.unicard.pos.utils.OutlineProvider;

/**
 * Created by Akaki on 11/5/18.
 */
public class ConfigurationInfoDialogFragment extends BaseDialogFragment {

    List<ConfigurationInfoItem> configurationInfoItemList;
    ConfigurationInfoItem configurationInfoItem;

    Bundle bundle;

    public static ConfigurationInfoDialogFragment newInstance() {
        return new ConfigurationInfoDialogFragment();
    }

    @BindView(R.id.list)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = bindView(inflater, R.layout.configuration_info_dialog_fragment);
        OutlineProvider.ROUNDED.applyTo(view, R.dimen.dialog_corner_radius);

        bundle = getArguments();
        if(bundle != null) {
            configurationInfoItemList = new ArrayList<>();
            configurationInfoItemList.add(new ConfigurationInfoItem("Merchant Name", bundle.getString("MerchantName")));
            configurationInfoItemList.add(new ConfigurationInfoItem("Address", bundle.getString("AddressAdditionalValue")));
            configurationInfoItemList.add(new ConfigurationInfoItem("Device ID", bundle.getString("DeviceID")));
            configurationInfoItemList.add(new ConfigurationInfoItem("Merchan ID", bundle.getString("MerchantCode")));
            configurationInfoItemList.add(new ConfigurationInfoItem("Terminal ID", bundle.getString("TerminalCode")));
            configurationInfoItemList.add(new ConfigurationInfoItem("Sim number", bundle.getString("SimCardNumber")));
            configurationInfoItemList.add(new ConfigurationInfoItem("API version", bundle.getString("APIVersion")));
            configurationInfoItemList.add(new ConfigurationInfoItem("APP version", bundle.getString("AppVersion")));
            configurationInfoItemList.add(new ConfigurationInfoItem("APP version code", String.valueOf(BuildConfig.VERSION_CODE)));
        }
        else {
            //
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView.addFooterView(new View(requireContext()));
        listView.setAdapter(new ConfigurationInfoListAdapter(requireContext(),configurationInfoItemList
                /*Collections.nCopies(5, new ConfigurationInfoItem(
                        "დასახელება: ",
                        "დასახელება"))*/));
    }

    @OnClick(R.id.toolbar_action_btn2)
    void onCloseButtonClicked() {
        onResult(Activity.RESULT_CANCELED, null);
    }


}
