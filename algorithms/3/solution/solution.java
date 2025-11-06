public int countWords(String text) {
    if (text == null || text.trim().isEmpty()) {
        return 0;
    }

    String[] words = text.split("\\s+");
    Set<String> uniqueWords = new HashSet<>();

    for (String word : words) {
        if (!word.isEmpty()) {
            uniqueWords.add(word);
        }
    }

    return uniqueWords.size();
}