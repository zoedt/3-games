package project1.csc296.project1;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScoreboardFragment extends Fragment {

    TextView mPlayer1, mPlayer2;

    public ScoreboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View content =  inflater.inflate(R.layout.fragment_scoreboard, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            mPlayer1 = (TextView) content.findViewById(R.id.text_view_player_1);
            mPlayer2 = (TextView) content.findViewById(R.id.text_view_player_2);

            String player1name = bundle.getString(MainActivity.KEY_PLAYER_1);
            String player2name = bundle.getString(MainActivity.KEY_PLAYER_2);
            int player1score = bundle.getInt(MainActivity.KEY_SCORE_1);
            int player2score = bundle.getInt(MainActivity.KEY_SCORE_2);
            Log.d("SCOREBOARD", player1score+"");
            Log.d("SCOREBOARD", player2score+"");


            mPlayer1.setText(player1name + " : " + String.valueOf(player1score));
            mPlayer2.setText(player2name + " : " + String.valueOf(player2score));
        }

        return content;
    }




}
