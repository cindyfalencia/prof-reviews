# prof-reviews

This is my final project for the Winter Semester at McGill University, inspired by an assignment created by Juliette Woodrow, Kathleen Creel, and Nick Bowman from Stanford University. The project's primary goal is to process and analyze a complex dataset of college professor reviews collected from RateMyProfessors.com. By developing specific classes and data structures, we aim to parse and store this structured data and use it alongside a graphical tool to visualize word frequency data across review quality and professor gender, revealing insights into language usage biases.

Project Background:
At the heart of this assignment lies a dataset obtained from RateMyProfessors.com, comprising anonymous textual reviews provided by university students, along with numerical ratings on the perceived quality and difficulty of courses. We have chosen to focus on reviews from a subset of universities spanning 15 years, totaling 19,685 reviews. Our focus is on identifying language biases related to gender when evaluating professors, aiming to foster gender inclusivity in teaching evaluations.

Teaching evaluations hold significant impact in university life, influencing students' course choices and academic planning. However, these evaluations often carry harmful biases, including gender bias, as recent research has revealed. Such biases can affect decisions regarding hiring, firing, tenure, and promotions.

Project Objectives:
The project is divided into two main parts:

Part 1 - Data Structure Implementation:
In this part, we will focus on implementing robust data structures to effectively process and analyze the professor reviews dataset.

Part 2 - Data Visualization and Analysis:
This part involves creating powerful tools to visualize word frequency breakdowns based on numerical ratings and professor gender. The project's data visualization is straightforward, but we encourage exploring and extending the analysis to gain deeper insights into gender-inclusive language usage.

Dataset Files:
You have received two files: RateMyProf Data Gendered.csv and RateMyProf Data Gendered Sample.csv. The latter serves as a subset for testing and debugging purposes, while the larger dataset (RateMyProf Data Gendered.csv) will be used for evaluation.

Columns in the Dataset:

1. Professor Name: Name of the professor receiving the review.
2. School Name: Name of the school where the professor is affiliated.
3. Department Name: The department where the professor teaches.
4. Post Date: The date on which the review was posted.
5. Student Star: Numerical rating given by the student regarding professor/course quality.
6. Student Difficulty: Numerical rating given by the student regarding professor/course difficulty.
7. Comments: The comment posted by the student.
8. Gender: Perceived gender of the professor based on the pronouns used in the comments (M, F, or X for non-binary).
