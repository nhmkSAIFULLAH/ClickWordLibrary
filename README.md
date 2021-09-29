# ClickWordLibrary

[![](https://jitpack.io/v/nhmkSAIFULLAH/ClickWordLibrary.svg)](https://jitpack.io/#nhmkSAIFULLAH/ClickWordLibrary)

**Step 1. Add the JitPack repository to your build file**

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
**Step 2. Add the dependency**
  
  ```gradle
  dependencies {
	        implementation 'com.github.nhmkSAIFULLAH:ClickWordLibrary:1.3.0'
	}
  ```
  
**Step 3. pass your textview and text String via WordClicker Constructor like below,**
  
  ```
          WordClicker wordClicker = new WordClicker(tvContent,content);
  ```
  
**Step 4: get the selected word every time user clicked on a word in the textview by the interface below,**
  
  ```
          wordClicker.getClickedWord(clickedWord ->
                Toast.makeText(this, clickedWord, Toast.LENGTH_SHORT).show()
        );
  ```
  
  You can do whatever you want with the word.
  
**Step 5: You have to call create() method to start. Otherwise this library won't work.**
  
  ```
  wordClicker.create();
  ```
  
## Extra Feature: 
  
  **1. You can set the selected word color with the method below,**
  
  ```
          wordClicker.setSelectedColor("#fcba03");
  ```
  
  **2. You can set the selected word style with the method below,**

  ```
          wordClicker.setTextStyle(Typeface.BOLD);
  ```
  
  **There are 4 style available.**
  
    1. Typeface.NORMAL   [default]
    2. Typeface.BOLD
    3. Typeface.BOLD_ITALIC
    4. Typeface.ITALIC


 **3. You can set your local languege with the method below,**


 ```
        wordClicker.setLanguage(LANGUAGEKt.ENGLISH());
 ```
 
 **There are 7 language available.**
 
 ```
   1. LANGUAGEKt.ENGLISH()   [default]
   2. LANGUAGEKt.CHINESE()
   3. LANGUAGEKt.FRENCH()
   4. LANGUAGEKt.GERMAN()
   5. LANGUAGEKt.ITALIAN()
   6. LANGUAGEKt.JAPANESE()
   7. LANGUAGEKt.KOREAN()
```
  
  ## Happy coding :smiley:
