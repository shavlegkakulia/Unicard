package ge.unicard.pos.presentation.other;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.launcher.LauncherContract;
import ge.unicard.pos.ui.base.BaseDialogFragment;


public class InternetFragment extends BaseDialogFragment {

    ProgressBar progressBar;
    TextView status;

    // TODO: Rename and change types and number of parameters
    public static InternetFragment newInstance() {
        InternetFragment fragment = new InternetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internet, container, false);

        status = view.findViewById(R.id.textView_status);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Device Config");   //"შეიყვანეთ პაროლი"

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", (dialog, which) -> {
                    if (input.getText().toString().equals("2107")) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                    }
                    dialog.cancel();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            }
        });

         progressBar =  view.findViewById(R.id.progressBar);
        int colorCodeDark = Color.parseColor("#90c84d");
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));


        return view;
    }
}
