package com.domesoft.wordclicker;


import android.annotation.SuppressLint;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.BreakIterator;
import java.util.Locale;

public class WordClicker {

    private final TextView textView;
    private final String string;
    private GetClickedWord getClickedWord;

    public WordClicker(TextView textView, String string) {
        this.textView = textView;
        this.string = string;
    }


    @SuppressLint("ClickableViewAccessibility")
    public void create(){

        textView.setText(string);
        selectableWord(textView,string);
        textView.setOnTouchListener(new LinkMovementMethod());

    }
    public WordClicker getClickedWord(GetClickedWord getClickedWord){
        this.getClickedWord = getClickedWord;
        return this;
    }


    private void selectableWord(TextView textView, String entireString) {
        //First we trim the text and remove the spaces at start and end.
        String entireContent = entireString.trim();
        //And then  set the textview movement method to prevent freezing
        //And we set the text as SPANNABLE text.
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        textView.setText(entireContent, TextView.BufferType.SPANNABLE);
        //After we get the spans of the text with iterator and we initialized the iterator
        Spannable spans = (Spannable) textView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.ENGLISH);
        iterator.setText(entireContent);
        int start = iterator.first();

        //Here we get all possible words by iterators
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String possibleWord = entireContent.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }

            final String mWord;

            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {

                //put clicked word to user by this interface
                getClickedWord.getWord(mWord);
            }
        };
    }

    public static class LinkMovementMethod implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //First we get the initialized text view and its content
            TextView clickableText = (TextView) v;
            Object text = clickableText.getText();
            //And the control it if a Spanned.
            if (text instanceof Spanned) {
                //If it is a spanned text we get the text and motion event. This will decide the movement direction: UP or DOWN.
                Spanned buffer = (Spanned) text;
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP
                        || action == MotionEvent.ACTION_DOWN) {
                    //We set the new position to show. We moved the lines.
                    int x = (int) event.getX();
                    int y = (int) event.getY();


                    x -= clickableText.getTotalPaddingLeft();
                    y -= clickableText.getTotalPaddingTop();

                    x += clickableText.getScrollX();
                    y += clickableText.getScrollY();

                    Layout layout = clickableText.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);
                    //And we initialized the clickable spans again for all spanned words
                    ClickableSpan[] link = buffer.getSpans(off, off,
                            ClickableSpan.class);
                    // and assign onclick method of the ClickableSpan class for each word.
                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(clickableText);
                        }

                        //else if (action == MotionEvent.ACTION_DOWN) { }

                        return true;
                    }
                }
            }
            return false;
        }


    }

    public interface GetClickedWord {
        void getWord(String clickedWord);
    }

}
