package util;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.io.OutputStream;
import java.util.List;

public class WordCloudBuilder {


    public static void buildWordCouldByWordFrequencies(int width_img, int height_img, int angle_word, int maxFont,
                                                       int minFont, List<WordFrequency> wordFrequencies, Color backgroudColor, OutputStream outputStream,
                                                       Color... wordColors) {
        writeToStreamAsPNG(buildWordCouldByWordFrequencies(width_img, height_img, angle_word, maxFont, minFont,
                wordFrequencies, backgroudColor, wordColors), outputStream);
    }

    public static void buildWordCouldByWordFrequencies(int width_img, int height_img, int angle_word, int maxFont,
                                                       int minFont, List<WordFrequency> wordFrequencies, Color backgroudColor, String filePath,
                                                       Color... wordColors) {
        writeToFile(buildWordCouldByWordFrequencies(width_img, height_img, angle_word, maxFont, minFont,
                wordFrequencies, backgroudColor, wordColors), filePath);
    }

    public static void buildWordCouldByWords(int width_img, int height_img, int angle_word, int maxFont, int minFont,
                                             List<String> words, Color backgroudColor, OutputStream outputStream, Color... wordColors) {
        writeToStreamAsPNG(buildWordCouldByWordFrequencies(width_img, height_img, angle_word, maxFont, minFont,
                buildWordFrequencies(words), backgroudColor, wordColors), outputStream);
    }

    public static void buildWordCouldByWords(int width_img, int height_img, int angle_word, int maxFont, int minFont,
                                             List<String> words, Color backgroudColor, String filePath, Color... wordColors) {
        writeToFile(buildWordCouldByWordFrequencies(width_img, height_img, angle_word, maxFont, minFont,
                buildWordFrequencies(words), backgroudColor, wordColors), filePath);
    }

    private static WordCloud buildWordCouldByWordFrequencies(int width_img, int height_img, int angle_word, int maxFont,
                                                             int minFont, List<WordFrequency> wordFrequencies, Color backgroudColor, Color... wordColors) {
        Dimension dimension = new Dimension(width_img, height_img);
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setKumoFont(new KumoFont("default", FontWeight.PLAIN));
        wordCloud.setColorPalette(new ColorPalette(wordColors));
        wordCloud.setFontScalar(new LinearFontScalar(minFont, maxFont));
        wordCloud.setBackgroundColor(backgroudColor);
        wordCloud.setAngleGenerator(new AngleGenerator(angle_word));
        wordCloud.build(wordFrequencies);
        return wordCloud;
    }

    private static List<WordFrequency> buildWordFrequencies(List<String> words) {
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
        return wordFrequencies;
    }

    private static void writeToFile(WordCloud wordCloud, String filePath) {
        wordCloud.writeToFile(filePath);
    }

    private static void writeToStreamAsPNG(WordCloud wordCloud, OutputStream outputStream) {
        wordCloud.writeToStreamAsPNG(outputStream);
    }
}





