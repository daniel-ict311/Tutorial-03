package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String KEY_INDEX = "index";
    private static  final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;

    private boolean mCheated;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true)
    };
    private int mCurrentQuestionIndex;


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentQuestionIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null){
            mCurrentQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answer_true = mQuestionBank[mCurrentQuestionIndex].isStatementTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answer_true);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newIndex = mCurrentQuestionIndex - 1;
                if (newIndex < 0){
                    newIndex = mQuestionBank.length - 1;
                }
                mCurrentQuestionIndex = newIndex % mQuestionBank.length; //just in case
                updateQuestion();
                mCheated = false;
            }
        });


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentQuestionIndex = (mCurrentQuestionIndex + 1) % mQuestionBank.length;
                updateQuestion();
                mCheated = false;
            }
        });
    }

    private void updateQuestion(){
        int questionTextId = mQuestionBank[mCurrentQuestionIndex].getTextResId();
        mQuestionTextView.setText(questionTextId);
    }

    private void checkAnswer(boolean userPressedTrue){
        if (mCheated){
            Toast.makeText(QuizActivity.this, R.string.judgement_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPressedTrue == mQuestionBank[mCurrentQuestionIndex].isStatementTrue()){
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mCheated = CheatActivity.wasAnswerShown(data);
        }
    }
}
