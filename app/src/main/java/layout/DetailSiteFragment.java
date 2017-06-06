package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    /**
        final VideoView videoView =
                (VideoView) v.findViewById(R.id.videoView);

        videoView.setVideoPath(
                "http://www.ebookfrenzy.com/android_book/movie.mp4");

        MediaController mediaController = new
                MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new
        MediaPlayer.OnPreparedListener()  {
        @Override
        public void onPrepared(MediaPlayer mp) {
           System.out.println("Duration = " +videoView.getDuration());
        }
        });

        videoView.start();
     **/

        return v;
    }

}
