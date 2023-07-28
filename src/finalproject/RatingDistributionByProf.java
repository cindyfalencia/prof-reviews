package finalproject;

import javafx.util.Pair;

public class RatingDistributionByProf extends DataAnalyzer {
	// store data on ratings given by student to professor
	private MyHashTable<String, MyHashTable<String, Integer>> ratings;
	
    public RatingDistributionByProf(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// return distribution of ratings based on that keyword
		return ratings.get(keyword.trim().toLowerCase());
	}

	@Override
	public void extractInformation() {
		ratings = new MyHashTable<>();

		final Integer PROF_INDEX = parser.fields.get("professor_name");
		final Integer RATING_INDEX = parser.fields.get("student_star");

		for(String[] review : parser.data) {
			String profName = review[PROF_INDEX].trim().toLowerCase();
			String ratingCategory = getRatingCategory(review[RATING_INDEX]);
			MyHashTable<String, Integer> profRatings;

			if (!ratings.getKeySet().contains(profName)) {
				ratings.put(profName, new MyHashTable<>());
				profRatings = ratings.get(profName);
				for(int i=0; i<=5; i++){
					profRatings.put(String.valueOf(i),0);
				}
			}
			profRatings = ratings.get(profName);
			Integer count = profRatings.get(ratingCategory);
			profRatings.put(ratingCategory, count+1);
		}
		System.out.println("Finished");
		System.out.println(ratings.getKeySet());
	}
	private String getRatingCategory(String rating){
		return String.valueOf(Double.valueOf(rating).intValue());
	}
}

