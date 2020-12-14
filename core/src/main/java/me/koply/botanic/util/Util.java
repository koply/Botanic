package me.koply.botanic.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public final class Util {

    public static String readFile(File file) {
        final StringBuilder sb = new StringBuilder();
        try {
            final FileInputStream fs = new FileInputStream(file);
            readWorker(fs, sb);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    public static String readFile(InputStream is) {
        final StringBuilder sb = new StringBuilder();
        try {
            readWorker(is, sb);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    private static void readWorker(InputStream is, StringBuilder sb) throws IOException {
        final InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        is.close();
        isr.close();
        br.close();
    }

    public static void writeFile(File file, String str) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static final Random random = new Random();
    public static Color randomColor() { return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)); }

    public static MessageEmbed embed(String txt) {
        return new EmbedBuilder().setColor(randomColor())
                .setDescription(txt).build();
    }

    public static EmbedBuilder basicEmbed(Object o) {
        return new EmbedBuilder()
                .setDescription(o.toString())
                .setColor(randomColor());
    }


    public static boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}