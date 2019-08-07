package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import butterknife.BindView;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.EXTRA_ID;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.EXTRA_NAME;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.EXTRA_PICTURE;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    // UI Components
    @BindView(R.id.activity_user_details_img)
    ImageView mProfilePictureImageView;
    @BindView(R.id.activity_user_details_return_img)
    ImageView mReturnImageView;
    @BindView(R.id.activity_user_details_name1_txt)
    TextView mNameTextView1;
    @BindView(R.id.activity_user_details_name2_txt)
    TextView mNameTextView2;
    @BindView(R.id.activity_user_details_address_txt)
    TextView mAddressTextView;
    @BindView(R.id.activity_user_details_phone_txt)
    TextView mPhoneNumberTextView;
    @BindView(R.id.activity_user_details_facebook_txt)
    TextView mFacebookAddressTextView;
    @BindView(R.id.activity_user_details_description_txt)
    TextView mDescriptionTextView;
    @BindView(R.id.activity_user_details_fab)
    FloatingActionButton mFavoriteFab;

    //the current user we are looking at
    Neighbour mCurrentNeighbour;

    //the current user is favorite or not
    boolean mIsFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get data and set data
        getIncomingIntent();

        /**
         * Change the favorite fab and save user as favorite or not.
         */
        mFavoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFavorite) {
                    mFavoriteFab.setImageResource(R.drawable.ic_favorite_border_24dp);
                    mIsFavorite = false;
                } else {
                    mFavoriteFab.setImageResource(R.drawable.ic_favorite_24dp);
                    mIsFavorite = true;
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
    private void setFab() {
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
