package finalproject;

public class GenderByKeyword extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> counts;

	public GenderByKeyword(Parser p) {
		super(p);
		counts = new MyHashTable<>();
		counts.put("M", new MyHashTable<>());
		counts.put("F", new MyHashTable<>());
		counts.put("X", new MyHashTable<>());
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		if (counts == null) {
			extractInformation();
		}

		MyHashTable<String, Integer> dist = new MyHashTable<>();

		// Iterate over the gender keys
		for (String genderKey : counts.getKeySet()) {
			MyHashTable<String, Integer> genderCounts = counts.get(genderKey);
			if (genderCounts != null) {
				Integer keywordCount = genderCounts.get(keyword.trim().toLowerCase());
				if (keywordCount != null) {
					// Store the count of the keyword for this gender
					dist.put(genderKey, keywordCount);
				} else {
					// If the keyword doesn't exist for this gender, put 0 as the count
					System.out.println("Keyword '" + keyword + "' not found for gender " + genderKey);
					dist.put(genderKey, 0);
				}
			} else {
				System.out.println("Gender counts not found for gender " + genderKey);
			}
		}

		return dist;
	}

	@Override
	public void extractInformation() {
		final Integer PROF_INDEX = parser.fields.get("professor");
		final Integer COMMENT_INDEX = parser.fields.get("comments");
		final Integer GENDER_INDEX = parser.fields.get("gender");

		counts = new MyHashTable<>();
		counts.put("M", new MyHashTable<>());
		counts.put("F", new MyHashTable<>());
		counts.put("X", new MyHashTable<>());

		for (String[] review : parser.data) {
			String comment = "";
			if (review[COMMENT_INDEX] != null) {
				comment = review[COMMENT_INDEX].trim().toLowerCase().replaceAll("[^a-z'z]", " ");
			}
			String gender = "";
			if (review[GENDER_INDEX] != null && !review[GENDER_INDEX].isEmpty()) {
				gender = review[GENDER_INDEX].trim().toLowerCase();
			}

			MyHashTable<String, Integer> genderCounts = counts.get(gender);
			if (genderCounts == null) {
				genderCounts = new MyHashTable<>();
				counts.put(gender, genderCounts);
			}

			String[] tokens = comment.split("\\s+");

			for (String token : tokens) {
				if (token.isEmpty()) {
					continue;
				}
				Integer tokenCount = genderCounts.get(token);
				if (tokenCount == null) {
					genderCounts.put(token, 1);
				} else {
					genderCounts.put(token, tokenCount + 1);
				}
			}
		}
	}
}
