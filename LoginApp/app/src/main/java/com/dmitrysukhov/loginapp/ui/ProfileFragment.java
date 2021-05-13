package com.dmitrysukhov.loginapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dmitrysukhov.loginapp.viewmodel.MyViewModel;
import com.dmitrysukhov.loginapp.R;
import com.dmitrysukhov.loginapp.database.User;
import com.dmitrysukhov.loginapp.database.UserDatabase;
import com.dmitrysukhov.loginapp.databinding.FragmentProfileInfoBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

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
        ((Toolbar) profileBinding.toolbarProfile.getRoot()).setNavigationIcon(R.drawable.ic_arrow);
        ((Toolbar) profileBinding.toolbarProfile.getRoot()).setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        canNotBeBlank = getString(R.string.can_not_be_blank);
        userDatabase = myViewModel.getUserDatabase(getActivity());

        if (getArguments().getBoolean("update")) {
            if (myViewModel.getCurrentUser().imageUri != null) {
                imageUri = Uri.parse(myViewModel.getCurrentUser().imageUri);
                profileBinding.imageViewProfilePhoto.setImageURI(Uri.parse(myViewModel.getCurrentUser().imageUri));
            }
            profileBinding.editTextProfileFirstName.setText(myViewModel.getCurrentUser().firstName);
            profileBinding.editTextProfileLastName.setText(myViewModel.getCurrentUser().lastName);
            profileBinding.editTextProfileLogin.setText(myViewModel.getCurrentUser().login);
            profileBinding.editTextProfilePassword.setText(myViewModel.getCurrentUser().password);
            profileBinding.editTextProfileConfirmPassword.setText(myViewModel.getCurrentUser().password);
        }
        String login = getArguments().getString("login");
        if (login != null) profileBinding.editTextProfileLogin.setText(login);
    }

    public void onSaveButtonClick() {
        if (validationWasSuccessful()) {
            User user = myViewModel.getCurrentUser();
            user.firstName = profileBinding.editTextProfileFirstName.getText().toString();
            user.lastName = profileBinding.editTextProfileLastName.getText().toString();
            user.login = profileBinding.editTextProfileLogin.getText().toString();
            user.password = profileBinding.editTextProfilePassword.getText().toString();
            if (imageUri != null) {
                user.imageUri = String.valueOf(imageUri);
            }
            myViewModel.saveCurrentUser(user);
            userDatabase.userDao().insertUser(user);
            navController.navigate(ProfileFragmentDirections.actionProfileInfoFragmentToMainScreenFragment());
        }
    }


    private boolean validationWasSuccessful() {
        boolean valid = false;
        if (isThereNoEmptySpaces()) {
            if (Pattern.matches("[a-z0-9]{0,20}", profileBinding.editTextProfileLogin.getText())) {
                if (Pattern.matches("^[a-zA-Z0-9]{8,16}$", profileBinding.editTextProfilePassword.getText())) {
                    if (profileBinding.editTextProfilePassword.getText().toString()
                            .equals(profileBinding.editTextProfileConfirmPassword.getText().toString())) {
                        valid = true;
                    } else {
                        profileBinding.editTextProfilePassword.setError(getString(R.string.password_mismatch));
                        profileBinding.editTextProfileConfirmPassword.setError(getString(R.string.password_mismatch));
                    }
                } else {
                    profileBinding.editTextProfilePassword.setError(getString(R.string.password_must_contain));
                }
            }else profileBinding.editTextProfileLogin.setError(getString(R.string.login_must_contain));
            }
            return valid;
        }

        private boolean checkLengthOfPassword () {
            return profileBinding.editTextProfilePassword.getText().toString().length() > 7;
        }

        public boolean isThereNoEmptySpaces () {
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


        public void onAddImageClick () {
            ImagePicker.Companion.with(this)
                    .cropSquare()
                    .start();
        }

        @Override
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                profileBinding.imageViewProfilePhoto.setImageURI(imageUri);
            }
        }
    }
