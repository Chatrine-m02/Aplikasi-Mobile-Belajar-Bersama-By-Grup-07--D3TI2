package stud11318057.develops.belber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import stud11318057.develops.belber.constants.Constants;
import stud11318057.develops.belber.dao.AnswerData;
import stud11318057.develops.belber.dao.QuestionData;
import stud11318057.develops.belber.models.Answer;
import stud11318057.develops.belber.models.Question;


public class QuestionActivity extends Activity {
    QuestionData mQuestionData;
    AnswerData mAnswerData;

    private int mCategoryId;
    private Map<Integer, Question> mQuestions;
    private Map<Integer, Answer> mAnswers;

    private List<Integer> mQuestionIndexList;
    private int mNextQuestionIndex = 0;

    private int mTotalQuestions = 0;
    private int mQuestionsAnswered = 0;
    private int mCorrectAnswers = 0;

    private Question mCurrentQuestion;

    private List<Button> answerButtons;

    private LinearLayout.LayoutParams mAnswerButtonLayout;

    private TextView mViewHeader;
    private ScrollView mQuestionScrollView;
    private TextView mQuestionText;
    private LinearLayout mAnswersFrame;
    private TextView mQuestionDescriptionText;
    private Button mNextQuestionButton;
    private Button mCompleteButton;

    // Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        adMob();
        mCategoryId = getIntent().getIntExtra("categoryId", 0);

        Log.i(Constants.APP_LOG_NAME, "Getting question for category ID "
                + mCategoryId);

        mAnswerButtonLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mAnswerButtonLayout.setMargins(30, 20, 30, 0);

        mViewHeader = (TextView) findViewById(R.id.viewHeader);
        mQuestionScrollView = (ScrollView) findViewById(R.id.questionScrollView);
        mQuestionText = (TextView) findViewById(R.id.questionText);
        mAnswersFrame = (LinearLayout) findViewById(R.id.answersFrame);
        mQuestionDescriptionText = (TextView) findViewById(R.id.questionDescriptionText);
        mNextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        mCompleteButton = (Button) findViewById(R.id.completeButton);

        mQuestionData = new QuestionData(this);
        mAnswerData = new AnswerData(this);

        mQuestions = mQuestionData.getQuestionsByCategoryId(mCategoryId);

        mQuestionIndexList = new ArrayList<Integer>();

        for (int questionId : mQuestions.keySet()) {
            mQuestionIndexList.add(questionId);
        }

        Collections.shuffle(mQuestionIndexList);

        if (mQuestionIndexList.size() > (Constants.MAX_QUESTIONS_PER_ROUND)) {
            mQuestionIndexList = mQuestionIndexList.subList(0,
                    Constants.MAX_QUESTIONS_PER_ROUND);
        }

        mTotalQuestions = mQuestionIndexList.size();

        Log.w(Constants.APP_LOG_NAME, "Total questions: " + mTotalQuestions);

        if (mTotalQuestions > 0) {
            displayNextQuestion();
        } else {
            Log.e(Constants.APP_LOG_NAME, "No questions found for category ID "
                    + mCategoryId);
        }

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    // Override
    public void onDestroy() {
        super.onDestroy();

        if (mQuestionData != null) {
            mQuestionData.close();
        }

        if (mAnswerData != null) {
            mAnswerData.close();
        }
    }

    /**
     * Displays the next available question in the current trivia round.
     */
    public void displayNextQuestion() {
        // Reset ScrollView position.
        mQuestionScrollView.scrollTo(0, 0);

        mCurrentQuestion = getNextQuestion();

        String headerText = getString(R.string.question) + " "
                + mNextQuestionIndex + " " + getString(R.string.of) + " "
                + mTotalQuestions;

        mViewHeader.setText(headerText);

        displayQuestion(mCurrentQuestion);

        mQuestionDescriptionText.setVisibility(View.GONE);
        mNextQuestionButton.setVisibility(View.GONE);
    }

