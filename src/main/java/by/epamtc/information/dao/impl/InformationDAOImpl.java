package by.epamtc.information.dao.impl;

import by.epamtc.information.dao.CustomFileReader;
import by.epamtc.information.dao.InformationDAO;
import by.epamtc.information.dao.impl.parser.TextElementParser;
import by.epamtc.information.entity.*;
import by.epamtc.information.entity.impl.Paragraph;
import by.epamtc.information.entity.impl.PunctuationMark;
import by.epamtc.information.entity.impl.Text;
import by.epamtc.information.entity.impl.Word;
import by.epamtc.information.main.InjectProperty;
import by.epamtc.information.main.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class InformationDAOImpl implements InformationDAO {
    private TextElementParser parser = new TextElementParser();
    private CustomFileReader customFileReader = new CustomFileReaderImpl();

    @InjectProperty("textElementRegex")
    private String textElementRegex;

    @Override
    public Text text(String fileName) {
        Text text = new Text();
        final String sourceText = customFileReader.stringFromFile(fileName);
        text.setText(sourceText);
        return text;
    }
    

    @Override
    public List<TextElement> textElementList(Text text) {
        List<TextElement> textElements = new ArrayList<>();

        textElementRegex = "(?<Title>\\d\\.( |\\d)*.+[^.]\\n)|(?<Paragraph>.+[^;}{][.:!?]*\\n)|(?<CodeBlock>.*\\{\\n(.*\\n)+?\\n*(}\\n)+((.+;\\n)*(}\\n)+)*)";

        Pattern pattern = Pattern.compile(textElementRegex);
        Matcher matcher = pattern.matcher(text.getText());

        while (matcher.find()){
            String title = matcher.group("Title");
            String paragraph = matcher.group("Paragraph");
            String codeBlock = matcher.group("CodeBlock");

            if (title != null){
                System.out.println("!!!!!!!!!!!!!!!title");
                System.out.println(title);
            }
            if (paragraph != null){
                System.out.println("!!!!!!!!!!!!!!!!!!paragraph");
                System.out.println(paragraph);
            }
            if (codeBlock != null){
                System.out.println("!!!!!!!!!!!!!!!!!!codeBlock");
                System.out.println(codeBlock);
            }
        }
        return textElements;
    }

    @Override
    public List<Word> wordList(Paragraph paragraph) {
        return null;
    }

    @Override
    public List<PunctuationMark> punctuationMarkList(Paragraph paragraph) {
        return null;
    }
}
