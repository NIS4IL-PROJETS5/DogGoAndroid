package com.example.doggo_android.Views;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.ViewModels.ConnectionViewModel;
import com.example.doggo_android.databinding.FragmentInformationsProfileBinding;
import com.example.doggo_android.databinding.FragmentProfileBinding;

import java.io.File;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InformationsProfileFragment extends Fragment {
    FragmentInformationsProfileBinding binding;
    RetrofitRequests requests;
    String token;
    ConnectionViewModel connectionViewModel;
    IUser user;

    public InformationsProfileFragment() {
        // Required empty public constructor
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("TAG", "File Uri: " + uri.toString());
                    // Get the path
                    String path = requireContext().getFilesDir().getAbsolutePath();
                    Log.d("TAG", "File Path: " + path);
                    // Get the file instance
                    File imgFile = new File(uri.getPath());
                    // Initiate the upload
                    Uri selectedImage = data.getData();
                    ImageView imageView = binding.imageViewProfile;
                    imageView.setImageURI(selectedImage);
                    binding.imageViewProfile.setOnClickListener(v -> {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                        ImageView mImageView = binding.imageViewProfile;
                        ViewGroup parent = (ViewGroup) mImageView.getParent();
                        if (parent != null) {
                            parent.removeView(mImageView);
                        }

                        mBuilder.setView(mImageView);
                        mImageView.setImageURI(data.getData());
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                        mImageView.setOnClickListener(v1 -> {
                            // Ajoutez la vue enfant à un autre groupe de vues ici
                            mDialog.dismiss();
                            //refresh la page
                            NavHostFragment.findNavController(this).navigate(R.id.informationsProfileFragment);
                        });
                        //delete white corners
                        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    });

                    break;
                }

                super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInformationsProfileBinding.inflate(inflater, container, false);
        Button button = binding.button2;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        connectionViewModel = new ViewModelProvider(requireActivity()).get(ConnectionViewModel.class);
        this.requests = Utils.getRetrofitCon(requireContext());
        SharedPreferences getToken = requireActivity().getSharedPreferences("DogGo", 0);
        String apiUrl = Utils.getConfigValue(requireContext(), "api_url");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create()).build();
            requests = retrofit.create(RetrofitRequests.class);
        } catch (Exception e) {
            Log.e("ContactFragment", "onViewCreated: " + e.getMessage());
        }

        this.token = Utils.getToken(requireContext());

        this.user = connectionViewModel.getUser();
        this.checkToken();
        Button updateButton = binding.button;
        updateButton.setOnClickListener(v -> updateButton.setOnClickListener(v1 -> {
            String name = binding.textViewName.getText().toString();
            String surname = binding.textViewSurname.getText().toString();
            String email = binding.textViewEmail.getText().toString();
            String phone = binding.textViewPhone.getText().toString();

            if(validateFields(name,surname,email,phone)){
                HashMap<String, String> updateMap = new HashMap<>();
                updateMap.put("name", name);
                updateMap.put("surname", surname);
                updateMap.put("email", email);
                updateMap.put("phone", phone);

                Call<IUser> call = requests.executeUpdateUser(user.getId(), updateMap, "Bearer " + getToken.getString("token", ""));
                call.enqueue(new Callback<IUser>() {
                    @Override
                    public void onResponse(Call<IUser> call, Response<IUser> response) {
                        if (response.isSuccessful()) {
                            IUser updatedUser = response.body();
                            // handle the updated user object and update UI
                        } else {
                            // handle the error
                        }
                    }
                    @Override
                    public void onFailure(Call<IUser> call, Throwable t) {
                        // handle the failure
                    }
                });

            }
        }));
    }
    private boolean validateFields(String name, String surname, String email, String phone) {
        boolean isValid = true; // Initialise avec la valeur true
        if (name.isEmpty()) {
            Toast.makeText( getContext(), "Champ nom vide", Toast.LENGTH_SHORT).show();
            binding.ViewTextName.setBackgroundColor(Color.RED);
            isValid = false; // Met la variable isValid à false
        }
        else  {
            binding.ViewTextName.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }
        if (surname.isEmpty()) {
            Toast.makeText(getContext(), "Champ prénom vide", Toast.LENGTH_SHORT).show();
            binding.ViewTextSurname.setBackgroundColor(Color.RED);
            isValid = false;
        }
        else {
            binding.ViewTextSurname.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Email invalide", Toast.LENGTH_SHORT).show();
            binding.ViewTextEmail.setBackgroundColor(Color.RED);
            isValid = false;
        }
        else {
            binding.ViewTextEmail.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }
        if (!android.util.Patterns.PHONE.matcher(phone).matches() || !phone.matches("^[0-9]{10}$")) {
            Toast.makeText(getContext(), "Téléphone invalide", Toast.LENGTH_SHORT).show();
            binding.ViewTextPhone.setBackgroundColor(Color.RED);
            isValid = false;
        }
        else {
            binding.ViewTextPhone.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }
        return isValid;
    }

    public void checkToken() {
        Call<IUser> call = this.requests.executeCheckToken("Bearer " + this.token);

        call.enqueue(new Callback<IUser>() {
            @Override
            public void onResponse(Call<IUser> call, Response<IUser> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: Token is valid");
                    if (user.getName() == null) {
                        user = response.body();
                        connectionViewModel.setUser(user);
                        fetchUser();
                    } else {
                        binding.textViewName.setText(user.getName());
                        binding.textViewSurname.setText(user.getSurname());
                        binding.textViewEmail.setText(user.getEmail());
                        binding.textViewPhone.setText(user.getPhone());
                    }
                } else {
                    Log.d(TAG, "onResponse: Token is invalid");
                }
            }

            @Override
            public void onFailure(Call<IUser> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void fetchUser() {

        Call<IUser> call = this.requests.executeGetUser(user.getId(), "Bearer " + this.token);

        call.enqueue(new Callback<IUser>() {
            @Override
            public void onResponse(Call<IUser> call, Response<IUser> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: User fetched");
                    user = response.body();
                    connectionViewModel.setUser(user);
                    binding.textViewName.setText(user.getName());
                    binding.textViewSurname.setText(user.getSurname());
                    binding.textViewEmail.setText(user.getEmail());
                    binding.textViewPhone.setText(user.getPhone());

                } else {
                    Log.d(TAG, "onResponse: User not fetched");
                }
            }

            @Override
            public void onFailure(Call<IUser> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}