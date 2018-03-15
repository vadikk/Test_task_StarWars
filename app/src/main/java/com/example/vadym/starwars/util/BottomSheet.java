package com.example.vadym.starwars.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vadym.starwars.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vadym on 15.03.2018.
 */

public class BottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    public static String NAME = "name";
    public static String HEIGHT = "height";
    public static String MASS = "mass";
    public static String GENDER = "gender";
    public static String STARSHIPS = "starships";
    public static String FILMS = "films";
    public static String VEHICLES = "vehicles";
    public static String URL = "url";
    @BindView(R.id.nameDetail)
    TextView nameText;
    @BindView(R.id.heightDetail)
    TextView heightText;
    @BindView(R.id.massDetail)
    TextView massText;
    @BindView(R.id.genderDetail)
    TextView genderText;
    @BindView(R.id.closeBtn)
    ImageButton closeBtn;
    @BindView(R.id.starshipsCount)
    TextView starshipsText;
    @BindView(R.id.filmsCount)
    TextView filmsText;
    @BindView(R.id.vehiclesCount)
    TextView vehiclesText;
    @BindView(R.id.openBtn)
    Button openBtn;
    private String url = null;

    public BottomSheet() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        ButterKnife.bind(this, view);

        if (closeBtn != null)
            closeBtn.setOnClickListener(this);
        if (openBtn != null)
            openBtn.setOnClickListener(this);

        setValue();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int position = view.getId();
        switch (position) {
            case R.id.closeBtn:
                dismiss();
                break;
            case R.id.openBtn:
                openBrowser(url);
                break;
        }
    }

    private void openBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void setValue() {

        Bundle bundle = getArguments();

        url = bundle.getString(BottomSheet.URL);
        setNameText(bundle.getString(BottomSheet.NAME));
        setHeightText(bundle.getString(BottomSheet.HEIGHT));
        setMassText(bundle.getString(BottomSheet.MASS));
        setGenderText(bundle.getString(BottomSheet.GENDER));
        setStarshipsText(bundle.getString(BottomSheet.STARSHIPS));
        setFilmsText(bundle.getString(BottomSheet.FILMS));
        setVehiclesText(bundle.getString(BottomSheet.VEHICLES));
    }

    private void setNameText(String text) {
        nameText.setText(text);
    }

    private void setHeightText(String text) {
        heightText.setText(text);
    }

    private void setMassText(String text) {
        massText.setText(text);
    }

    private void setGenderText(String text) {
        genderText.setText(text);
    }

    private void setStarshipsText(String text) {
        starshipsText.setText(text);
    }

    private void setFilmsText(String text) {
        filmsText.setText(text);
    }

    private void setVehiclesText(String text) {
        vehiclesText.setText(text);
    }

}
