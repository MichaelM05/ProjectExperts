package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.mjb.projectexperts.GridViewImageAdapter;
import com.mjb.projectexperts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailSiteFragment extends Fragment {


    public DetailSiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_site, container, false);
        GridView gridView = (GridView) v.findViewById( R.id.GridView1 );
        gridView.setAdapter( new GridViewImageAdapter(v.getContext()) );

        Button btnAccept = (Button)v.findViewById(R.id.btn_view_video);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoRouteFragment videoRouteFragment = new VideoRouteFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, videoRouteFragment, "videoRouteFragment");
                ft.addToBackStack("videoRouteFragment");
                ft.commit();

            }
        });

        return v;
    }

}
