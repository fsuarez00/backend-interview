package ai.brace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ai.brace.model.BookInfo;
import ai.brace.model.TextInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Main
{
    public static Path getPathForResource(final String path)
    {
        try
        {
            return Paths.get(ClassLoader.getSystemResource(path).toURI());
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("######### TASK 1 #########");
        // read json file and deserialize to an object of type BookInfo
        Gson gson = new Gson();
        Path pathA1 = getPathForResource("a1.json");
        BufferedReader readerA1 = Files.newBufferedReader(pathA1);
        BookInfo bookInfoA1 = gson.fromJson(readerA1, BookInfo.class);

        TextInfo[] textInfoArr1 = bookInfoA1.getTextArray();
        // sort the array
        Arrays.sort(textInfoArr1);

        for(TextInfo textInfo : textInfoArr1)
            System.out.println(textInfo.getTextdata());

        System.out.println("######### TASK 2 #########");
        Path pathA2 = getPathForResource("a2.json");
        BufferedReader readerA2 = Files.newBufferedReader(pathA2);
        BookInfo bookInfoA2 = gson.fromJson(readerA2, BookInfo.class);

        TextInfo[] textInfoArr2 = bookInfoA2.getTextArray();

        // merge both arrays
        TextInfo[] textInfoArr3 = new TextInfo[textInfoArr1.length + textInfoArr2.length];
        for (int i = 0; i < textInfoArr1.length; i++)
            textInfoArr3[i] = textInfoArr1[i];
        for (int i = textInfoArr1.length, j = 0; j < textInfoArr2.length; i++, j++)
            textInfoArr3[i] = textInfoArr2[j];

        // sort the array
        Arrays.sort(textInfoArr3);

        for(TextInfo textInfo : textInfoArr3)
            System.out.println(textInfo.getTextdata());

        System.out.println("######### TASK 3 #########");

        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        // using a regex to match words and count them
        Map<String, Integer> wordFreqMap = new TreeMap<>();
        for(TextInfo textInfo : textInfoArr3) {
            Matcher matcher = pattern.matcher(textInfo.getTextdata());

            while (matcher.find()) {
                String matchStr = matcher.group().toLowerCase();

                Integer count = wordFreqMap.get(matchStr);
                if(count == null) {
                    count = 0;
                    wordFreqMap.put(matchStr, count);
                }
                wordFreqMap.put(matchStr, count + 1);
            }
        }

        for(Map.Entry<String, Integer> entry : wordFreqMap.entrySet())
            System.out.println('(' + entry.getKey() + ") : " + entry.getValue());

        System.out.println("######### TASK 4 #########");

        // change lastmodified from epoch to ISO format
        ZoneOffset offset = ZoneOffset.UTC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateA1 = LocalDateTime.ofEpochSecond(Long.parseLong(bookInfoA1.getLastModified()), 0, offset);
        String dateA1Str = formatter.format(dateA1);
        LocalDateTime dateA2 = LocalDateTime.ofEpochSecond(Long.parseLong(bookInfoA2.getLastModified()), 0, offset);
        String dateA2Str = formatter.format(dateA2);

        JsonElement a1Json = gson.toJsonTree(bookInfoA1);
        JsonElement a2Json = gson.toJsonTree(bookInfoA2);

        // generate random uui
        UUID uuid = UUID.randomUUID();

        // TODO add check for JsonArray and merge recursively?  Ran out of time x_x
        JsonObject mergedJsonObj;
        if(dateA1.isAfter(dateA2)) {
            mergedJsonObj = mergeJson(a2Json, a1Json);

            mergedJsonObj.addProperty("lastModified", dateA1Str);

            mergedJsonObj.addProperty("uuid", uuid.toString());
        } else {
            mergedJsonObj = mergeJson(a1Json, a2Json);

            mergedJsonObj.addProperty("lastModified", dateA2Str);

            mergedJsonObj.addProperty("uuid", uuid.toString());
        }

        String mergedJsonStr = gson.toJson(mergedJsonObj);
        String outputPath = pathA1.getParent().toString() + "/output.json";
        System.out.println("Writing to: [" + outputPath + ']');
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.append(mergedJsonStr);
        writer.close();
    }

    public static JsonObject mergeJson(JsonElement jsonElement1, JsonElement jsonElement2) {
        JsonObject mergedJsonObj = new JsonObject();
        for(Map.Entry<String, JsonElement> entry : jsonElement1.getAsJsonObject().entrySet())
            mergedJsonObj.add(entry.getKey(), entry.getValue());
        for(Map.Entry<String, JsonElement> entry : jsonElement2.getAsJsonObject().entrySet())
            mergedJsonObj.add(entry.getKey(), entry.getValue());

        return mergedJsonObj;
    }
}
