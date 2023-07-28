package finalproject;

import java.util.ArrayList;

import java.util.HashMap;

public class RatingByGender extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> ratings;

	public RatingByGender(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		return ratings.get(keyword.trim().toLowerCase());
	}

	@Override
	public void extractInformation() {
//		final Integer COMMENT_INDEX = parser.fields.get("comment");
//		final Integer RATING_INDEX = parser.fields.get("quality");
//		for (String[] review : parser.data) {
//			String comment = review[COMMENT_INDEX].toLowerCase().replaceAll("[^a-z']", " ");
//			int rating = Integer.parseInt(review[RATING_INDEX]);
//			boolean counted = false;
//			for (String word : comment.split("\\s+")) {
//				if (word.equals("")) {
//					continue;
//				}
//				if (!counted) {
//					ratings[rating - 1].put(word, ratings[rating - 1].getKeySet().contains(word) ? ratings[rating - 1].get(word) + 1 : 1);
//					counted = true;
//				}
//			}
//		}

	}
}

