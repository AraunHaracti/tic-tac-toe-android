package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Button> squares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        View.OnClickListener listener = (view) -> {
            Button btn = (Button) view;

            if (!btn.getText().toString().equals("")) return;

            if (GameInfo.isTurn) {
                btn.setText(GameInfo.firstSymbol);
            } else {
                btn.setText(GameInfo.secondSymbol);
            }

            int[] winPositions = calcWinnPositions();
            if (winPositions != null && !GameInfo.isWin) {
                GameInfo.isWin = true;
                Toast.makeText(
                        getApplicationContext(),
                        "winner is " + squares.get(winPositions[0]).getText().toString(),
                        Toast.LENGTH_LONG).show();
                for (int i : winPositions) {
                    squares.get(i).setBackgroundTintList(ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.light_green));
                }
            }

            GameInfo.isTurn = !GameInfo.isTurn;

        };
        LinearLayout board = findViewById(R.id.board);
        generateBoard(3, 3, board);
        setListenerToSquares(listener);

        initClearBordBtn();
    }

    private void initClearBordBtn() {
        Button clearBtn = findViewById(R.id.clear_board_value);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameInfo.isWin = false;
                Toast.makeText(
                        getApplicationContext(),
                        "Новая игра",
                        Toast.LENGTH_LONG).show();
                for (Button square : squares) {
                    square.setText("");
                    square.setBackgroundTintList(
                            ContextCompat.getColorStateList(
                                    getApplicationContext(),
                                    R.color.gray_light));
                }
            }
        });
    }

    private void setListenerToSquares(View.OnClickListener listener) {
        for (int i = 0; i < squares.size(); i++)
            squares.get(i).setOnClickListener(listener);
    }

    public void generateBoard(int rowCount, int columnCount, LinearLayout board) {
        for (int row = 0; row < rowCount; row++) {
            LinearLayout rowContainer = generateRow(columnCount);
            board.addView(rowContainer);
        }
    }

    private LinearLayout generateRow(int squaresCount) {
        LinearLayout rowContainer = new LinearLayout(getApplicationContext());
        rowContainer.setOrientation(LinearLayout.HORIZONTAL);
        rowContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        for (int square = 0; square < squaresCount; square++) {
            Button button = new Button(getApplicationContext());
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.gray_light));
            button.setWidth(convertToPixel(50));
            button.setHeight(convertToPixel(90));
            rowContainer.addView(button);
            squares.add(button);
        }
        return rowContainer;
    }

    public int convertToPixel(int digit) {
        float density = getApplicationContext()
                .getResources().getDisplayMetrics().density;
        return (int) (digit * density + 0.5);
    }

    public int[] calcWinnPositions() {
        for (int i = 0; i < GameInfo.winCombination.length; i++) {
            boolean findComb = true;
            String symbol = squares.get(GameInfo.winCombination[i][0]).getText().toString();
            if (!(symbol.equals("X") || symbol.equals("O")))
                continue;
            for (int j = 0; j < GameInfo.winCombination[0].length; j++) {
                int index = GameInfo.winCombination[i][j];
                if (!squares.get(index).getText().toString().equals(symbol)) {
                    findComb = false;
                    break;
                }
            }
            if (findComb) return GameInfo.winCombination[i];
        }
        return null;
    }
}