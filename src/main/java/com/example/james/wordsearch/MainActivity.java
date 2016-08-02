package com.example.james.wordsearch;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends Activity implements OnClickListener {

    // get references to widgets
    private EditText letterNumEditText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private Button searchButton;

    // define instance variables
    private int letterNum;
    private String letterNumString;
    public String wordSearch;
    private ArrayList<EditText> letterBoxes;

    private WordDB wordDB = new WordDB(this);

    public String getWordSearch(){
        return wordSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        letterNumEditText = (EditText) findViewById(R.id.letterNumEditText);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        searchButton = (Button) findViewById(R.id.searchButton);

        // set listeners
        searchButton.setOnClickListener(this);

        // create ArrayList for text boxes
        letterBoxes = new ArrayList<EditText>();
        letterBoxes.add(editText1);
        letterBoxes.add(editText2);
        letterBoxes.add(editText3);
        letterBoxes.add(editText4);
        letterBoxes.add(editText5);
        letterBoxes.add(editText6);
    }

    public void search(){

        // get the number of letters
        letterNumString = letterNumEditText.getText().toString();
        try {
            letterNum = Integer.parseInt(letterNumString);
        }
        catch (NumberFormatException e){
            letterNum = 0;
        }

        String test1 = editText1.getText().toString();
        String test2 = editText2.getText().toString();

        StringBuilder testWordSB = new StringBuilder(letterNum);
        testWordSB.append(test1);
        testWordSB.append(test2);
        String wordTest = testWordSB.toString();

//        Log.d("word search", wordTest);

        String tempLetterBoxText;
        StringBuilder wordSB = new StringBuilder(letterNum);

        for (int i = 0; i < letterBoxes.size(); i++){
            if (letterBoxes.get(i).getText().length() == 0){
                tempLetterBoxText = "_";
            }
            else{
                tempLetterBoxText = letterBoxes.get(i).getText().toString();
            }
            wordSB.append(tempLetterBoxText);
        }
        wordSearch = wordSB.toString();

//        Log.d("word search", wordSearch);

        // call getWords method and iterate through words

//        ArrayList<Word> words = wordDB.getWords();
//
//        for (int i = 0; i < words.size(); i++) {
//            Log.d("word search", words.get(i).getName());
//            Log.d("word search", words.get(i).getDefinition());
//            Log.d("word search", words.get(i).getOther());
//        }

        // call searchWords method and iterate through matching words
        ArrayList<Word> searchWords = wordDB.searchWords(wordSearch);

        Log.d("word search", searchWords.size() + " matching word(s) have been found.");

        for (int i = 0; i < searchWords.size(); i++) {
            Log.d("word search", "Word: " + searchWords.get(i).getName());
            Log.d("word search", "Definition: " + searchWords.get(i).getDefinition());
            Log.d("word search", "Other info: " + searchWords.get(i).getOther());
        }

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.searchButton:
                search();
                break;
        }
    }
}
