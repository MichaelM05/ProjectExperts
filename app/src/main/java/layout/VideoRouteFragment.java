package layout;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.mjb.projectexperts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoRouteFragment extends Fragment {

    private VideoView mVideoView;
    public VideoRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video_route, container, false);
        mVideoView =(VideoView) v.findViewById(R.id.surface_view);
        MediaController mediaController= new MediaController(v.getContext());
        mediaController.setAnchorView(mVideoView);
        Uri uri=Uri.parse("http://rutascr.esy.es/video/hero_video.mp4");
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();

        mVideoView.start();
        return v;
    }

}
