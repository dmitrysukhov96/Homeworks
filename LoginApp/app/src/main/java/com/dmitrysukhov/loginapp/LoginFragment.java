package com.dmitrysukhov.loginapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dmitrysukhov.loginapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private NavController navController;
    private MyViewModel myViewModel;
    private FragmentLoginBinding fragmentLoginBinding;
    private UserDatabase mUserDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        fragmentLoginBinding.setLoginFragment(this);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        mUserDatabase = myViewModel.getUserDatabase(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onLogInButtonClick() {
        if (fragmentLoginBinding.editTextLoginFragmentLogin.getText().toString().trim().equalsIgnoreCase("")) {
            fragmentLoginBinding.editTextLoginFragmentLogin.setError(getActivity().getString(R.string.can_not_be_blank));
        } else if (fragmentLoginBinding.editTextLoginFragmentPassword.getText().toString().trim().equalsIgnoreCase("")) {
            fragmentLoginBinding.editTextLoginFragmentPassword.setError(getActivity().getString(R.string.can_not_be_blank));}
        else {
        User user = mUserDatabase.userDao().getUserByCredentials(
                fragmentLoginBinding.editTextLoginFragmentLogin.getText().toString(),
                fragmentLoginBinding.editTextLoginFragmentPassword.getText().toString());
        if (user != null) {
            myViewModel.saveCurrentUser(user);
            navController.navigate(R.id.mainScreenFragment);
        } else {
            Toast.makeText(getActivity(),"Wrong login or password",Toast.LENGTH_LONG).show();
        }
    }}

    public void onRegisterButtonClick() {
        Bundle args = new Bundle();
        args.putString("login",fragmentLoginBinding.editTextLoginFragmentLogin.getText().toString());
        navController.navigate(R.id.profileInfoFragment,args);
    }
}