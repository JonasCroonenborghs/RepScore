package be.thomasmore.repscore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, null);

//        Bundle bundle = this.getArguments();
//
//        if (bundle != null) {
//            String id = bundle.getString("id");
//            String name = bundle.getString("name");
//            String first_name = bundle.getString("first_name");
//            String last_name = bundle.getString("last_name");
//            String email = bundle.getString("email");
//            String gender = bundle.getString("gender");
//
//            TextView textViewName = (TextView) getView().findViewById(R.id.textViewUsername);
//            textViewName.setText("Welcome " + name);
//        }
    }
}