    /**
     * Gets the next available question in the current trivia round.
     *
     * //return Question - The instance of the next available question.
     */
    private Question getNextQuestion() {
        int nextQuestionId = mQuestionIndexList.get(mNextQuestionIndex);

        Log.w(Constants.APP_LOG_NAME, "Got next question ID " + nextQuestionId);

        Question question = mQuestions.get(nextQuestionId);

        if (question == null) {
            Log.w(Constants.APP_LOG_NAME, "Next question is null.");
        }

        mNextQuestionIndex++;

        return question;
    }

    /**
     * Displays a question on the screen.
     *
     * //param Question question - The question instance to display.
     */
    private void displayQuestion(Question question) {
        if (question != null) {
            mAnswers = mAnswerData.getAnswersByQuestionId(question
                    .getQuestionId());

            List<Integer> answerIndexList = new ArrayList<Integer>();

            for (int answerId : mAnswers.keySet()) {
                answerIndexList.add(answerId);
            }

            Collections.shuffle(answerIndexList);

            mAnswersFrame.removeAllViews();

            mQuestionText.setText(question.getText());

            if (mAnswers != null) {
                answerButtons = new ArrayList<Button>();

                Answer answer = null;

                for (int answerId : answerIndexList) {
                    Log.i(Constants.APP_LOG_NAME, "Adding answer ID "
                            + answerId);

                    answer = mAnswers.get(answerId);

                    Button answerButton = new Button(this);
                    answerButton.setId(answer.getAnswerId());
                    answerButton.setText(answer.getText());
                    answerButton
                            .setBackgroundResource(R.drawable.standard_button);

                    answerButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            disableAnswerButtons();

                            answerButtonClickHandler(v);
                        }
                    });

                    answerButtons.add(answerButton);

                    mAnswersFrame.addView(answerButton, mAnswerButtonLayout);
                }
            }
        } else {
            // TODO: Need to handle this in a more user-friendly way.
            mQuestionText.setText("Null question.");
        }
    }

    /**
     * Click handler for answer option buttons. Determins the result of the user
     * selecting an answer.
     *
     * //param View v - The current view.
     */
    public void answerButtonClickHandler(View v) {
        Answer answer = mAnswers.get(v.getId());

        if (answer != null) {
            mQuestionsAnswered++;

            if (answer.isCorrect()) {
                mCorrectAnswers++;

                v.setBackgroundResource(R.drawable.answer_button_correct);


            } else {
                v.setBackgroundResource(R.drawable.answer_button_incorrect);


            }

            mQuestionDescriptionText.setText(mCurrentQuestion.getDescription());
            mQuestionDescriptionText.setVisibility(View.VISIBLE);

            if (mNextQuestionIndex < mQuestionIndexList.size()) {
                mNextQuestionButton.setVisibility(View.VISIBLE);
            } else {
                mCompleteButton.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Click handler for the next question button. Displays the next available
     * question after current question is answered.
     *
     * //param View v - The current view.
     */
    public void nextQuestionButtonClickHandler(View v) {
        displayNextQuestion();


    }

    /**
     * Click handler for the trivia round completion button. Displays the round
     * summary screen when clicked.
     *
     * //param View v - The current view.
     */
    public void completeButtonClickHandler(View v) {
        Intent i = new Intent(this, SummaryActivity.class);
        i.putExtra("categoryId", mCategoryId);
        i.putExtra("questionsAnswered", mQuestionsAnswered);
        i.putExtra("correctAnswers", mCorrectAnswers);
        startActivity(i);
    }

    /**
     * Disables all answer buttons. Used after an answer is selected.
     */
    private void disableAnswerButtons() {
        Button answerButton = null;

        for (int i = 0; i < answerButtons.size(); i++) {
            answerButton = answerButtons.get(i);
            answerButton.setEnabled(false);
        }
    }

    // AdMob
    public void adMob() {
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("TEST_DEVICE_ID").build();
        adView.loadAd(adRequest);

    }
}