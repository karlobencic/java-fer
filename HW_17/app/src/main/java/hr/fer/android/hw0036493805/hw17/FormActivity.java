package hr.fer.android.hw0036493805.hw17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.android.hw0036493805.hw17.models.UserProfile;
import hr.fer.android.hw0036493805.hw17.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This activity represents a simple user profile which gets the data from the given url path.
 *
 * @author Karlo Bencic
 */
public class FormActivity extends AppCompatActivity {

    /**
     * The url input.
     */
    @BindView(R.id.urlInput)
    EditText urlInput;

    /**
     * The first name text.
     */
    @BindView(R.id.firstNameText)
    TextView firstName;

    /**
     * The last name text.
     */
    @BindView(R.id.lastNameText)
    TextView lastName;

    /**
     * The phone number text.
     */
    @BindView(R.id.phoneNumberText)
    TextView phoneNumber;

    /**
     * The email text.
     */
    @BindView(R.id.emailText)
    TextView email;

    /**
     * The spouse text.
     */
    @BindView(R.id.spouseText)
    TextView spouse;

    /**
     * The age text.
     */
    @BindView(R.id.ageText)
    TextView age;

    /**
     * The avatar image.
     */
    @BindView(R.id.avatarImg)
    ImageView avatar;

    /**
     * The REST API service.
     */
    private RetrofitService service;

    /**
     * Starting point of this activity.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://m.uploadedit.com")
                .build();

        service = retrofit.create(RetrofitService.class);
    }

    /**
     * Triggered when the load button is clicked. It gets the data from the server using the Retrofit REST API and shows it on screen.
     */
    @OnClick(R.id.loadBtn)
    void onLoadBtn() {
        if (service == null) {
            Toast.makeText(this, "Unable to get data", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = urlInput.getText().toString();
        service.getUser(url).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Gson gson = new Gson();
                String json = gson.toJson(response.body());

                if (json == null || json.equals("null")) {
                    Toast.makeText(FormActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserProfile profile = gson.fromJson(json, UserProfile.class);
                setData(profile);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(FormActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets the user profile data on the screen.
     *
     * @param profile the user profile data
     */
    private void setData(UserProfile profile) {
        if (profile.getAvatarUrl() == null || profile.getAvatarUrl().isEmpty()) {
            avatar.setVisibility(View.GONE);
        } else {
            Glide.with(this)
                    .load(profile.getAvatarUrl())
                    .into(avatar);
        }

        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        phoneNumber.setText(profile.getPhoneNumber());
        email.setText(profile.getEmail());
        spouse.setText(profile.getSpouse());
        age.setText(profile.getAge());
    }
}
