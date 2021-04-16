package com.dmitrysukhov.loginapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dmitrysukhov.loginapp.databinding.FragmentProfileInfoBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private FragmentProfileInfoBinding profileBinding;
    private Uri imageUri;
    private String canNotBeBlank;
    private UserDatabase userDatabase;
    private NavController navController;
    private MyViewModel myViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = FragmentProfileInfoBinding.inflate(inflater, container, false);
        profileBinding.setProfileFragment(this);
        return profileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        navController = Navigation.findNavController(view);
        canNotBeBlank = getString(R.string.can_not_be_blank);
        userDatabase = myViewModel.getUserDatabase(getActivity());

        if (getArguments().getBoolean("update")) {
            if (myViewModel.getCurrentUser().imageUri != null)
                profileBinding.imageViewProfilePhoto.setImageURI(Uri.parse(myViewModel.getCurrentUser().imageUri));
            profileBinding.editTextProfileFirstName.setText(myViewModel.getCurrentUser().firstName);
            profileBinding.editTextProfileLastName.setText(myViewModel.getCurrentUser().lastName);
            profileBinding.editTextProfileLogin.setText(myViewModel.getCurrentUser().login);
        }
        String login = getArguments().getString("login");
        if (login != null) profileBinding.editTextProfileLogin.setText(login);
    }

    public void onSaveButtonClick() {
        if (isThereNoEmptySpaces()) {
            if (checkLength()) {
                User oldUser = myViewModel.getCurrentUser();
                if (profileBinding.editTextProfilePassword.getText().toString().equals(profileBinding.editTextProfileConfirmPassword.getText().toString())) {
                    User newUser = new User();
                    newUser.firstName = profileBinding.editTextProfileFirstName.getText().toString();
                    newUser.lastName = profileBinding.editTextProfileLastName.getText().toString();
                    newUser.login = profileBinding.editTextProfileLogin.getText().toString();
                    newUser.password = profileBinding.editTextProfilePassword.getText().toString();
                    if (imageUri != null) {
                        newUser.imageUri = String.valueOf(imageUri);
                    }
                    if (oldUser != null) userDatabase.userDao().deleteUser(oldUser);
                    myViewModel.saveCurrentUser(newUser);
                    userDatabase.userDao().insertUser(newUser);
                    navController.navigate(R.id.mainScreenFragment);
                } else {
                    profileBinding.editTextProfilePassword.setError(getString(R.string.password_mismatch));
                    profileBinding.editTextProfileConfirmPassword.setError(getString(R.string.password_mismatch));
                }
            }else profileBinding.editTextProfilePassword.setError(getString(R.string.min_eight_char));
        }
    }

    private boolean checkLength() {
        return profileBinding.editTextProfilePassword.getText().toString().length() > 7;
    }

    public boolean isThereNoEmptySpaces() {
        if (profileBinding.editTextProfileFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            profileBinding.editTextProfileFirstName.setError(canNotBeBlank);
            return false;
        } else if (profileBinding.editTextProfileLastName.getText().toString().trim().equalsIgnoreCase("")) {
            profileBinding.editTextProfileLastName.setError(canNotBeBlank);
            return false;
        } else if (profileBinding.editTextProfileLogin.getText().toString().trim().equalsIgnoreCase("")) {
            profileBinding.editTextProfileLogin.setError(canNotBeBlank);
            return false;
        } else if (profileBinding.editTextProfilePassword.getText().toString().trim().equalsIgnoreCase("")) {
            profileBinding.editTextProfilePassword.setError(canNotBeBlank);
            return false;
        } else if (profileBinding.editTextProfileConfirmPassword.getText().toString().trim().equalsIgnoreCase("")) {
            profileBinding.editTextProfileConfirmPassword.setError(canNotBeBlank);
            return false;
        } else return true;
    }


    public void onAddImageClick() {
        ImagePicker.Companion.with(this)
                .cropSquare()
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
            profileBinding.imageViewProfilePhoto.setImageURI(imageUri);
        }
    }
}
