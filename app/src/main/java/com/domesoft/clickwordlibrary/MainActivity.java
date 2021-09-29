package com.domesoft.clickwordlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.domesoft.wordclicker.LANGUAGEKt;
import com.domesoft.wordclicker.WordClicker;

public class MainActivity extends AppCompatActivity {

    TextView tvContent, tvShow;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        tvShow = findViewById(R.id.tvShow);
        content = "<h3><strong>1. <span style=\"color: #ff0000;\">Read everything you can get your hands on</span></strong></h3>\n" +
                "<p>Classic literature, paperbacks, newspapers, websites, emails, your social media feed, cereal boxes: if it&rsquo;s in English, read it. Why? Well, this content will be full of juicy new vocabulary, as well as a fair amount you already know. This helps you improve quickly, as re-exposure to learned vocabulary gives you new examples in context, therefore reinforcing those words in your mind. On the other hand, learning new words and expressions is essential to building your vocabulary arsenal, particularly in a language like English with so many words! However, don&rsquo;t just read and move on &ndash; next, you&rsquo;ve got to&hellip;</p>\n" +
                "<h3><strong>2. <span style=\"color: #ff0000;\">Actively take note of new vocabulary</span></strong></h3>\n" +
                "<p>This tip is a classic one for good reason: it works! When learning, we often enjoy a new word of phrase so much that forgetting it seems impossible. But trust us, not everything sticks the first time. To fight this, get into the habit of carrying around a funky notebook or using a tool like Evernote. Whenever you hear or read a new word or expression, write it down in context: that is, in a sentence and with its meaning noted. This saves you time as you won&rsquo;t return to that word and ask yourself: &ldquo;What did that word/expression mean again?&rdquo;</p>";

        WordClicker wordClicker = new WordClicker(tvContent,content);
        wordClicker.getClickedWord(clickedWord ->
                tvShow.setText(clickedWord)
                //Toast.makeText(this, clickedWord, Toast.LENGTH_SHORT).show()
        );

        wordClicker.setSelectedColor("#121211");
        wordClicker.setTextStyle(Typeface.NORMAL);
        wordClicker.setBackgroundColor("#f0f283");
        wordClicker.setTextSize(1.05f);
        wordClicker.setLanguage(LANGUAGEKt.ENGLISH());
        wordClicker.create();


    }
}