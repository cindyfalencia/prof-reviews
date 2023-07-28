package finalproject;


public class RatingByKeyword extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String, Integer>> counts;

	public RatingByKeyword(Parser p) {
		super(p);
		counts = new MyHashTable<>();
		for (int i = 1; i <= 5; i++) {
			counts.put(String.valueOf(i), new MyHashTable<>());
		}
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		if (counts == null) {
			extractInformation();
		}

		MyHashTable<String, Integer> dist = new MyHashTable<>();

		// Iterate over the rating keys
		for (String ratingKey : counts.getKeySet()) {
			MyHashTable<String, Integer> ratingCounts = counts.get(ratingKey);
			if (ratingCounts != null) {
				Integer keywordCount = ratingCounts.get(keyword.trim().toLowerCase());
				if (keywordCount != null) {
					// Store the count of the keyword for this rating
					dist.put(ratingKey, keywordCount);
				} else {
					// If the keyword doesn't exist for this rating, put 0 as the count
					System.out.println("Keyword '" + keyword + "' not found for rating " + ratingKey);
					dist.put(ratingKey, 0);
				}
			} else {
				System.out.println("Rating counts not found for rating " + ratingKey);
			}
		}

		return dist;
	}

	@Override
	public void extractInformation() {
		final Integer RATING_INDEX = parser.fields.get("rating");
		final Integer COMMENT_INDEX = parser.fields.get("comments");

		counts = new MyHashTable<>();
		for (int i = 1; i <= 5; i++) {
			counts.put(String.valueOf(i), new MyHashTable<>());
		}

		if (RATING_INDEX != null && COMMENT_INDEX != null) {
			for (String[] review : parser.data) {
				String comment = "";
				if (review[COMMENT_INDEX] != null) {
					comment = review[COMMENT_INDEX].trim().toLowerCase().replaceAll("[^a-z']", " ");
				}

				int rating = 0;
				if (review[RATING_INDEX] != null && !review[RATING_INDEX].isEmpty()) {
					rating = (int) Double.parseDouble(review[RATING_INDEX]);
				}
				String ratingCategory = String.valueOf(Math.min(5, Math.max(1, rating)));

				MyHashTable<String, Integer> ratingCounts = counts.get(ratingCategory);
				if (ratingCounts == null) {
					ratingCounts = new MyHashTable<>();
					counts.put(ratingCategory, ratingCounts);
				}

				String[] tokens = comment.split("\\s+");
				for (String token : tokens) {
					if (token.isEmpty()) {
						continue;
					}
					Integer tokenCount = ratingCounts.get(token);
					if (tokenCount == null) {
						ratingCounts.put(token, 1);
					} else {
						ratingCounts.put(token, tokenCount + 1);
					}
				}
			}
		}
	}
}
