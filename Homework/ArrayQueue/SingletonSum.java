public class SingletonSum {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String[] tokens = args[i].split("\\p{javaWhitespace}+");
            for (int j = 0; j < tokens.length; j++) {
                if (tokens[j].isEmpty()) continue;
                ArrayQueueSingleton.push(Integer.parseInt(tokens[j]));
            }
        }
        int sum = 0;
        while (!ArrayQueueSingleton.isEmpty()) {
            sum += (Integer)ArrayQueueSingleton.pop();
        }
        System.out.println(sum);
    }
}
