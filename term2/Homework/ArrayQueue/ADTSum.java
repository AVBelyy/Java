public class ADTSum {
    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        for (int i = 0; i < args.length; i++) {
            String[] tokens = args[i].split("\\p{javaWhitespace}+");
            for (int j = 0; j < tokens.length; j++) {
                if (tokens[j].isEmpty()) continue;
                ArrayQueueADT.push(queue, Integer.parseInt(tokens[j]));
            }
        }
        int sum = 0;
        while (!ArrayQueueADT.isEmpty(queue)) {
            sum += (Integer)ArrayQueueADT.pop(queue);
        }
        System.out.println(sum);
    }
}
