import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                names.get(new Random().nextInt(names.size())),
                families.get(new Random().nextInt(families.size())),
                new Random().nextInt(100),
                Sex.values()[new Random().nextInt(Sex.values().length)],
                Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long minorCount = persons.stream().filter(person -> person.getAge() < 18).count();
        System.out.println("Number of minors: " + minorCount);

        int limit = 20;

        List<String> militaryList = persons.stream()
            .filter(person -> person.getAge() < 27)
            .filter(person -> person.getAge() >= 18)
            .map(Person::getFamily)
            .collect(Collectors.toList());
        System.out.printf("First %d out of %d conscripts: %s\n", limit, militaryList.size(),
            militaryList.stream().limit(20).toList());

        List<String> laborList = persons.stream()
            .filter(person -> person.getEducation() == Education.HIGHER)
            .filter(person -> person.getSex() == Sex.MAN && person.getAge() < 65
                || person.getSex() == Sex.WOMAN && person.getAge() < 60)
            .filter(person -> person.getAge() >= 18)
            .sorted(Comparator.comparing(Person::getFamily))
            .map(Person::getFamily)
            .collect(Collectors.toList());
        System.out.printf("First %d out of %d workers: %s", limit, laborList.size(),
            laborList.stream().limit(20).toList());
    }
}