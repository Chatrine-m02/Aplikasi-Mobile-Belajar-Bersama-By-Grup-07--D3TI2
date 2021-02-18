package stud11318057.develops.belber.views;

import stud11318057.develops.belber.R;
import stud11318057.develops.belber.models.Score;
import stud11318057.develops.belber.constants.Constants;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScoreView extends LinearLayout
{
    private Context mContext;

    private TextView mCategoryName;
    private TextView mQuestionsAnswered;
    private TextView mPercentCorrect;

    public ScoreView(Context context, Score score)
    {
        super(context);

        this.setOrientation(VERTICAL);

        this.setPadding(10, 5, 10, 5);

        mContext = context;

        mCategoryName = new TextView(context);
        mCategoryName.setTextSize(25);
        mCategoryName.setTextColor(Color.BLACK);

        mQuestionsAnswered = new TextView(context);
        mQuestionsAnswered.setTextSize(15);
        mQuestionsAnswered.setTextColor(Color.BLACK);

        mPercentCorrect = new TextView(context);
        mPercentCorrect.setTextSize(15);
        mPercentCorrect.setTextColor(Color.BLACK);

        mCategoryName.setText(score.getCategoryName());

        Log.d(Constants.APP_LOG_NAME, "Displaying score for category ID " + score.getCategoryId() + ": " + score.getCategoryName());

        mQuestionsAnswered.setText(getFormattedQuestionsAnsweredString(score.getCorrectAnswers(), score.getQuestionsAnswered()));

        mPercentCorrect.setText(getFormattedPercentCorrectString(score.getCorrectAnswers(), score.getQuestionsAnswered()));

        addView(mCategoryName, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mQuestionsAnswered, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mPercentCorrect, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setCategoryName(String categoryName)
    {
        this.mCategoryName.setText(categoryName);
    }

    public void setQuestionsAnswered(int correctAnswers, int questionsAnswered)
    {
        this.mQuestionsAnswered.setText(getFormattedQuestionsAnsweredString(correctAnswers, questionsAnswered));
    }

    public void setPercentCorrect(int percentCorrect)
    {
        String percentCorrectText = "";

        if (percentCorrect > 0)
        {
            percentCorrectText = percentCorrect + "%";
        }

        this.mPercentCorrect.setText(percentCorrectText);
    }

    private String getFormattedPercentCorrectString(int correctAnswers, int questionsAnswered)
    {
        String percentCorrectText = "";

        if (questionsAnswered > 0)
        {
            int percent = (int)(((double)correctAnswers / (double)questionsAnswered) * 100);

            percentCorrectText = percent + "%";
        }

        return percentCorrectText;
    }

    private String getFormattedQuestionsAnsweredString(int correctAnswers, int questionsAnswered)
    {
        String questionsAnsweredText;

        if (questionsAnswered > 0)
        {
            questionsAnsweredText = mContext.getString(R.string.answered)
                    + " " + correctAnswers
                    + " " + mContext.getString(R.string.of)
                    + " " + questionsAnswered
                    + " " + mContext.getString(R.string.questions_correctly);
        }
        else
        {
            questionsAnsweredText = mContext.getString(R.string.no_questions_answered);
        }

        return questionsAnsweredText;
    }
}
