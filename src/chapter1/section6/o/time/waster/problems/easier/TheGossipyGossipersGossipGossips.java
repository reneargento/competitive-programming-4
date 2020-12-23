package chapter1.section6.o.time.waster.problems.easier;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by Rene Argento on 22/12/20.
 */
public class TheGossipyGossipersGossipGossips {

    private static class Person {
        int id;
        boolean informed;

        public Person(int id, boolean informed) {
            this.id = id;
            this.informed = informed;
        }
    }

    private static class Event implements Comparable<Event> {
        Person person1;
        Person person2;
        int time;

        public Event(Person person1, Person person2, int time) {
            this.person1 = person1;
            this.person2 = person2;
            this.time = time;
        }

        @Override
        public int compareTo(Event otherEvent) {
            if (time != otherEvent.time) {
                return time - otherEvent.time;
            }
            if (person1.informed || person2.informed) {
                return -1;
            } else if (otherEvent.person1.informed || otherEvent.person2.informed) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            PriorityQueue<Event> events = new PriorityQueue<>();
            int population = scanner.nextInt();
            int meetUpEvents = scanner.nextInt();
            UnionFind unionFind = new UnionFind(population);
            Person[] people = new Person[population];
            for (int i = 0; i < population; i++) {
                people[i] = new Person(i, false);
            }

            int informedPeople = 1;
            people[0].informed = true;

            for (int i = 0; i < meetUpEvents; i++) {
                int person1Id = scanner.nextInt() - 1;
                int person2Id = scanner.nextInt() - 1;
                int frequency = scanner.nextInt();
                unionFind.union(person1Id, person2Id);

                for (int f = 0; f < frequency; f++) {
                    int time = scanner.nextInt();
                    events.offer(new Event(people[person1Id], people[person2Id], time));
                }
            }

            if (unionFind.count() != 1) {
                System.out.println("-1");
            } else {
                int currentTime = 0;
                while (informedPeople != people.length) {
                    Event event = events.poll();

                    // Make sure all events are updated (the informed field may have been changed)
                    List<Event> eventsToUpdate = new ArrayList<>();
                    eventsToUpdate.add(event);
                    while (!events.isEmpty() && events.peek().time == event.time) {
                        eventsToUpdate.add(events.poll());
                    }
                    for (Event eventToUpdate : eventsToUpdate) {
                        events.offer(eventToUpdate);
                    }
                    event = events.poll();

                    if ((event.person1.informed && !event.person2.informed)
                            || (!event.person1.informed && event.person2.informed)) {
                        if (!event.person1.informed) {
                            event.person1.informed = true;
                        } else {
                            event.person2.informed = true;
                        }
                        informedPeople++;
                    }
                    currentTime = event.time;
                    events.offer(new Event(event.person1, event.person2, event.time + 100));
                }
                System.out.println(currentTime);
            }
        }
    }

    private static class UnionFind {
        private int[] leaders;
        private int[] ranks;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        // O(inverse Ackermann function)
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }
            components--;
        }
    }
}
