package com.domesoft.wordclicker;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
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

    private SpannableStringBuilder stringBuilder;
    private boolean isColorAdded = false; private String selectedFgColor;
    private boolean isBgAdded = false; private String selectedBackground;
    private boolean isTypeFaceAdded = false; private int typeFace;
    private boolean isSizeAdded = false; private float textSize;
    
    private Locale language = Locale.ENGLISH;




    public WordClicker(TextView textView, String string) {
        this.textView = textView;
        this.string = string;
        this.stringBuilder = new SpannableStringBuilder(string);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void create(){
        //textView.setText(string);
        selectableWord(textView,stringBuilder);

        textView.setOnTouchListener(new LinkMovementMethod());

    }
    public WordClicker getClickedWord(GetClickedWord getClickedWord){
        this.getClickedWord = getClickedWord;
        return this;
    }

    public WordClicker setSelectedColor(String color){
        this.selectedFgColor = color;
        isColorAdded = true;
        return this;
    }

    public WordClicker setBackgroundColor(String backgroundColor){
        this.selectedBackground = backgroundColor;
        isBgAdded = true;
        return this;
    }

    public WordClicker setTextStyle(int typeFace){
        this.typeFace = typeFace;
        isTypeFaceAdded = true;
        return this;
    }

    public WordClicker setTextSize(float textSize){
        isSizeAdded = true;
        this.textSize = textSize;
        return this;
    }
    
    public WordClicker setLanguage(int language){

        switch (language){
            case 0: this.language = Locale.ENGLISH;
            break;
            case 1: this.language = Locale.CHINESE;
            break;
            case 2: this.language = Locale.FRENCH;
            break;
            case 3: this.language = Locale.GERMAN;
            break;
            case 4: this.language = Locale.ITALIAN;
            break;
            case 5: this.language = Locale.JAPANESE;
            break;
            case 6: this.language = Locale.KOREAN;
            break;
        }
       return this;
    }




    private void selectableWord(TextView textView, SpannableStringBuilder entireString) {
        //First we trim the text and remove the spaces at start and end.
        SpannableStringBuilder entireContent = entireString;
        //And then  set the textview movement method to prevent freezing
        //And we set the text as SPANNABLE text.
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        textView.setText(entireContent, TextView.BufferType.SPANNABLE);
        //After we get the spans of the text with iterator and we initialized the iterator
        Spannable spans = (Spannable) textView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(language);
        iterator.setText(entireContent.toString());
        int start = iterator.first();

        //Here we get all possible words by iterators
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String possibleWord = entireContent.toString().substring(start,end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord, start, end);
                spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(final String word, int start, int end) {
        return new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
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
                stringBuilder = buildSpan(string,start, end,selectedFgColor,selectedBackground, textSize);
                selectableWord(textView,stringBuilder);
            }
        };
    }

    private SpannableStringBuilder buildSpan(String content, int start, int end, String fSpanColor, String bgColor, float textSize) {

        final SpannableStringBuilder sb = new SpannableStringBuilder(content);

        if (isColorAdded){
            final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor(fSpanColor));
            sb.setSpan(fcs,start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        if (isBgAdded){
            final BackgroundColorSpan bcs = new BackgroundColorSpan(Color.parseColor(bgColor));
            sb.setSpan(bcs,start,end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        if (isTypeFaceAdded){
            final StyleSpan bss = new StyleSpan(typeFace);
            sb.setSpan(bss, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        if (isSizeAdded){
            final RelativeSizeSpan rss = new RelativeSizeSpan(textSize);
            sb.setSpan(rss, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }


        return sb;
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
