package layout;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.mjb.projectexperts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRouteFragment extends Fragment {

    String[] duracion = {"1 HORA","2 HORA","3 HORA"};
    String[] actividad = {"Actividad 1","Actividad 2"};
    String[] distancia = {"1 KM","2 KM"};

    public CreateRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_create_route, container, false);

        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, actividad);
        MaterialBetterSpinner materialDesignSpinnerLocation = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_actividad);
        materialDesignSpinnerLocation.setAdapter(arrayAdapterLocation);

        ArrayAdapter<String> arrayAdapterActivity = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, distancia);
        MaterialBetterSpinner materialDesignSpinnerActivity = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_distancia);
        materialDesignSpinnerActivity.setAdapter(arrayAdapterActivity);

        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, duracion);
        MaterialBetterSpinner materialDesignSpinnerTT = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_duracion);
        materialDesignSpinnerTT.setAdapter(arrayAdapterTT);


        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_search);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSitesFragment addSitesFragment = new AddSitesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, addSitesFragment, "routesFoundFragment");
                ft.commit();
            }
        });



        return v;
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
