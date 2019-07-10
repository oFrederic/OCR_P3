package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.EXTRA_ID;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.EXTRA_NAME;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.EXTRA_PICTURE;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    SharedPreferences mPreferences;

    ImageView mProfilePictureImageView;
    ImageView mReturnImageView;
    TextView mNameTextView1;
    TextView mNameTextView2;
    TextView mAddressTextView;
    TextView mPhoneNumberTextView;
    TextView mFacebookAddressTextView;
    TextView mDescriptionTextView;
    FloatingActionButton mFavoriteFab;

    //the current user we are looking at
    Neighbour mCurrentNeighbour;

    //the current user is favorite or not
    boolean mIsFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //folder where we can save and load data
        mPreferences = getSharedPreferences("PREF", MODE_PRIVATE);

        //link every widgets to is variable
        mProfilePictureImageView = findViewById(R.id.activity_user_details_img);
        mReturnImageView = findViewById(R.id.activity_user_details_return_img);
        mNameTextView1 = findViewById(R.id.activity_user_details_name1_txt);
        mNameTextView2 = findViewById(R.id.activity_user_details_name2_txt);
        mAddressTextView = findViewById(R.id.activity_user_details_address_txt);
        mPhoneNumberTextView = findViewById(R.id.activity_user_details_phone_txt);
        mFacebookAddressTextView = findViewById(R.id.activity_user_details_facebook_txt);
        mDescriptionTextView = findViewById(R.id.activity_user_details_description_txt);
        mFavoriteFab = findViewById(R.id.activity_user_details_fab);

        //get data and set data
        getIncomingIntent();

        //check if the current user were saved favorite or not
        mIsFavorite = mPreferences.getBoolean(mCurrentNeighbour.getName(), false);

        //show the correct logo on favorite fab
        setFabOnPreferences();

        /**
         * Change the favorite fab and save user as favorite or not.
         */
        mFavoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFavorite) {
                    mFavoriteFab.setImageResource(R.drawable.ic_favorite_border_24dp);
                    mIsFavorite = false;
                    mPreferences.edit().putBoolean(mCurrentNeighbour.getName(), false).apply();
                } else {
                    mFavoriteFab.setImageResource(R.drawable.ic_favorite_24dp);
                    mIsFavorite = true;
                    mPreferences.edit().putBoolean(mCurrentNeighbour.getName(), true).apply();
                }
            }
        });

        /**
         * OnClickListener go back to precedent activity.
         */
        mReturnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     * Check if user is favorite or not and show the logo in consequence.
     */
    private void setFabOnPreferences() {
        if (mIsFavorite) {
            mFavoriteFab.setImageResource(R.drawable.ic_favorite_24dp);
        } else {
            mFavoriteFab.setImageResource(R.drawable.ic_favorite_border_24dp);
        }
    }

    /**
     * checking for incoming intents.
     */
    private void getIncomingIntent() {
        if (getIntent().hasExtra(EXTRA_PICTURE) && getIntent().hasExtra(EXTRA_NAME)) {
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            String pictureUrl = getIntent().getStringExtra(EXTRA_PICTURE);
            String name = getIntent().getStringExtra(EXTRA_NAME);

            mCurrentNeighbour = new Neighbour(id, name, pictureUrl);

            setProfile(pictureUrl, name);
        }
    }

    /**
     * setting the picture and name to widgets.
     *
     * @param pictureUrl url of the picture.
     * @param name       name of the user.
     */
    private void setProfile(String pictureUrl, String name) {
        Glide.with(this)
                .asBitmap()
                .load(pictureUrl)
                .into(mProfilePictureImageView);

        mNameTextView1.setText(name);
        mNameTextView2.setText(name);
        mAddressTextView.setText("Adresse de l'utilisateur");
        mPhoneNumberTextView.setText("+33 1 23 45 67 89");
        mFacebookAddressTextView.setText("www.facebook.fr/" + name);
        mDescriptionTextView.setText("Description");
    }
}
