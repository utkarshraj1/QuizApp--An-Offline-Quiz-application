package com.invertemotech.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView questionLabel, questionCountLabel, scoreLabel;
    EditText answerEdt;
    Button submitButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArraylist;


    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionCountLabel = findViewById(R.id.noQuestion);
        questionLabel = findViewById(R.id.question);
        scoreLabel = findViewById(R.id.score);

        answerEdt = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        questionModelArraylist = new ArrayList<>();

        setUpQuestion();

        setData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer();
            }
        });

        answerEdt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                Log.e("event.getAction()",event.getAction()+"");
                Log.e("event.keyCode()",keyCode+"");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    checkAnswer();
                    return true;
                }
                return false;
            }
        });

    }
    public void checkAnswer(){
        String answerString  = answerEdt.getText().toString().trim();




        if(answerString.equalsIgnoreCase(questionModelArraylist.get(currentPosition).getAnswer())){
            numberOfCorrectAnswer ++;



            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Good job!")
                    .setContentText("Right Answer")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            currentPosition ++;

                            setData();
                            answerEdt.setText("");
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();


        }else {

            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Wrong Answer")
                    .setContentText("The right answer is : "+questionModelArraylist.get(currentPosition).getAnswer())
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();

                            currentPosition ++;

                            setData();
                            answerEdt.setText("");
                        }
                    })
                    .show();
        }





        int x = ((currentPosition+1) * 100) / questionModelArraylist.size();

        progressBar.setProgress(x);



    }




    public void setUpQuestion(){


        questionModelArraylist.add(new QuestionModel("Recursive problems are implemented by ? ","stack"));
        questionModelArraylist.add(new QuestionModel("The complexity of binary sort algorithm is ? ","O(logn)"));
        questionModelArraylist.add(new QuestionModel("Deletion from one end and insertion from other end is ? ","queue"));
        questionModelArraylist.add(new QuestionModel("Which of the following data structure is linear data structure ? ","array"));
        questionModelArraylist.add(new QuestionModel("Is java a case-sensitive language-yes or no ? ","yes"));
        questionModelArraylist.add(new QuestionModel("Do java support pointers explicitly-yes or no ? ","no"));
        questionModelArraylist.add(new QuestionModel("java.util.Collections is a: ","class"));
        questionModelArraylist.add(new QuestionModel("In java, the Comparator interface contains the method ? ","compare()"));
        questionModelArraylist.add(new QuestionModel("Hashtables in java are synchronized-true or false ? ","true"));
        questionModelArraylist.add(new QuestionModel("Enumeration returned by ArrayList is Fail-safe or Fail-fast ? ","Fail-fast"));



    }

    public void setData(){


        if(questionModelArraylist.size()>currentPosition) {

            questionLabel.setText(questionModelArraylist.get(currentPosition).getQuestionString());

            scoreLabel.setText("Score :" + numberOfCorrectAnswer + "/" + questionModelArraylist.size());
            questionCountLabel.setText("Question No : " + (currentPosition + 1));


        }else{


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("You have successfully completed the quiz")
                    .setContentText("Your score is : "+ numberOfCorrectAnswer + "/" + questionModelArraylist.size())
                    .setConfirmText("Restart")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            currentPosition = 0;
                            numberOfCorrectAnswer = 0;
                            progressBar.setProgress(0);
                            setData();
                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();

        }

    }



}
