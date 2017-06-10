package layout;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mjb.projectexperts.DeleteSiteAdapter;
import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.Domain.Site;
import com.mjb.projectexperts.MenuActivity;
import com.mjb.projectexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyRouteFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeleteSiteAdapter adapter;
    private ArrayList<Route> routeList;
    private ProgressDialog progressDialog;
    public ModifyRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_modify_route, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        routeList = new ArrayList<>();

        routeList = ((MenuActivity)getActivity()).preRouteList;

        String  idUser = ((MenuActivity) getActivity()).user.getIdUser();
        for(int i = 0; i < routeList.size(); i++){
            if(!routeList.get(i).getIdUser().equalsIgnoreCase(idUser)){
                routeList.remove(i);
            }
        }
        adapter = new DeleteSiteAdapter(this,routeList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere....");
        progressDialog.setCancelable(false);



        /**Button btnAdd = (Button)v.findViewById(R.id.btn_add_site);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSitesFragment addSitesFragment = new AddSitesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, addSitesFragment, "addSitesFragment");
                ft.addToBackStack("addSitesFragment");
                ft.commit();
            }
        });*/

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
