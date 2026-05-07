package chapter6.section2.b.input.parsing.recursive;

import java.io.*;

/**
 * Created by Rene Argento on 05/05/26.
 */
public class SentencePhraseGenerator {

    private static int choices = 1;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String request = FastReader.getLine();
        while (request != null) {
            String result = processRequest(request);
            outputWriter.printLine(result);
            request = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String processRequest(String request) {
        switch (request) {
            case "sentence": return getSentence();
            case "trans-sentence": return getTransSentence();
            case "intrans-sentence": return getIntransSentence();
            case "subject": return getSubject();
            case "object": return getObject();
            case "noun-phrase": return getNounPhrase();
            case "modified-noun": return getModifiedNoun();
            case "modifier": return getModifier();
            case "verb-phrase": return getVerbPhrase();
            case "intrans-verb-phrase": return getIntransVerbPhrase();
            case "prep-phrase": return getPrepPhrase();
            case "noun": return getNoun();
            case "trans-verb": return getTransVerb();
            case "intrans-verb": return getIntransVerb();
            case "article": return getArticle();
            case "adjective": return  getAdjective();
            case "adverb": return getAdverb();
            case "preposition": return getPreposition();
            default: return "";
        }
    }

    private static String getSentence() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return getTransSentence();
        } else {
            return getIntransSentence();
        }
    }

    private static String getTransSentence() {
        String transSentence = getSubject() + " " + getVerbPhrase() + " " + getObject();
        String prepPhrase = getPrepPhrase();
        if (!prepPhrase.isEmpty()) {
            transSentence +=  " " + prepPhrase;
        }
        return transSentence;
    }

    private static String getIntransSentence() {
        String intransSentence = getSubject() + " " + getIntransVerbPhrase();
        String prepPhrase = getPrepPhrase();
        if (!prepPhrase.isEmpty()) {
            intransSentence +=  " " + prepPhrase;
        }
        return intransSentence;
    }

    private static String getSubject() {
        return getNounPhrase();
    }

    private static String getObject() {
        return getNounPhrase();
    }

    private static String getNounPhrase() {
        return getArticle() + " " + getModifiedNoun();
    }

    private static String getModifiedNoun() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return getNoun();
        } else {
            return getModifier() + " " + getNoun();
        }
    }

    private static String getModifier() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return getAdjective();
        } else {
            return getAdverb() + " " + getAdjective();
        }
    }

    private static String getVerbPhrase() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return getTransVerb();
        } else {
            return getAdverb() + " " + getTransVerb();
        }
    }

    private static String getIntransVerbPhrase() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return getIntransVerb();
        } else {
            return getAdverb() + " " + getIntransVerb();
        }
    }

    private static String getPrepPhrase() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return getPreposition() + " " + getNounPhrase();
        } else {
            return "";
        }
    }

    private static String getNoun() {
        int index = choices % 5;
        choices++;
        switch (index) {
            case 0: return "man";
            case 1: return "dog";
            case 2: return "fish";
            case 3: return "computer";
            default: return "waves";
        }
    }

    private static String getTransVerb() {
        int index = choices % 4;
        choices++;
        switch (index) {
            case 0: return "struck";
            case 1: return "saw";
            case 2: return "bit";
            default: return "took";
        }
    }

    private static String getIntransVerb() {
        int index = choices % 4;
        choices++;
        switch (index) {
            case 0: return "slept";
            case 1: return "jumped";
            case 2: return "walked";
            default: return "swam";
        }
    }

    private static String getArticle() {
        int index = choices % 2;
        choices++;
        if (index == 0) {
            return "the";
        } else {
            return "a";
        }
    }

    private static String getAdjective() {
        int index = choices % 4;
        choices++;
        switch (index) {
            case 0: return "green";
            case 1: return "small";
            case 2: return "rabid";
            default: return "quick";
        }
    }

    private static String getAdverb() {
        int index = choices % 3;
        choices++;
        switch (index) {
            case 0: return "nearly";
            case 1: return "suddenly";
            default: return "restlessly";
        }
    }

    private static String getPreposition() {
        int index = choices % 3;
        choices++;
        switch (index) {
            case 0: return "on";
            case 1: return "over";
            default: return "through";
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
