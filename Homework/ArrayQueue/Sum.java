public class Sum {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        for (int i = 0; i < args.length; i++) {
            String[] tokens = args[i].split("\\p{javaWhitespace}+");
            for (int j = 0; j < tokens.length; j++) {
                if (tokens[j].isEmpty()) continue;
                queue.push(Integer.parseInt(tokens[j]));
            }
        }
        int sum = 0;
        while (!queue.isEmpty()) {
            sum += (Integer)queue.pop();
        }
        System.out.println(sum);
    }
}
