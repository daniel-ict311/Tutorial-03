package com.bignerdranch.android.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mStatementTrue;

    public int getTextResId() {
        return mTextResId;
    }
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isStatementTrue() {
        return mStatementTrue;
    }
    public void setStatementTrue(boolean statementTrue) {
        mStatementTrue = statementTrue;
    }


    public Question(int textResId, boolean statementTrue){
        mTextResId = textResId;
        mStatementTrue = statementTrue;
    }
}

