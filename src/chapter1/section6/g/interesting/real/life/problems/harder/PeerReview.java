package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 13/11/20.
 */
public class PeerReview {

    private static class Author {
        int id;
        String institution;

        public Author(int id, String institution) {
            this.id = id;
            this.institution = institution;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Author author = (Author) other;
            return id == author.id &&
                    Objects.equals(institution, author.institution);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, institution);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int reviews = scanner.nextInt();
        int papers = scanner.nextInt();

        while (papers != 0 || reviews != 0) {
            Set<Integer> problems = new HashSet<>();
            Map<Integer, String> paperToInstitutionMap = new HashMap<>();
            Map<Author, Set<Integer>> authorToReviewsMap = new HashMap<>();
            int[] reviewsCount = new int[papers + 1];

            for (int paperId = 1; paperId <= papers; paperId++) {
                String institution = scanner.next();
                Set<Integer> reviewsSet = new HashSet<>();

                for (int r = 0; r < reviews; r++) {
                    int reviewedPaperId = scanner.nextInt();

                    if (reviewedPaperId != paperId) {
                        if (!reviewsSet.contains(reviewedPaperId)) {
                            reviewsSet.add(reviewedPaperId);
                            reviewsCount[reviewedPaperId]++;
                        } else {
                            problems.add(reviewedPaperId);
                        }
                    } else {
                        problems.add(paperId);
                    }
                }

                authorToReviewsMap.put(new Author(paperId, institution), reviewsSet);
                paperToInstitutionMap.put(paperId, institution);
            }

            for (Author author : authorToReviewsMap.keySet()) {
                String institution = author.institution;
                Set<Integer> reviewsList = authorToReviewsMap.get(author);

                for (int paperId : reviewsList) {
                    String paperInstitution = paperToInstitutionMap.get(paperId);
                    if (paperInstitution.equals(institution)) {
                        problems.add(paperId);
                    }
                }
            }

            for (int paperId = 1; paperId < reviewsCount.length; paperId++) {
                if (reviewsCount[paperId] != reviews) {
                    problems.add(paperId);
                }
            }

            if (problems.isEmpty()) {
                System.out.println("NO PROBLEMS FOUND");
            } else if (problems.size() == 1) {
                System.out.println("1 PROBLEM FOUND");
            } else {
                System.out.printf("%d PROBLEMS FOUND\n", problems.size());
            }
            reviews = scanner.nextInt();
            papers = scanner.nextInt();
        }
    }
}
