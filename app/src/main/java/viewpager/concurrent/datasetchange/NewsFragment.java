package viewpager.concurrent.datasetchange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsFragment extends Fragment {
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView txvContent = (TextView) view.findViewById(R.id.txvContent);
        txvContent.setText("News Page");
    }
}
