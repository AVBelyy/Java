public class Sum {
    public static void main(String[] args) {
        short sum = 0;
        for (int i = 0; i < args.length; i++) {
            String[] tokens = args[i].split("\\p{javaWhitespace}+");
            for (int j = 0; j < tokens.length; j++) {
                if (tokens[j].isEmpty()) continue;
                if (tokens[j].startsWith("-q") || tokens[j].startsWith("-Q")) {
                    sum += Integer.parseInt("-" + tokens[j].substring(2), 4);
                } else if (tokens[j].startsWith("q") || tokens[j].startsWith("Q")) {
                    sum += Integer.parseInt(tokens[j].substring(1), 4);
                } else {
                    sum += Integer.parseInt(tokens[j]);
                }
            }
        }
        System.out.println(sum);
    }
}
