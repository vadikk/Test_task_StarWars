package com.example.vadym.starwars.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vadym.starwars.R;
import com.example.vadym.starwars.model.StarWar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vadym on 14.03.2018.
 */

public class StarWarViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView nameText;
    @BindView(R.id.birthday)
    TextView birthText;
    @BindView(R.id.genderImage)
    ImageView genderImage;

    public StarWarViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setText(StarWar starWar) {

        nameText.setText(starWar.getName());
        birthText.setText(starWar.getBirth());
        switch (starWar.getGender()) {
            case "male":
                genderImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_human_male));
                genderImage.setColorFilter(itemView.getResources().getColor(R.color.maleColor));
                break;
            case "female":
                genderImage.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_human_female));
                genderImage.setColorFilter(itemView.getResources().getColor(R.color.femaleColor));
                break;
            default:
                genderImage.setImageDrawable(null);
                genderImage.setColorFilter(null);
                break;
        }
    }
}
