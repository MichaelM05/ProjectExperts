package layout;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.NewRouteAdapter;
import com.mjb.projectexperts.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewSiteFragment extends Fragment {


    private RecyclerView recyclerView;
    private NewRouteAdapter adapter;
    private ArrayList<Route> routeList;

    public NewSiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_new_site, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        routeList = new ArrayList<>();

      //  for(int i = 0; i < 4; i++){
         //   routeList.add(new Route("Ruta " + i, "Descripción",
               //     "http://rentacarcostarica.com/portal/wp-content/uploads/2016/09/Prusia-Park-is-part-of-the-Iraz%C3%BA-National-Park.jpg"));
      //  }

        adapter = new NewRouteAdapter(this, routeList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        Button btnCreate = (Button)v.findViewById(R.id.btn_accept_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             CreateRouteFragment createRouteFragment = new CreateRouteFragment();
             FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
             ft.replace(R.id.frame, createRouteFragment, "createRouteFragment");
             ft.addToBackStack("createRouteFragment");
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
