package finalproject;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingDistributionBySchool extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> ratings;

	public RatingDistributionBySchool(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		return ratings.get(keyword.trim().toLowerCase());
	}

	@Override
	public void extractInformation() {
		MyHashTable<String, MyHashTable<String, MyHashTable<String, Double>>> tmpRatings = new MyHashTable<>();

		final Integer PROF_INDEX = parser.fields.get("professor_name");
		final Integer SCHOOL_INDEX = parser.fields.get("school_name");
		final Integer RATING_INDEX = parser.fields.get("student_star");

		for (String[] review : parser.data) {
			String profName = review[PROF_INDEX].trim().toLowerCase();
			String schoolName = review[SCHOOL_INDEX].trim().toLowerCase();
			String rating = review[RATING_INDEX];

			if (!tmpRatings.getKeySet().contains(schoolName))
				tmpRatings.put(schoolName, new MyHashTable<>());

			MyHashTable<String, MyHashTable<String, Double>> schoolRatings = tmpRatings.get(schoolName);
			MyHashTable<String, Double> profRatings;

			if (!schoolRatings.getKeySet().contains(profName)) {
				schoolRatings.put(profName, new MyHashTable<>());
				profRatings = schoolRatings.get(profName);
				profRatings.put("count", 0.);
				profRatings.put("total_score", 0.);
			}

			profRatings = schoolRatings.get(profName);
			profRatings.put("count", profRatings.get("count") + 1);
			profRatings.put("total_score", profRatings.get("total_score") + Double.valueOf(rating));
		}

		ratings = new MyHashTable<>();
		for (MyPair<String, MyHashTable<String, MyHashTable<String, Double>>> school : tmpRatings) {
			String schoolName = school.getKey();
			if (!ratings.getKeySet().contains(schoolName))
				ratings.put(schoolName, new MyHashTable<>());

			MyHashTable<String, Integer> professors = ratings.get(schoolName);
			for (MyPair<String, MyHashTable<String, Double>> professor : school.getValue()) {
				String profName = professor.getKey();

				MyHashTable<String, Double> profRatings = professor.getValue();
				Double average_rating = profRatings.get("total_score") / profRatings.get("count");

				professors.put(profName + "\n" + String.format("%.2f", average_rating),
						profRatings.get("count").intValue());
			}
		}
	}
}